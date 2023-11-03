package com.code.rent.entity.dto;

import com.code.rent.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserDTO {
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

    public static User toPo(UserDTO dto){

        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setPassword(dto.getPassword());
        user.setNickname(dto.getNickname());
        user.setAvater(dto.getAvater());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setType(dto.getType());
        user.setState(dto.getState());
        return user;
    }
}
