package com.taskforge.taskforge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RestController
@SpringBootApplication(scanBasePackages = "com.taskforge.taskforge")
public class TaskforgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskforgeApplication.class, args);
	}
	@GetMapping("/")
	public String home() {
		return "APP WORKING";
	}
}