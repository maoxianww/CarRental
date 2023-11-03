package com.code.rent.entity.base;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @author HeXin
 */
@Data
public class BaseEntity {

    /**
      * id
      */
    @TableId(value = "id",type = IdType.AUTO)
    private String id;
    /**
     * 创建时间
     */
    @TableField(value = "gmt_create",fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @TableField(value = "gmt_modified",fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    /**
     * 创建者
     */
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 更新着
     */
    @TableField(value = "update_by",fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    /**
     * 逻辑删除
     */
    @TableField(value = "is_deleted")
    @TableLogic(value = "0",delval = "1")
    private Integer isDeleted;
}
