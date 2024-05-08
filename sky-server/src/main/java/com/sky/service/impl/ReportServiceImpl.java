package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.ReportMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.*;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private WorkspaceService workspaceService;

    /**
     * turnover Statistics
     * @param begin
     * @param end
     * @return
     */
    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) {
        //get dateList, to string
        List<LocalDate> dateList = getDateList(begin, end);
        String dateListS = StringUtils.join(dateList,",");
        //get turnoverList, to string
        List<BigDecimal> turnoverList = new ArrayList<>();
        for (LocalDate date : dateList){
            // get beginTime and endTime
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            //construct a map to store all the conditions
            Map map = new HashMap<>();
            map.put("begin",beginTime);
            map.put("end",endTime);
            map.put("status", Orders.COMPLETED);
            BigDecimal turnover = reportMapper.getTurnoverByMap(map);
            // if turnover is null, handle it
            turnover = turnover==null? BigDecimal.valueOf(0) :turnover;
            turnoverList.add(turnover);
        }
        String turnoverListS = StringUtils.join(turnoverList,",");

        // get VO, return
        TurnoverReportVO turnoverReportVO = TurnoverReportVO
                .builder()
                .dateList(dateListS)
                .turnoverList(turnoverListS)
                .build();
        return turnoverReportVO;
    }

    /**
     * user Statistics
     * @param begin
     * @param end
     * @return
     */
    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) {
        // get dateList
        List<LocalDate> dateList = getDateList(begin, end);

        // get total user list and new user list
        List<Integer> totalUserList = new ArrayList<>();
        List<Integer> newUserList = new ArrayList<>();
        for (LocalDate date: dateList){
            // get the exact time stamp
            LocalDateTime beginTime = LocalDateTime.of(date,LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date,LocalTime.MAX);

            // get total users this day
            Integer totalUser = reportMapper.getTotalUser(endTime);
            totalUserList.add(totalUser);

            //get new users this day
            Integer newUser = reportMapper.getNewUser(beginTime, endTime);
            newUserList.add(newUser);
        }
        // construct VO
        UserReportVO userReportVO = UserReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .totalUserList(StringUtils.join(totalUserList,","))
                .newUserList(StringUtils.join(newUserList,","))
                .build();
        return userReportVO;
    }

    /**
     * order Statistics
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public OrderReportVO orderStatistics(LocalDate begin, LocalDate end) {
        // get dateList
        List<LocalDate> dateList = getDateList(begin, end);

        // get orderCountList
        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> validOrderCountList = new ArrayList<>();
        Integer totalOrderCount = 0;
        Integer validOrderCount = 0;
        for (LocalDate date : dateList){
            // get the exact time stamp
            LocalDateTime beginTime = LocalDateTime.of(date,LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date,LocalTime.MAX);

            // get order Count Today
            Map map = new HashMap<>();
            map.put("begin",beginTime);
            map.put("end",endTime);
            Integer orderCountToday = reportMapper.getOrderCountByMap(map);

            // get total order count
            totalOrderCount += orderCountToday;

            // get order count list
            orderCountList.add(orderCountToday);

            // get valid order Count Today
            map.put("status",Orders.COMPLETED);
            Integer validOrderCountToday = reportMapper.getOrderCountByMap(map);

            // get valid order count
            validOrderCount += validOrderCountToday;

            // get valid order count list
            validOrderCountList.add(validOrderCountToday);
        }
        // get orderCompletionRate
        Double orderCompletionRate = 0.0;
        if (totalOrderCount != 0){
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
        }

        // construct VO
        OrderReportVO orderReportVO = OrderReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .orderCountList(StringUtils.join(orderCountList,","))
                .validOrderCountList(StringUtils.join(validOrderCountList,","))
                .orderCompletionRate(orderCompletionRate)
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .build();
        return orderReportVO;
    }

    /**
     * Sales Top 10 Statistics
     * @param begin
     * @param end
     * @return
     */
    @Override
    public SalesTop10ReportVO salesTopStatistics(LocalDate begin, LocalDate end) {

        // get exact time stamp
        LocalDateTime beginTime = LocalDateTime.of(begin,LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end,LocalTime.MAX);

        // each dish's sale volume  from order_detail and order is valid
        List<GoodsSalesDTO> goodsSalesDTOList = reportMapper.getSalesTopStatistics(beginTime,endTime);
        // get nameList
        List<String> nameList = goodsSalesDTOList.stream().map(a -> a.getName()).collect(Collectors.toList());
        // get numberList
        List<Integer> numberList = goodsSalesDTOList.stream().map(a -> a.getNumber()).collect(Collectors.toList());

        // construct VO
        SalesTop10ReportVO salesTop10ReportVO = SalesTop10ReportVO
                .builder()
                .nameList(StringUtils.join(nameList,","))
                .numberList(StringUtils.join(numberList,","))
                .build();
        return salesTop10ReportVO;
    }

    /**
     * export business data
     * @param response
     */

    @Override
    public void export(HttpServletResponse response) {
        // get data
        LocalDateTime beginTime = LocalDateTime.of(
                LocalDate.now().minusDays(30), LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(
                LocalDate.now().minusDays(1), LocalTime.MAX);
        BusinessDataVO businessDataVO = workspaceService.getBusinessData(
                beginTime,endTime);

        // write to Excel file via POI
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("template/operation_data_report_template.xlsx");

        try {
            // create a new file based on template
            XSSFWorkbook excel = new XSSFWorkbook(in);

            // get sheet
            XSSFSheet sheet = excel.getSheet("Sheet1");
            // fill data
            sheet.getRow(1).getCell(1).setCellValue("Time from "+beginTime+" to "+endTime);
            // row 4
            XSSFRow row = sheet.getRow(3);
            row.getCell(2).setCellValue(businessDataVO.getTurnover());
            row.getCell(4).setCellValue(businessDataVO.getOrderCompletionRate());
            row.getCell(6).setCellValue(businessDataVO.getNewUsers());
            // row 5
            row = sheet.getRow(4);
            row.getCell(2).setCellValue(businessDataVO.getValidOrderCount());
            row.getCell(4).setCellValue(businessDataVO.getUnitPrice());
            // row 8 - Fill in detailed data

            for (int i = 0; i < 30; i++) {
                LocalDateTime todayBegin = beginTime.plusDays(i);
                LocalDateTime todayEnd = LocalDateTime.of(todayBegin.toLocalDate(),LocalTime.MAX);

                // get data today
                BusinessDataVO businessData = workspaceService.getBusinessData(todayBegin,todayEnd);
                // fill data
                row = sheet.getRow(7 + i);
                row.getCell(1).setCellValue(todayBegin.toString());
                row.getCell(2).setCellValue(businessData.getTurnover());
                row.getCell(3).setCellValue(businessData.getValidOrderCount());
                row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
                row.getCell(5).setCellValue(businessData.getUnitPrice());
                row.getCell(6).setCellValue(businessData.getNewUsers());
            }

            // download file through outStream to web browser
            ServletOutputStream outputStream = response.getOutputStream();
            excel.write(outputStream);

            // close resources
            outputStream.close();
            excel.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * helper method to get dateList
     * @param begin
     * @param end
     * @return
     */
    private List<LocalDate> getDateList(LocalDate begin, LocalDate end){
        //get dateList, to string
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!Objects.equals(begin, end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        return dateList;
    }
}
