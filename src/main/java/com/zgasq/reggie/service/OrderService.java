package com.zgasq.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zgasq.reggie.entity.Orders;

public interface OrderService extends IService<Orders> {

    public void submit(Orders orders);
}
