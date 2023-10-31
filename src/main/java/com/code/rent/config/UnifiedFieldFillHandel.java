package com.code.rent.config;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author HeXin
 * @date 2023/10/30
 */
@Component
public class UnifiedFieldFillHandel implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long id = StpUtil.getLoginId(1L);
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);
        //创建人
        this.setFieldValByName("createBy",id,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long id = StpUtil.getLoginId(1L);
        this.setFieldValByName("gmtModified",new Date(),metaObject);
        this.setFieldValByName("updateBy",id,metaObject);
    }
}
