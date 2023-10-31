package com.code.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.rent.entity.Order;
import com.code.rent.service.OrderService;
import com.code.rent.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author HeXin
* @description 针对表【order】的数据库操作Service实现
* @createDate 2023-10-29 14:58:39
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




