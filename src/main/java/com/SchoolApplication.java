package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@EnableOpenApi
@SpringBootApplication
//@MapperScan({"com.mapper"})
public class SchoolApplication {
	public static void main(String[] args) {
		SpringApplication.run(SchoolApplication.class, args);
//		Thread t1 = new Thred();
//		Thread t2 = new Thred();
//		t1.start();
//		t2.start();
		
		log.info("启动成功");
	}
}
