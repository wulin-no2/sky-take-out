package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.ReportMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
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

    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) {
        //get dateList, to string
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!Objects.equals(begin, end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
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
}
