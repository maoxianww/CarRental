package com.code.rent.entity.vo;

import com.code.rent.entity.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class OrderVO {
    private String id;
    @Schema(description = "用户id")
    private Long uid;
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
    @Schema(description = "总租金费用")
    private BigDecimal totalCost;

    public static Order toPo(OrderVO vo){

        if (vo == null) {
            return null;
        }
        Order order = new Order();
        order.setUid(vo.getUid());
        order.setVehicleId(vo.getVehicleId());
        order.setStart(vo.getStart());
        order.setEnd(vo.getEnd());
        order.setReturnTime(vo.getReturnTime());
        order.setTotalCost(vo.getTotalCost());
        order.setIllustrate(vo.getIllustrate());
        order.setId(vo.getId());
        return order;
    }
}
