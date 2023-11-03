package com.code.rent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.code.rent.entity.base.BaseEntity;
import com.code.rent.entity.vo.UserVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * @author HeXin
 * @TableName user
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="user")
@Data
@Accessors(chain = true)
public class User extends BaseEntity implements Serializable {

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 用户昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 头像
     */
    @TableField(value = "avater")
    private String avater;

    /**
     * 邮箱地址
     */
    @TableField(value = "email")
    private String email;

    /**
     * 用户所在地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 用户类型(0：管理员 1：VIP用户  2：普通用户)
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 用户状态(0：禁用 1：启用，默认为1)
     */
    @TableField(value = "state")
    private Integer state;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public static UserVO toVo(User user){

        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUserName(user.getUserName());
        userVO.setPassword(user.getPassword());
        userVO.setNickname(user.getNickname());
        userVO.setAvater(user.getAvater());
        userVO.setEmail(user.getEmail());
        userVO.setAddress(user.getAddress());
        userVO.setType(user.getType());
        userVO.setState(user.getState());
        return userVO;
    }
}