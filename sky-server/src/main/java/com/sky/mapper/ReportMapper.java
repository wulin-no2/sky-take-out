package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface ReportMapper {

    BigDecimal getTurnoverByMap(Map map);

    Integer getTotalUser(LocalDateTime endTime);

    Integer getNewUser(LocalDateTime beginTime, LocalDateTime endTime);

    Integer getOrderCountByMap(Map map);
}
