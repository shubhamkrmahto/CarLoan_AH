package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ClmsAhApiModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClmsAhApiModuleApplication.class, args);
	}
	

	
	@Bean
	public RestTemplate rt()
	{
		RestTemplate rs = new RestTemplate();
		
		return rs;
	}

}
