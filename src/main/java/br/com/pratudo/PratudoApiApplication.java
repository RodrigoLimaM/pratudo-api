package br.com.pratudo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PratudoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PratudoApiApplication.class, args);
	}

}
