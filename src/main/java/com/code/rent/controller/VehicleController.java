package com.code.rent.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.rent.common.PageInfo;
import com.code.rent.common.Result;
import com.code.rent.constants.CodeEnum;
import com.code.rent.constants.RedisConstants;
import com.code.rent.entity.Vehicle;
import com.code.rent.exception.CustomException;
import com.code.rent.service.VehicleService;
import com.code.rent.utils.RedisUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author HeXin
 */
@Tag(name = "车辆模块")
@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private RedisUtil redisUtil;
    @Operation(summary = "车辆分页详情")
    @GetMapping("/page")
    public Result<PageInfo<Vehicle>> page(@RequestParam(value = "pageNum",defaultValue = "1") Long pageNum,
                                          @RequestParam(value = "pageSize",defaultValue = "10") Long pageSize,
                                          @RequestParam(required = false) String key){
        // 获取用户类型
        String role = null;
        if(StpUtil.isLogin()){
           role = (String) redisUtil.get(RedisConstants.USER_ROLE.getKey() + StpUtil.getLoginId());
        }
        IPage<Vehicle> vehicleIpage;
        if("admin".equals(role) || "vip".equals(role)){
            vehicleIpage = vehicleService.lambdaQuery()
                    .like(StringUtils.isNotBlank(key),Vehicle::getBrand,key)
                    .or()
                    .like(StringUtils.isNotBlank(key),Vehicle::getModel,key)
                    .or()
                    .like(StringUtils.isNotBlank(key),Vehicle::getIllustrate,key)
                    .page(new Page<>(pageNum,pageSize));
        } else if("user".equals(role) || StringUtils.isBlank(role)){
            vehicleIpage = vehicleService.lambdaQuery()
                    .eq(Vehicle::getType,0)
                    .like(StringUtils.isNotBlank(key),Vehicle::getBrand,key)
                    .or()
                    .like(StringUtils.isNotBlank(key),Vehicle::getModel,key)
                    .or()
                    .like(StringUtils.isNotBlank(key),Vehicle::getIllustrate,key)
                    .page(new Page<>(pageNum,pageSize));
        }else{
            throw new CustomException(CodeEnum.SYSTEM_REPAIR);
        }
        return Result.page(vehicleIpage);
    }

    @Operation(summary = "根据id获取车辆信息")
    @GetMapping("/{id}")
    public Result<Vehicle> getVehicleById(@PathVariable  Long id){
        return Result.success(vehicleService.getById(id));
    }

}
