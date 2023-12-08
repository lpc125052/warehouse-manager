package com.pn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.pn.mapper")
@SpringBootApplication
public class WareHouseManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WareHouseManagerApplication.class, args);
    }
}
