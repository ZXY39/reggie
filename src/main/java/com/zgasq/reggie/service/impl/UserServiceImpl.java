package com.zgasq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgasq.reggie.entity.User;
import com.zgasq.reggie.mapper.UserMapper;
import com.zgasq.reggie.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
