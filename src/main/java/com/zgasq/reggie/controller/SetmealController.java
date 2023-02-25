package com.zgasq.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zgasq.reggie.common.R;
import com.zgasq.reggie.dto.SetmealDto;
import com.zgasq.reggie.entity.Category;
import com.zgasq.reggie.entity.Setmeal;
import com.zgasq.reggie.entity.SetmealDish;
import com.zgasq.reggie.service.CategoryService;
import com.zgasq.reggie.service.DishService;
import com.zgasq.reggie.service.SetmealDishService;
import com.zgasq.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public R<String> add(@RequestBody SetmealDto dto){
        setmealService.saveWithDish(dto);
        return R.success("添加套餐成功");
    }

    @GetMapping("/page")
    private R<Page> page(int page, int pageSize, String name){
        Page<Setmeal> setmealPage = new Page<>(page,pageSize);
        Page<SetmealDto> setmealPageDto = new Page<>();
        BeanUtils.copyProperties(setmealPage,setmealPageDto,"records");
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper =new LambdaQueryWrapper<>();

        lambdaQueryWrapper.like(name!=null,Setmeal::getName,name);
        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(setmealPage,lambdaQueryWrapper);
        List<Setmeal> records = setmealPage.getRecords();
        List<SetmealDto> dtoList =
        records.stream().map((item)->{
            Category byId = categoryService.getById(item.getCategoryId());
            SetmealDto setmealDto = new SetmealDto();
            if(byId!=null){
                BeanUtils.copyProperties(item,setmealDto);
                setmealDto.setCategoryName(byId.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());
        setmealPageDto.setRecords(dtoList);
        return R.success(setmealPageDto);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("套餐删除成功");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper =new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        setmealLambdaQueryWrapper.eq(setmeal.getStatus()!=0,Setmeal::getStatus,setmeal.getStatus());
        setmealLambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(setmealLambdaQueryWrapper);
        return R.success(list);
    }
}
