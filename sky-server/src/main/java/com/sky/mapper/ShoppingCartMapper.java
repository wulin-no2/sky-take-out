package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    List<ShoppingCart> list(ShoppingCart shoppingCart);
    @Update("update shopping_cart set number = #{number} where id=#{id}")
    void updateNumber(ShoppingCart shoppingCart1);

    void insert(ShoppingCart shoppingCart);
    @Delete("delete from shopping_cart where user_id = #{currentId}")
    void deleteBatchById(Long currentId);
    @Delete("delete from shopping_cart where id = #{id}")
    void deleteById(Long id);
}
