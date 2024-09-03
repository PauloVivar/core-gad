package com.azo.backend.msvc.avaluos_catastros.msvc_avaluos_catastros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//igual al CRUD Repository pero para hacer get, post, etc hacia otros microservicios.
@EnableFeignClients
@SpringBootApplication
public class MsvcAvaluosCatastrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcAvaluosCatastrosApplication.class, args);
    System.out.println("Servidor avaluos y catastrosssssssssssssssss!!!");
	}

}
