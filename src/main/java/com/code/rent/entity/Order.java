package com.code.rent.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.code.rent.entity.base.BaseEntity;
import com.code.rent.entity.vo.OrderVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author HeXin
 * @TableName order
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="`order`")
@Data
@Accessors(chain = true)
// @Schema(name = "用户类",description = "用户实体类")
public class Order extends BaseEntity implements Serializable{

    /**
     * 订单用户id
     */
    @TableField(value = "uid")
    private String uid;

    /**
     * 租赁车辆id
     */
    @TableField(value = "vehicle_id")
    private String vehicleId;

    /**
     * 租赁开始日期
     */
    @TableField(value = "`start`")
    private Date start;

    /**
     * 租赁结束日期
     */
    @TableField(value = "`end`")
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
     * 订单状态(0: 未支付  1: 已支付  2: 已完成  3: 已取消  4: 订单异常  5: 取消订单)
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 订单信息(备注)
     */
    @TableField(value = "illustrate")
    private String illustrate;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public static OrderVO toVo(Order order){

        if (order == null) {
            return null;
        }
        OrderVO orderVO = new OrderVO();
        orderVO.setId(order.getId());
        orderVO.setUid(order.getUid());
        orderVO.setVehicleId(order.getVehicleId());
        orderVO.setStart(order.getStart());
        orderVO.setEnd(order.getEnd());
        orderVO.setIllustrate(order.getIllustrate());
        orderVO.setReturnTime(order.getReturnTime());
        orderVO.setTotalCost(order.getTotalCost());
        orderVO.setStatus(order.getStatus());

        return orderVO;
    }
}