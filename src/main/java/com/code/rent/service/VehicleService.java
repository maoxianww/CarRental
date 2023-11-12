package com.code.rent.service;

import com.code.rent.entity.Vehicle;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author HeXin
* @description 针对表【vehicle】的数据库操作Service
* @createDate 2023-10-29 14:58:39
*/
public interface VehicleService extends IService<Vehicle> {

    /**
     * 获取库存
     *
     * @param brand 品牌
     * @param model 型
     * @param type  类型
     * @return {@link Long}
     */
    Long getInventory(String brand,String model,Integer type);

    /**
     * 获取库存
     *
     * @param vehicle 车辆
     * @return {@link Long}
     */
    Long getInventory(Vehicle vehicle);

}
