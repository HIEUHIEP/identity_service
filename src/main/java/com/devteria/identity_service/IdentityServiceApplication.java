package com.devteria.identity_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IdentityServiceApplication {

	public static void main(String[] args) {
		//docker pull mysql:8.0.40-debian
		//docker run --name mysql-8.0.40-2 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.40-debian
		//services.msc => stop mySQL neu ko start dc docker
		SpringApplication.run(IdentityServiceApplication.class, args);
	}

}
