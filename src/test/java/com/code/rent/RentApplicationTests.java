package com.code.rent;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.MD5;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RentApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(IdUtil.fastSimpleUUID());
        System.out.println(IdUtil.getSnowflake().nextId());
        System.out.println(IdUtil.objectId());
        System.out.println(IdUtil.nanoId());
    }

}