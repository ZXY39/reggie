package com.zgasq.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgasq.reggie.common.CustomException;
import com.zgasq.reggie.entity.Category;
import com.zgasq.reggie.entity.Dish;
import com.zgasq.reggie.entity.Setmeal;
import com.zgasq.reggie.mapper.CategoryMapper;
import com.zgasq.reggie.service.CategoryService;
import com.zgasq.reggie.service.DishService;
import com.zgasq.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(queryWrapper);
        if(count>0){
            throw new CustomException("当前分类已关联菜品，无法删除");
        }
        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        setmealQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count(setmealQueryWrapper);
        if(count1>0){
            throw new CustomException("当前分类已关联套餐，无法删除");
        }

        super.removeById(id);
    }
}
