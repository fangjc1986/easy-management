package com.fangjc1986.ezmpro;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@ServletComponentScan(basePackages = "com.fangjc1986")
@EnableCaching
public class EzmproApplication {

    public static void main(String[] args) {
        SpringApplication.run(EzmproApplication.class, args);
    }

}
