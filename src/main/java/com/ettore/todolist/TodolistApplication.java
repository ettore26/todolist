package com.ettore.todolist;

import com.ettore.todolist.model.Todo;
import com.ettore.todolist.repository.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodolistApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(TodoRepository todoRepository) {
		return args -> {
			todoRepository.save(new Todo("Task 1", "Description 1", false));
			todoRepository.save(new Todo("Task 2", "Description 2", false));
			todoRepository.save(new Todo("Task 3", "Description 3", true));
		};
	}

}
