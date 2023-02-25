package com.zgasq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgasq.reggie.entity.ShoppingCart;
import com.zgasq.reggie.mapper.ShoppingCartMapper;
import com.zgasq.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>implements ShoppingCartService {
}
