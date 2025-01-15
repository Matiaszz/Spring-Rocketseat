package dev.matias.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodolistApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
		clear();
	}

	public static void clear() {
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
	}

}
