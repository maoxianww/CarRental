package com.code.rent.service;

import com.code.rent.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.code.rent.entity.dto.LoginParam;

/**
* @author HeXin
* @description 针对表【user】的数据库操作Service
* @createDate 2023-10-29 14:58:39
*/
public interface UserService extends IService<User> {
    /**
      * 登录功能
      */
    String login(LoginParam login);

    /**
      * 根据用户名获取用户
      */
    User getUserByName(String username);
}
