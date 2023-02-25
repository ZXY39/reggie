package com.zgasq.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgasq.reggie.dto.DishDto;
import com.zgasq.reggie.entity.Dish;
import com.zgasq.reggie.entity.DishFlavor;
import com.zgasq.reggie.mapper.DishMapper;
import com.zgasq.reggie.service.DishFlavorService;
import com.zgasq.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);

        Long id = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.stream().map((item) ->{
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);

    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        Dish byId = getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(byId,dishDto);
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getDishId, byId.getId());
        List<DishFlavor> list = dishFlavorService.list(lambdaQueryWrapper);
        dishDto.setFlavors(list);
        return dishDto;
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        this.updateById(dishDto);
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors= flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper =new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
        dishFlavorService.remove(dishFlavorLambdaQueryWrapper);
        dishFlavorService.saveBatch(flavors);
    }
}
