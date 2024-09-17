package com.azo.backend.msvc.binnacle.msvc_binnacle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcBinnacleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcBinnacleApplication.class, args);
    System.out.println("Servicio Binnacleeeeeeeeeeeeeeeeeeeeeeeeeeee!!!");
	}

}
