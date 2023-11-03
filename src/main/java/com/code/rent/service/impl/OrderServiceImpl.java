package com.code.rent.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.rent.entity.Order;
import com.code.rent.entity.Vehicle;
import com.code.rent.entity.dto.OrderDTO;
import com.code.rent.entity.vo.OrderVO;
import com.code.rent.service.OrderService;
import com.code.rent.mapper.OrderMapper;
import com.code.rent.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

/**
* @author HeXin
* @description 针对表【order】的数据库操作Service实现
* @createDate 2023-10-29 14:58:39
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

    private Random random = new Random();
    @Autowired
    private VehicleService vehicleService;

    @Override
    public OrderVO create(OrderDTO dto) {
        OrderVO vo = new OrderVO();
        // 查询订单中车辆的日租金
        Vehicle vehicle = vehicleService.lambdaQuery().eq(Vehicle::getId, dto.getVehicleId()).one();
        // 计算正常还车天数
        long normal = DateUtil.betweenDay(dto.getStart(), dto.getEnd(), true);
        // 计算实际还车天数
        long actual = DateUtil.betweenDay(dto.getStart(), dto.getReturnTime(), true);
        if(actual > normal){
            // 如果归还时间超过正常还车时间，则超过一天的日租金按照正常日租金的1.5倍支付
            vo.setTotalCost(vehicle.getDailyRate().multiply (BigDecimal.valueOf(normal).add(BigDecimal.valueOf(actual - normal).multiply(BigDecimal.valueOf(1.5)))));
        }else{
            vo.setTotalCost(vehicle.getDailyRate().multiply(BigDecimal.valueOf(normal)));
        }
        // 时间戳加随机数生成订单id
        vo.setId(String.valueOf(System.currentTimeMillis()+random.nextInt(99999)));
        vo.setStart(dto.getStart());
        vo.setEnd(dto.getEnd());
        vo.setUid(StpUtil.getLoginIdAsLong());
        vo.setIllustrate(dto.getIllustrate());
        vo.setVehicleId(dto.getVehicleId());
        save(OrderVO.toPo(vo));
        // 更新车辆可用性状态
        vehicle.setAvailable(0);
        vehicleService.updateById(vehicle);
        return null;
    }
}




