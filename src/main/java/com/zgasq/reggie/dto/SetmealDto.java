package com.zgasq.reggie.dto;

import com.zgasq.reggie.entity.Setmeal;
import com.zgasq.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
