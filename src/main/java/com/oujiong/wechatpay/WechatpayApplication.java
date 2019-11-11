package com.oujiong.wechatpay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.oujiong.wechatpay.mapper")
//开启事务管理
@EnableTransactionManagement
public class WechatpayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WechatpayApplication.class, args);
	}
}
