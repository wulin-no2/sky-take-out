package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.ReportMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportMapper reportMapper;

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
