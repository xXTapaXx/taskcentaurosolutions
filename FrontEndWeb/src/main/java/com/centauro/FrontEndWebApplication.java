package com.centauro;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.centauro.controllers.TasksQuickstart;

@SpringBootApplication

public class FrontEndWebApplication {

	public static void main(String[] args) {
		/*try {
			TasksQuickstart.getTasksService();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		SpringApplication.run(FrontEndWebApplication.class, args);
	}
}