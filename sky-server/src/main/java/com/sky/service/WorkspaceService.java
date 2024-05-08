package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkspaceService {

    /**
     * get turnover based on time range
     * @param begin
     * @param end
     * @return
     */
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    /**
     * get order overview
     * @return
     */
    OrderOverViewVO getOrderOverView();

    /**
     * get dishes overview
     * @return
     */
    DishOverViewVO getDishOverView();

    /**
     * get setmeal overview
     * @return
     */
    SetmealOverViewVO getSetmealOverView();

}

