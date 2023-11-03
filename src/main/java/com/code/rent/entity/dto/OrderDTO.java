package com.code.rent.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO {
    @Schema(description = "车辆id")
    private Long vehicleId;
    @Schema(description = "车辆租赁开始时间")
    private Date start;
    @Schema(description = "车辆租赁结束时间")
    private Date end;
    @Schema(description = "车辆信息描述")
    private String illustrate;
    @Schema(description = "车辆归还日期")
    private Date returnTime;
}
