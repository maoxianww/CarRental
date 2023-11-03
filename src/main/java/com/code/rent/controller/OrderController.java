package com.code.rent.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.code.rent.common.Result;
import com.code.rent.entity.Order;
import com.code.rent.entity.dto.OrderDTO;
import com.code.rent.entity.vo.OrderVO;
import com.code.rent.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单模块")
@RestController
@SaCheckLogin
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Operation(summary = "创建订单")
    @PostMapping("/create")
    public Result<OrderVO> createOrder(@RequestParam OrderDTO dto){
        return Result.success(orderService.create(dto));
    }

    @Operation(summary = "根据用户id获取订单详情")
    @GetMapping("/details/{uid}")
    public Result<OrderVO> getOrderDetailsByUid(@PathVariable Long uid){
        return Result.success(Order.toVo(orderService.lambdaQuery().eq(Order::getUid,uid).one()));
    }

    @Operation(summary = "根据订单id获取订单")
    @GetMapping("/details/{id}")
    public Result<OrderVO> getOrderById(@PathVariable Long id){
        return Result.success(Order.toVo(orderService.getById(id)));
    }
}
