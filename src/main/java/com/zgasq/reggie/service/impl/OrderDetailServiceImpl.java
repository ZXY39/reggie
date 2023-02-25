package com.zgasq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgasq.reggie.entity.OrderDetail;
import com.zgasq.reggie.mapper.OrderDetailMapper;
import com.zgasq.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>implements OrderDetailService {
}
