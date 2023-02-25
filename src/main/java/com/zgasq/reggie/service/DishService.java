package com.zgasq.reggie.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zgasq.reggie.dto.DishDto;
import com.zgasq.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);

}
