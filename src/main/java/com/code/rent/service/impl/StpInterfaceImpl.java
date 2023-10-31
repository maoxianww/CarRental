package com.code.rent.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.code.rent.entity.User;
import com.code.rent.exception.CustomException;
import com.code.rent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        Integer type = user.getType();
        List<String> list = new ArrayList<>();
        switch (type) {
            case 0 -> list.add("*");
            case 1 -> list.add("vip-user");
            case 2 -> list.add("ordinary-user");
            default -> throw new CustomException("系统异常！");
        }
        return list;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginKey) {
        User user = userService.getById(loginId.toString());
        Integer type = user.getType();
        List<String> list = new ArrayList<>();
        switch (type) {
            case 0 -> list.add("admin");
            case 1 -> list.add("vip");
            case 2 -> list.add("user");
            default -> throw new CustomException("系统异常！");
        }
        return list;
    }
}
