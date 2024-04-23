package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "shop interfaces")
@RequestMapping("/admin/shop")
@Slf4j
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * get shop status in Redis
     * @return
     */
    @GetMapping("/status")
    @ApiOperation(value = "get shop status")
    public Result<Integer> getShopStatus(){

        Integer shopStatus = (Integer)redisTemplate.opsForValue().get("SHOP_STATUS");
        log.info("get shop status {}",shopStatus);
        return Result.success(shopStatus);
    }

    /**
     * set shop status in Redis
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation(value = "set shop status")
    public Result setShopStatus(@PathVariable Integer status){
        log.info("set shop status,{}",status==1?"on":"off");
        redisTemplate.opsForValue().set("SHOP_STATUS",status);
        return Result.success();
    }
}
