package com.code.rent.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.code.rent.constants.RedisConstants;
import com.code.rent.entity.User;
import com.code.rent.exception.CustomException;
import com.code.rent.service.UserService;
import com.code.rent.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * sa-token 权限认证
 * @author HeXin
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private UserService userService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginKey) {
        User user = userService.getById(loginId.toString());
        List<String> list = new ArrayList<>();
        list.add(userService.getUserPermission(user));
        return list;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginKey) {
        User user = userService.getById(loginId.toString());
        List<String> list = new ArrayList<>();
        list.add(userService.getUserRole(user));
        return list;
    }
}
