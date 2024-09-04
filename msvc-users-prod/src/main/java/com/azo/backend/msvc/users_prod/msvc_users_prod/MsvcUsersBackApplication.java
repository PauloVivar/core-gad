package com.azo.backend.msvc.users_prod.msvc_users_prod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcUsersBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcUsersBackApplication.class, args);
    System.out.println("Servidor levantado!!! YESSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
	}

}
