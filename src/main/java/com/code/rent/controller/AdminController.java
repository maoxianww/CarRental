package com.code.rent.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.rent.common.PageInfo;
import com.code.rent.common.Result;
import com.code.rent.constants.RedisConstants;
import com.code.rent.entity.Order;
import com.code.rent.entity.User;
import com.code.rent.entity.Vehicle;
import com.code.rent.entity.dto.LoginParam;
import com.code.rent.entity.dto.UserDTO;
import com.code.rent.entity.dto.VehicleDTO;
import com.code.rent.service.OrderService;
import com.code.rent.service.UserService;
import com.code.rent.service.VehicleService;
import com.code.rent.utils.PasswordUtils;
import com.code.rent.utils.RedisUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 管理员模块
 *
 * @author HeXin
 * @date 2023/11/12
 */
@RestController
@RequestMapping("/admin")
@SaCheckLogin
@SaCheckRole("admin")
@Tag(name = "管理员模块")
public class AdminController {

    /**
     * 用户服务
     */
    @Autowired
    private UserService userService;

    /**
     * 车辆服务
     */
    @Autowired
    private VehicleService vehicleService;

    /**
     * 订单服务
     */
    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 登录
     *
     * @param param 参数
     * @return {@link Result}<{@link String}>
     */
    @Operation(summary = "登录功能")
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginParam param){
        return Result.success(userService.loginAdmin(param));
    }

    /**
     * 按 ID 获取用户信息
     *
     * @param id 编号
     * @return {@link Result}<{@link User}>
     */
    @Operation(summary = "根据id查询用户")
    @GetMapping("/user/{id}")
    public Result<User> getById(@PathVariable String id){
        User user = (User) redisUtil.get(RedisConstants.USER.getKey() + id);
        if(user==null){
            user = userService.getById(id);
            redisUtil.set(RedisConstants.USER,id,user);
        }
        return Result.success(user);
    }

    /**
     * 保存用户
     *
     * @param user 用户
     * @return {@link Result}
     */
    @Operation(summary = "增加用户")
    @PostMapping("/save/user")
    public Result saveUser(@RequestBody UserDTO user){
        user.setPassword(PasswordUtils.encrypt(user.getPassword()));
        redisUtil.set(RedisConstants.USER,user.getId(),user);
        return Result.isSuccess(userService.save(UserDTO.toPo(user)));
    }

    /**
     * 更新用户
     *
     * @param dto 用户
     * @return {@link Result}
     */
    @Operation(summary = "修改用户信息")
    @PutMapping("/update/user")
    public Result updateUser(@RequestBody UserDTO dto){
        dto.setPassword(PasswordUtils.encrypt(dto.getPassword()));
        User user = UserDTO.toPo(dto);
        redisUtil.set(RedisConstants.USER,user.getId(),user);
        return Result.isSuccess(userService.updateById(user));
    }

    /**
     * 注销
     *
     * @return {@link Result}<{@link String}>
     */
    @Operation(summary = "退出登录")
    @SaIgnore
    @GetMapping("/logout")
    public Result<String> logout() {
        StpUtil.logout(StpUtil.getLoginId());
        redisUtil.remove(RedisConstants.USER.getKey()+StpUtil.getLoginId());
        redisUtil.remove(RedisConstants.USER_ROLE.getKey()+StpUtil.getLoginId());
        redisUtil.remove(RedisConstants.USER_PERMISSION.getKey()+StpUtil.getLoginId());
        return Result.success();
    }

    /**
     * 用户页面
     *
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @param key      钥匙
     * @param type     类型
     * @return {@link Result}<{@link PageInfo}<{@link User}>>
     */
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

    /**
     * 用户更改状态
     *
     * @param id        编号
     * @param userState 用户状态
     * @return {@link Result}
     */
    @Operation(summary = "修改用户状态")
    @PutMapping("/user/changeStatus")
    public Result userChangeStatus(@RequestParam("id") String id,@RequestParam("userState") Integer userState){
        User user = userService.getById(id);
        user.setState(userState);
        // 更新缓存
        redisUtil.set(RedisConstants.USER,user.getId(),user);
        return Result.isSuccess(userService.updateById(user));
    }

    /**
     * 用户更改类型
     *
     * @param id       编号
     * @param userType
     * @return {@link Result}
     */
    @Operation(summary = "修改用户类型")
    @PutMapping("/user/changeType")
    public Result userChangeType(@RequestParam("id") String id,@RequestParam("userType") Integer userType){
        User user = userService.getById(id);
        user.setType(userType);
        // 更新缓存
        redisUtil.set(RedisConstants.USER,user.getId(),user);
        return Result.isSuccess(userService.updateById(user));
    }

    /**
     * 删除用户
     *
     * @param id 编号
     * @return {@link Result}
     */
    @Operation(summary = "删除用户")
    @DeleteMapping("/delete/user/{id}")
    public Result deleteUser(@PathVariable String id){
        redisUtil.remove(RedisConstants.USER.getKey()+id);
        return  Result.isSuccess(userService.removeById(id));
    }

    /**
     * 保存车辆
     *
     * @param dto DTO
     * @return {@link Result}
     */
    @Operation(summary = "增加车辆信息")
    @PostMapping("/save/vehicle")
    public Result saveVehicle(@RequestBody VehicleDTO dto){
        return Result.isSuccess(vehicleService.save(VehicleDTO.toPo(dto)));
    }

    /**
     * 删除车辆
     *
     * @param id 编号
     * @return {@link Result}
     */
    @Operation(summary = "删除车辆")
    @DeleteMapping("/delete/vehicle/{id}")
    public Result deleteVehicle(@PathVariable String id){
        return Result.isSuccess(vehicleService.removeById(id));
    }

    /**
     * 更新车辆
     *
     * @param dto DTO
     * @return {@link Result}
     */
    @Operation(summary = "修改车辆信息")
    @PutMapping("/update/vehicle")
    public Result updateVehicle(@RequestBody VehicleDTO dto){
        return Result.isSuccess(vehicleService.updateById(VehicleDTO.toPo(dto)));
    }

    /**
     * 更新车辆状态
     *
     * @param id     编号
     * @param status 地位
     * @return {@link Result}
     */
    @Operation(summary = "修改车辆状态")
    @PutMapping("/vehicle/chageStatus")
    public Result updateVehicleStatus(@RequestParam("id") String id,@RequestParam("status") Integer status){
        Vehicle vehicle = vehicleService.getById(id);
        vehicle.setAvailable(status);
        return Result.isSuccess(vehicleService.updateById(vehicle));
    }

    /**
     * 车辆页面
     *
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @param type     类型
     * @param key      钥匙
     * @return {@link Result}<{@link PageInfo}<{@link Vehicle}>>
     */
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

    /**
     * 订单页面
     *
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @param start    开始
     * @param end      结束
     * @param status   地位
     * @param key      钥匙
     * @return {@link Result}<{@link PageInfo}<{@link Order}>>
     */
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

    /**
     * 订单变更状态
     *
     * @param id          编号
     * @param orderStatus 订单状态
     * @return {@link Result}
     */
    @Operation(summary = "修改订单状态")
    @PutMapping("/order/changeStatus")
    public Result orderChangeStatus(@RequestParam("id") String id,@RequestParam("orderStatus")Integer orderStatus){
        Order order = orderService.getById(id);
        order.setStatus(orderStatus);
        return Result.isSuccess(orderService.updateById(order));
    }

    /**
     * 删除订单
     *
     * @param id 编号
     * @return {@link Result}
     */
    @Operation(summary = "删除订单")
    @DeleteMapping("/delete/order/{id}")
    public Result deleteOrder(@PathVariable String id) {
        return Result.isSuccess(orderService.removeById(id));
    }
}
