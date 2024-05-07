package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
@Api(tags = "statistics interfaces")
@Slf4j
public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * turnover Statistics
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/turnoverStatistics")
    @ApiOperation("turnover Statistics")
    public Result<TurnoverReportVO> turnoverStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("turnover Statistics, from {} to {}",begin,end);
        TurnoverReportVO turnoverReportVO = reportService.turnoverStatistics(begin,end);
        return Result.success(turnoverReportVO);
    }

    /**
     * user Statistics
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/userStatistics")
    @ApiOperation("user Statistics")
    public Result<UserReportVO> userStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                               @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("userStatistics,from {} to {} ",begin,end);
        UserReportVO userReportVO = reportService.userStatistics(begin,end);
        return Result.success(userReportVO);
    }

    /**
     * orders Statistics
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/ordersStatistics")
    @ApiOperation("orders Statistics")
    public Result<OrderReportVO> orderStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("orderStatistics,from {} to {} ",begin,end);
        OrderReportVO orderReportVO = reportService.orderStatistics(begin,end);
        return Result.success(orderReportVO);
    }

    /**
     * sales Top 10 Statistics
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/top10")
    @ApiOperation("sales Top 10 Statistics")
    public Result<SalesTop10ReportVO> salesTopStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                         @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("sales Top 10 Statistics from {} to {} ",begin,end);
        SalesTop10ReportVO salesTop10ReportVO = reportService.salesTopStatistics(begin,end);
        return Result.success(salesTop10ReportVO);
    }
}
