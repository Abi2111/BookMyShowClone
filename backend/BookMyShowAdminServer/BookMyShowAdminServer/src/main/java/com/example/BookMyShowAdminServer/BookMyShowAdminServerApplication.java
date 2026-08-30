package com.example.BookMyShowAdminServer;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class BookMyShowAdminServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookMyShowAdminServerApplication.class, args);
	}

}
