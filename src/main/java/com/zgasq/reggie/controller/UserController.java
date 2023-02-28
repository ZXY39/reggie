package com.zgasq.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zgasq.reggie.common.R;
import com.zgasq.reggie.entity.User;
import com.zgasq.reggie.service.UserService;
import com.zgasq.reggie.utils.SMSUtils;
import com.zgasq.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            String s = ValidateCodeUtils.generateValidateCode(6).toString();
            log.info("code= {}",s);
            session.setAttribute(phone,s);
            redisTemplate.opsForValue().set(phone,s,5, TimeUnit.MINUTES);
            return R.success("短信验证码发送成功");

        }
        return R.error("短信验证码发送失败");
    }
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){

        if(map.size()>0){
            String phone =map.get("phone").toString();
            String code =map.get("code").toString();
            //Object attribute = session.getAttribute(phone);
            Object attribute = redisTemplate.opsForValue().get(phone);

            if(attribute!=null &&attribute.equals(code)){
                LambdaQueryWrapper<User> userLambdaQueryWrapper =new LambdaQueryWrapper<>();
                userLambdaQueryWrapper.eq(User::getPhone,phone);
                User user = userService.getOne(userLambdaQueryWrapper);
                if(user ==null){
                    user = new User();
                    user.setPhone(phone);
                    userService.save(user);
                }
                session.setAttribute("user",user.getId());
                redisTemplate.delete(phone);
                return R.success(user);
            }
        }
        return R.error( "验证码错误");
    }

}
