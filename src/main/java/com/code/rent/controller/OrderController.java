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

/**
 * 订单控制器
 *
 * @author HeXin
 * @date 2023/11/12
 */
@Tag(name = "订单模块")
@RestController
@SaCheckLogin
@RequestMapping("/order")
public class OrderController {

    /**
     * 订购服务
     */
    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     *
     * @param dto DTO
     * @return {@link Result}<{@link OrderVO}>
     */
    @Operation(summary = "创建订单")
    @PostMapping("/create")
    public Result<OrderVO> createOrder(@RequestBody OrderDTO dto){
        return Result.success(orderService.create(dto));
    }

    /**
     * 完成订单
     *
     * @param orderId 订单编号
     * @return {@link Result}<{@link OrderVO}>
     */
    @Operation(summary = "完成订单")
    @PostMapping("/finish/{orderId}")
    public Result<OrderVO> finishOrder(@PathVariable String orderId){
        return Result.success(orderService.finish(orderId));
    }

    /**
     * 通过UID获取订单详细信息
     *
     * @param uid uid
     * @return {@link Result}<{@link OrderVO}>
     */
    @Operation(summary = "根据用户id获取订单详情")
    @GetMapping("/details/{uid}")
    public Result<OrderVO> getOrderDetailsByUid(@PathVariable Long uid){
        return Result.success(Order.toVo(orderService.lambdaQuery().eq(Order::getUid,uid).one()));
    }

    /**
     * 按 ID 获取订单
     *
     * @param id 编号
     * @return {@link Result}<{@link OrderVO}>
     */
    @Operation(summary = "根据订单id获取订单")
    @GetMapping("/details/{id}")
    public Result<OrderVO> getOrderById(@PathVariable Long id){
        return Result.success(Order.toVo(orderService.getById(id)));
    }
}
