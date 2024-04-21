package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user) values " +
            "(#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void addCategory(Category category);

    Page<Category> listCategoryByPage(CategoryPageQueryDTO categoryPageQueryDTO);
    @AutoFill(value = OperationType.UPDATE)
    void updateCategory(Category category);
    @Delete("delete from category where id = #{id}")
    void deleteCategory(Long id);
    @Select("select * from category where type = #{type}")
    List<Category> getCategoryByType(Integer type);
    @Select("select * from category where id = #{categoryId}")
    Category getCategoryByCategoryId(Long categoryId);
}
