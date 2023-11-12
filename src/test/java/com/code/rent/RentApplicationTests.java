package com.code.rent;

import com.code.rent.utils.EmailUtils;
import com.code.rent.utils.PasswordUtils;
import com.code.rent.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class RentApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EmailUtils emailUtils;
    @Test
    void contextLoads() {
        System.out.println(PasswordUtils.encrypt("123456"));
        List<String> list = new ArrayList<>();
    }
}