package com.code.rent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author HeXin
 */
@SpringBootApplication
@EnableSwagger2
@MapperScan("com.code.rent.mapper")
public class RentApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentApplication.class, args);
    }

}
