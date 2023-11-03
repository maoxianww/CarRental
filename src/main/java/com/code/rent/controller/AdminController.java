package com.code.rent.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.rent.common.PageInfo;
import com.code.rent.common.Result;
import com.code.rent.entity.Order;
import com.code.rent.entity.User;
import com.code.rent.entity.Vehicle;
import com.code.rent.entity.dto.LoginParam;
import com.code.rent.entity.dto.UserDTO;
import com.code.rent.entity.dto.VehicleDTO;
import com.code.rent.service.OrderService;
import com.code.rent.service.UserService;
import com.code.rent.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author HeXin
 */
@RestController
@RequestMapping("/admin")
@SaCheckLogin
@SaCheckRole("admin")
@Tag(name = "管理员模块")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private OrderService orderService;
    @Operation(summary = "登录功能")
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginParam param){
        return Result.success(userService.loginAdmin(param));
    }
    @Operation(summary = "根据id查询用户")
    @GetMapping("/user/{id}")
    public Result<User> getById(@PathVariable Long id){
        return Result.success(userService.getById(id));
    }
    @Operation(summary = "增加用户")
    @PostMapping("/save/user")
    public Result saveUser(@RequestBody UserDTO user){
        return Result.isSuccess(userService.save(UserDTO.toPo(user)));
    }
    @Operation(summary = "修改用户信息")
    @PutMapping("/update/user")
    public Result updateUser(@RequestBody UserDTO user){
        return Result.isSuccess(userService.updateById(UserDTO.toPo(user)));
    }
    @Operation(summary = "退出登录")
    @SaIgnore
    @GetMapping("/logout")
    public Result<String> logout() {
        StpUtil.logout(StpUtil.getLoginId());
        return Result.success();
    }
    @Operation(summary = "分页查询用户结果")
    @GetMapping("/user/page")
    public Result<PageInfo<User>> userPage(@RequestParam(required = false,defaultValue = "1") Long pageNum,
                                       @RequestParam(required = false,defaultValue = "10") Long pageSize,
                                       @RequestParam(required = false) String key,
                                       @RequestParam(required = false,defaultValue = "-1") Integer type){
        IPage<User> userPage = userService.lambdaQuery()
                .eq(!type.equals(-1), User::getType, type)
                .like(StringUtils.isNotBlank(key), User::getNickname, key)
                .page(new Page<>(pageNum, pageSize));
        return Result.page(userPage);
    }

    @Operation(summary = "修改用户状态")
    @PutMapping("/user/changeStatus")
    public Result userChangeStatus(@RequestParam("id") Long id,@RequestParam("userState") Integer userState){
        User user = userService.getById(id);
        user.setState(userState);
        return Result.isSuccess(userService.updateById(user));
    }
    @Operation(summary = "修改用户类型")
    @PutMapping("/user/changeType")
    public Result userChangeType(@RequestParam("id") Long id,@RequestParam("userType") Integer userType){
        User user = userService.getById(id);
        user.setType(userType);
        return Result.isSuccess(userService.updateById(user));
    }
    @Operation(summary = "删除用户")
    @DeleteMapping("/delete/user/{id}")
    public Result deleteUser(@PathVariable Long id){
        return  Result.isSuccess(userService.removeById(id));
    }

    @Operation(summary = "增加车辆信息")
    @PostMapping("/save/vehicle")
    public Result saveVehicle(@RequestBody VehicleDTO dto){
        return Result.isSuccess(vehicleService.save(VehicleDTO.toPo(dto)));
    }

    @Operation(summary = "删除车辆")
    @DeleteMapping("/delete/vehicle/{id}")
    public Result deleteVehicle(@PathVariable Long id){
        return Result.isSuccess(vehicleService.removeById(id));
    }

    @Operation(summary = "修改车辆信息")
    @PutMapping("/update/vehicle")
    public Result updateVehicle(@RequestBody VehicleDTO dto){
        return Result.isSuccess(vehicleService.updateById(VehicleDTO.toPo(dto)));
    }

    @Operation(summary = "修改车辆状态")
    @PutMapping("/vehicle/chageStatus")
    public Result updateVehicleStatus(@RequestParam("id") Long id,@RequestParam("status") Integer status){
        Vehicle vehicle = vehicleService.getById(id);
        vehicle.setAvailable(status);
        return Result.isSuccess(vehicleService.updateById(vehicle));
    }

    @Operation(summary = "分页查询车辆信息")
    @GetMapping("/vehicle/page")
    public Result<PageInfo<Vehicle>> vehiclePage(
            @RequestParam(required = false,defaultValue = "1") Long pageNum,
            @RequestParam(required = false,defaultValue = "10")  Long pageSize,
            @RequestParam(required = false,defaultValue = "-1") Integer type,
            @RequestParam(required = false) String key
    ){
        IPage<Vehicle> page = vehicleService.lambdaQuery()
                .eq(!type.equals(-1),Vehicle::getType,type)
                .like(StringUtils.isNotBlank(key),Vehicle::getBrand,key)
                .or()
                .like(StringUtils.isNotBlank(key),Vehicle::getModel,key)
                .or()
                .like(StringUtils.isNotBlank(key),Vehicle::getIllustrate,key)
                .page(new Page<>(pageNum, pageSize));
        return Result.page(page);
    }

    @Operation(summary = "分页查询订单信息")
    @GetMapping("/order/page")
    public Result<PageInfo<Order>> orderPage(
            @RequestParam(required = false,defaultValue = "1") Long pageNum,
            @RequestParam(required = false,defaultValue = "10") Long pageSize,
            @RequestParam(required = false)Date start,
            @RequestParam(required = false) Date end,
            @RequestParam(required = false,defaultValue = "-1") Integer status,
            @RequestParam(required = false)String key
            ){
        Page<Order> page = orderService.lambdaQuery()
                .eq(!status.equals(-1), Order::getStatus, status)
                .like(StringUtils.isNotBlank(key), Order::getIllustrate, key)
                .ge(start != null, Order::getStart, start)
                .le(end != null, Order::getEnd, end)
                .page(new Page<>(pageNum, pageSize));
        return Result.page(page);
    }

    @Operation(summary = "修改订单状态")
    @PutMapping("/order/changeStatus")
    public Result orderChangeStatus(@RequestParam("id") Long id,@RequestParam("orderStatus")Integer orderStatus){
        Order order = orderService.getById(id);
        order.setStatus(orderStatus);
        return Result.isSuccess(orderService.updateById(order));
    }

    @Operation(summary = "删除订单")
    @DeleteMapping("/delete/order/{id}")
    public Result deleteOrder(@PathVariable Long id) {
        return Result.isSuccess(orderService.removeById(id));
    }
}
