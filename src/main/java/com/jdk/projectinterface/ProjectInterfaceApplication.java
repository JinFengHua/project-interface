package com.jdk.projectinterface;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.jdk.projectinterface.mapper")
@SpringBootApplication
public class ProjectInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectInterfaceApplication.class, args);
    }

}
