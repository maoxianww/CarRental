package com.code.rent.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.rent.common.PageInfo;
import com.code.rent.common.Result;
import com.code.rent.constants.CodeEnum;
import com.code.rent.constants.RedisConstants;
import com.code.rent.entity.Vehicle;
import com.code.rent.entity.vo.VehicleVO;
import com.code.rent.exception.CustomException;
import com.code.rent.service.VehicleService;
import com.code.rent.utils.RedisUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 车辆控制器
 *
 * @author HeXin
 * @date 2023/11/10
 */
@Tag(name = "车辆模块")
@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    /**
     * 车辆服务
     */
    @Autowired
    private VehicleService vehicleService;

    /**
     * Redis 实用程序
     */
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 页
     *
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @param key      钥匙
     * @return {@link Result}<{@link PageInfo}<{@link Vehicle}>>
     */
    @Operation(summary = "车辆分页详情")
    @GetMapping("/page")
    public Result<PageInfo<VehicleVO>> page(@RequestParam(value = "pageNum",defaultValue = "1") Long pageNum,
                                          @RequestParam(value = "pageSize",defaultValue = "10") Long pageSize,
                                          @RequestParam(required = false) String key){
        // 获取用户类型
        String role = null;
        if(StpUtil.isLogin()){
           role = (String) redisUtil.get(RedisConstants.USER_ROLE.getKey() + StpUtil.getLoginId());
        }
        IPage<VehicleVO> vehicleIpage;
        if("admin".equals(role) || "vip".equals(role)){
            vehicleIpage = vehicleService.lambdaQuery()
                    .eq(Vehicle::getAvailable,1)
                    .like(StringUtils.isNotBlank(key),Vehicle::getBrand,key)
                    .or()
                    .like(StringUtils.isNotBlank(key),Vehicle::getModel,key)
                    .or()
                    .like(StringUtils.isNotBlank(key),Vehicle::getIllustrate,key)
                    .orderByAsc(Vehicle::getDailyRate)
                    .page(new Page<>(pageNum,pageSize))
                    .convert(Vehicle::toVo);
        } else if("user".equals(role) || StringUtils.isBlank(role)){
            vehicleIpage = vehicleService.lambdaQuery()
                    .eq(Vehicle::getType,0)
                    .eq(Vehicle::getAvailable,1)
                    .like(StringUtils.isNotBlank(key),Vehicle::getBrand,key)
                    .or()
                    .like(StringUtils.isNotBlank(key),Vehicle::getModel,key)
                    .or()
                    .like(StringUtils.isNotBlank(key),Vehicle::getIllustrate,key)
                    .orderByAsc(Vehicle::getDailyRate)
                    .page(new Page<>(pageNum,pageSize))
                    .convert(Vehicle::toVo);
        }else{
            throw new CustomException(CodeEnum.SYSTEM_REPAIR);
        }
        return Result.page(vehicleIpage);
    }

    /**
     * 按 ID 获取车辆
     *
     * @param id 编号
     * @return {@link Result}<{@link Vehicle}>
     */
    @Operation(summary = "根据id获取车辆信息")
    @GetMapping("/{id}")
    public Result<VehicleVO> getVehicleById(@PathVariable  Long id){
        Vehicle vehicle = vehicleService.getById(id);
        vehicle.setInventory(vehicleService.getInventory(vehicle));
        return Result.success(Vehicle.toVo(vehicle));
    }

}
