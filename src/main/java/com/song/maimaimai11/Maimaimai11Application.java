package com.song.maimaimai11;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.song.maimaimai11.mapper")
public class Maimaimai11Application {

    public static void main(String[] args) {
        SpringApplication.run(Maimaimai11Application.class, args);
    }

}
