package com.code.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.rent.entity.Vehicle;
import com.code.rent.service.VehicleService;
import com.code.rent.mapper.VehicleMapper;
import org.springframework.stereotype.Service;

/**
* @author HeXin
* @description 针对表【vehicle】的数据库操作Service实现
* @createDate 2023-10-29 14:58:39
*/
@Service
public class VehicleServiceImpl extends ServiceImpl<VehicleMapper, Vehicle>
    implements VehicleService{
    @Override
    public Long getInventory(String brand, String model, Integer type) {
        return lambdaQuery()
                .eq(Vehicle::getBrand,brand)
                .eq(Vehicle::getModel,model)
                .eq(Vehicle::getType,type)
                .eq(Vehicle::getAvailable,1)
                .count();
    }

    @Override
    public Long getInventory(Vehicle vehicle) {
        return lambdaQuery()
                .eq(Vehicle::getBrand,vehicle.getBrand())
                .eq(Vehicle::getModel,vehicle.getModel())
                .eq(Vehicle::getType,vehicle.getType())
                .eq(Vehicle::getAvailable,1)
                .count();
    }
}




