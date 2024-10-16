package com.code.rent.entity.dto;

import com.code.rent.entity.Vehicle;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VehicleDTO {
    // @Schema(description = "id")
    private String id;
    // @Schema(description = "车辆品牌")
    private String brand;
    // @Schema(description = "车辆型号")
    private String model;
    // @Schema(description = "车牌号")
    private String license;
    // @Schema(description = "车辆类型(0: 轿车 1: 客车 2: 货车)")
    private Integer type;
    // @Schema(description = "车辆日租金")
    private BigDecimal dailyRate;
    // @Schema(description = "车辆信息描述")
    private String illustrate;
    // @Schema(description = "车辆图片")
    private String avatar;
    // @Schema(description = "车辆可用性状态(0:已租出 1:未租出 2:车辆异常)")
    private Integer available;

    public static Vehicle toPo(VehicleDTO vehicleDTO){

        if (vehicleDTO == null) {
            return null;
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleDTO.getId());
        vehicle.setBrand(vehicleDTO.getBrand());
        vehicle.setModel(vehicleDTO.getModel());
        vehicle.setLicense(vehicleDTO.getLicense());
        vehicle.setType(vehicleDTO.getType());
        vehicle.setDailyRate(vehicleDTO.getDailyRate());
        vehicle.setIllustrate(vehicleDTO.getIllustrate());
        vehicle.setAvatar(vehicleDTO.getAvatar());
        vehicle.setAvailable(vehicleDTO.getAvailable());
        return vehicle;
    }
}
