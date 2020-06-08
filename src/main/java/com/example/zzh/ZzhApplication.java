package com.example.zzh;
/**
 * @Author: zhao zhihong
 * @Date: 2020/1/21
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@MapperScan({"com.example.zzh.mapper"})
@SpringBootApplication
public class ZzhApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ZzhApplication.class).bannerMode(Banner.Mode.OFF).run(args);
    }

}
