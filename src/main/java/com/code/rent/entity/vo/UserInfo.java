package com.code.rent.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserInfo {
    @Schema(description = "用户角色")
    private String role;
    @Schema(description = "用户头像")
    private String avatar;
    @Schema(description = "用户名称")
    private String name;
}
