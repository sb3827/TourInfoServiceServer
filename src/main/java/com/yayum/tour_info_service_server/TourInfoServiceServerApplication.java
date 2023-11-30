package com.yayum.tour_info_service_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TourInfoServiceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TourInfoServiceServerApplication.class, args);
		System.out.println("http://localhost:4000");
	}

}
