package com.code.rent.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.rent.constants.CodeEnum;
import com.code.rent.entity.Order;
import com.code.rent.entity.Vehicle;
import com.code.rent.entity.dto.OrderDTO;
import com.code.rent.entity.vo.OrderVO;
import com.code.rent.exception.CustomException;
import com.code.rent.mapper.OrderMapper;
import com.code.rent.service.OrderService;
import com.code.rent.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

/**
* @author HeXin
* @description 针对表【order】的数据库操作Service实现
* @createDate 2023-10-29 14:58:39
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

    private final Random random = new Random();
    @Autowired
    private VehicleService vehicleService;

    @Override
    public OrderVO create(OrderDTO dto) {
        // 查询订单中车辆的日租金
        Vehicle vehicle = vehicleService.lambdaQuery().eq(Vehicle::getId, dto.getVehicleId()).one();
        OrderVO vo = new OrderVO();
        // 时间戳加随机数生成订单id
        vo.setId(String.valueOf(System.currentTimeMillis()+random.nextInt(99999)));
        vo.setStart(dto.getStart());
        vo.setEnd(dto.getEnd());
        vo.setUid(StpUtil.getLoginIdAsString());
        vo.setIllustrate(dto.getIllustrate());
        vo.setVehicleId(vehicle.getId());
        vo.setStatus(0);
        Order order = OrderVO.toPo(vo);
        save(order);
        // 更新车辆可用性状态
        vehicle.setAvailable(0);
        vehicleService.updateById(vehicle);
        return null;
    }

    @Override
    public OrderVO finish(String orderId) {
        Order order = getById(orderId);
        // 判断订单是否异常
        if(order.getStatus().equals(4)){
            throw new CustomException(CodeEnum.ORDER_ABNORMAL);
        }
        // 查询订单中车辆的日租金
        Vehicle vehicle = vehicleService.lambdaQuery().eq(Vehicle::getId, order.getVehicleId()).one();
        // 计算正常还车天数
        long normal = DateUtil.betweenDay(order.getStart(), order.getEnd(), true);
        // 计算实际还车天数
        long actual = DateUtil.betweenDay(order.getStart(), new Date(), true);
        if(actual > normal){
            // 如果归还时间超过正常还车时间，则超过一天的日租金按照正常日租金的1.5倍支付
            order.setTotalCost(vehicle.getDailyRate().multiply (BigDecimal.valueOf(normal).add(BigDecimal.valueOf(actual - normal).multiply(BigDecimal.valueOf(1.5)))));
        }else{
            order.setTotalCost(vehicle.getDailyRate().multiply(BigDecimal.valueOf(normal)));
        }
        // 设置还车时间
        order.setReturnTime(new Date());
        order.setStatus(2);
        OrderVO vo = Order.toVo(order);
        updateById(order);
        return vo;
    }

    @Override
    public Boolean update(String id, Date start, Date end) {
        Order order = getById(id);
        // 判断订单是否已经修改过
        if(order.getStatus().equals(5)){
            throw new CustomException(CodeEnum.ORDER_ALREADY_MODIFICATION);
        }else if(order.getStatus().equals(4)){
            throw new CustomException(CodeEnum.ORDER_ABNORMAL);
        }
        // 标记订单已修改
        order.setStatus(5);
        // 判断订单创建距现在过了多长时间
        Date create = order.getGmtCreate();
        long time = DateUtil.between(create, new Date(), DateUnit.MINUTE);
        if(time > 30){
            throw new CustomException(CodeEnum.ORDER_EXPIRED_MODIFICATION);
        }
        order.setStart(start);
        order.setEnd(end);
        return updateById(order);
    }
}




