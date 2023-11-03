package com.code.rent.service;

import com.code.rent.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.code.rent.entity.dto.LoginParam;
import com.code.rent.entity.dto.UserDTO;
import com.code.rent.entity.vo.UserVO;

/**
* @author HeXin
* @description 针对表【user】的数据库操作Service
* @createDate 2023-10-29 14:58:39
*/
public interface UserService extends IService<User> {
    /**
      * 登录功能（用户登录）
      */
    String login(LoginParam login);

    /**
      * 根据用户名获取用户
      */
    User getUserByName(String username);

    /**
      * 登录功能（管理员登录）
      */
    String loginAdmin(LoginParam param);

    /**
      * 获得用户角色
      */
    String  getUserRole(User user);

    /**
      * 获取角色权限
      */
    String getUserPermission(User user);

    /**
      * 注册用户
      */
    Boolean regist(UserDTO dto, String code);
}
