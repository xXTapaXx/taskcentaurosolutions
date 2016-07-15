package com.centauro;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.centauro.controllers.TasksQuickstart;

@SpringBootApplication
public class FrontEndWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontEndWebApplication.class, args);
		try {
			TasksQuickstart.getTasksService();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
