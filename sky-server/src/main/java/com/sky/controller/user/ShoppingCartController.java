package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user/shoppingCart")
@Api(tags = "shopping cart interfaces")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * add dish to shopping cart
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("add dish to shopping cart")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("add dish to shopping cart{}",shoppingCartDTO);
        shoppingCartService.add(shoppingCartDTO);
        return Result.success();
    }

    /**
     * list shopping cart items
     * @return shopping cart list
     */
    @GetMapping("/list")
    @ApiOperation("list shopping cart items")
    public Result<List<ShoppingCart>> list(){
        log.info("list shopping cart items");
        List<ShoppingCart> list = shoppingCartService.list();
        return Result.success(list);
    }

    /**
     * clean shopping car
     * @return
     */
    @DeleteMapping("/clean")
    @ApiOperation("clean shopping cart")
    public Result clean(){
        log.info("clean shopping cart");
        shoppingCartService.clean();
        return Result.success();
    }
}
