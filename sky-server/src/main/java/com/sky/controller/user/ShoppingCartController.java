package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user/shoppingCart")
@Api(tags = "shopping cart interfaces")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @PostMapping("/add")
    @ApiOperation("add dish to shopping cart")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("add dish to shopping cart{}",shoppingCartDTO);
        shoppingCartService.add(shoppingCartDTO);

        return Result.success();
    }
}
