package com.example.gatewave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewaveApplication.class, args);
	}

}
