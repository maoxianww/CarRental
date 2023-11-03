package com.code.rent.entity.vo;


import com.code.rent.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserVO {

    private String id;
    @Schema(description = "用户名")
    private String userName;
    @Schema(description = "密码")
    private String password;
    @Schema(description = "昵称")
    private String nickname;
    @Schema(description = "头像")
    private String avater;
    @Schema(description = "邮箱")
    private String email;
    @Schema(description = "用户所在地址")
    private String address;
    @Schema(description = "用户类型（0：管理员 1：vip用户 2：普通用户）")
    private Integer type;
    @Schema(description = "用户状态(0：禁用 1：启用，默认为1)")
    private Integer state;

    public static User toPo(UserVO vo){
        if (vo == null) {
            return null;
        }
        User user = new User();
        user.setUserName(vo.getUserName());
        user.setPassword(vo.getPassword());
        user.setNickname(vo.getNickname());
        user.setAvater(vo.getAvater());
        user.setEmail(vo.getEmail());
        user.setAddress(vo.getAddress());
        user.setType(vo.getType());
        user.setState(vo.getState());
        user.setId(vo.getId());
        return user;
    }
}
