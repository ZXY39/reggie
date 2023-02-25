package com.zgasq.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zgasq.reggie.dto.SetmealDto;
import com.zgasq.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto dto);

    public void removeWithDish(List<Long> ids);
}
