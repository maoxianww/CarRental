package com.code.rent.entity.vo;

import com.code.rent.entity.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class VehicleVO {
    private String id;
    @Schema(description = "车辆品牌")
    private String brand;
    @Schema(description = "车辆型号")
    private String model;
    @Schema(description = "车牌号")
    private String license;
    @Schema(description = "车辆类型(0: 轿车 1: 客车 2: 货车)")
    private Integer type;
    @Schema(description = "车辆日租金")
    private BigDecimal dailyRate;
    @Schema(description = "车辆信息描述")
    private String illustrate;
    @Schema(description = "车辆图片")
    private String avatar;
    @Schema(description = "车辆可用性状态(0:已租出 1:未租出 2:车辆异常)")
    private Integer available;

    public static Vehicle toPo(VehicleVO vo){

        if (vo == null) {
            return null;
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(vo.getBrand());
        vehicle.setModel(vo.getModel());
        vehicle.setLicense(vo.getLicense());
        vehicle.setType(vo.getType());
        vehicle.setDailyRate(vo.getDailyRate());
        vehicle.setIllustrate(vo.getIllustrate());
        vehicle.setAvatar(vo.getAvatar());
        vehicle.setAvailable(vo.getAvailable());
        vehicle.setId(vo.getId());
        return vehicle;
    }
}
