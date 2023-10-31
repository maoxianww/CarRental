package com.code.rent.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.code.rent.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * @author HeXin
 * @TableName order
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="order")
@Data
@Accessors(chain = true)
@Schema(name = "用户类",description = "用户实体类")
public class Order extends BaseEntity implements Serializable{

    /**
     * 订单用户id
     */
    @TableField(value = "uid")
    private Long uid;

    /**
     * 租赁车辆id
     */
    @TableField(value = "vehicle_id")
    private Long vehicleId;

    /**
     * 租赁开始日期
     */
    @TableField(value = "start")
    private Date start;

    /**
     * 租赁结束日期
     */
    @TableField(value = "end")
    private Date end;

    /**
     * 车辆归还日期
     */
    @TableField(value = "return_time")
    private Date returnTime;

    /**
     * 总租金费用
     */
    @TableField(value = "total_cost")
    private BigDecimal totalCost;

    /**
     * 订单状态(0: 未支付 1: 已支付 2: 已完成 3: 已取消 4: 订单异常)
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 订单信息(备注)
     */
    @TableField(value = "illustrate")
    private String illustrate;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}