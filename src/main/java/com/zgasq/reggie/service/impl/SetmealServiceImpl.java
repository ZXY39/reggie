package com.zgasq.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgasq.reggie.common.CustomException;
import com.zgasq.reggie.dto.SetmealDto;
import com.zgasq.reggie.entity.Setmeal;
import com.zgasq.reggie.entity.SetmealDish;
import com.zgasq.reggie.mapper.SetmealMapper;
import com.zgasq.reggie.service.SetmealDishService;
import com.zgasq.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;
    @Override
    public void saveWithDish(SetmealDto dto) {
        save(dto);
        List<SetmealDish> setmealDishes = dto.getSetmealDishes();
        Long id = dto.getId();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(id);
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }

    @Transactional
    @Override
    public void removeWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper =new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId,ids);
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus,1);
        int count = count(setmealLambdaQueryWrapper);
        if(count>0){
            throw new CustomException("存在正在售卖的套餐，无法删除");
        }
        removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper =new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(setmealDishLambdaQueryWrapper);
    }
}
