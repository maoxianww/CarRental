package com.code.rent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.code.rent.entity.base.BaseEntity;
import com.code.rent.entity.vo.VehicleVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * @author HeXin
 * @TableName vehicle
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="vehicle")
@Data
@Accessors(chain = true)
public class Vehicle extends BaseEntity implements Serializable {

    /**
     * 车辆品牌
     */
    @TableField(value = "brand")
    private String brand;

    /**
     * 车辆型号
     */
    @TableField(value = "model")
    private String model;

    /**
      * 车牌号
      */
    @TableField(value = "license")
    private String license;

    /**
     * 车辆类型(0: 轿车 1: 客车 2: 货车)
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 车辆日租金
     */
    @TableField(value = "daily_rate")
    private BigDecimal dailyRate;

    /**
     * 车辆信息描述
     */
    @TableField(value = "illustrate")
    private String illustrate;

    /**
     * 车辆图片
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 车辆可用性状态(0:已租出 1:未租出 2:车辆异常)
     */
    @TableField(value = "available")
    private Integer available;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public static VehicleVO toVo(Vehicle vehicle){

        if (vehicle == null) {
            return null;
        }
        VehicleVO vehicleVO = new VehicleVO();
        vehicleVO.setId(vehicle.getId());
        vehicleVO.setBrand(vehicle.getBrand());
        vehicleVO.setModel(vehicle.getModel());
        vehicleVO.setLicense(vehicle.getLicense());
        vehicleVO.setType(vehicle.getType());
        vehicleVO.setDailyRate(vehicle.getDailyRate());
        vehicleVO.setIllustrate(vehicle.getIllustrate());
        vehicleVO.setAvatar(vehicle.getAvatar());
        vehicleVO.setAvailable(vehicle.getAvailable());
        return vehicleVO;
    }
}