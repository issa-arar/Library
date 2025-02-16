package com.assessment.library;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.assessment.library.service.UserService;

@SpringBootApplication
@EnableCaching 
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	 @Bean
    CommandLineRunner initAdmin(UserService userService) {
        return args -> {
            userService.createDefaultAdmins();
        };
    }
}
