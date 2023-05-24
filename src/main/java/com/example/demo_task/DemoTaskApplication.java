package com.example.demo_task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DemoTaskApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(DemoTaskApplication.class, args);
		System.out.println("hello");
	}
}
