package com.zgasq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgasq.reggie.entity.AddressBook;
import com.zgasq.reggie.mapper.AddressBookMapper;
import com.zgasq.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
