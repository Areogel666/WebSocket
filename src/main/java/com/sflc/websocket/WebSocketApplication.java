package com.sflc.websocket;

import com.sflc.websocket.controller.MessageController;
import com.sflc.websocket.service.impl.MessageServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.sflc.websocket.mapper")
public class WebSocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketApplication.class, args);
	}


}
