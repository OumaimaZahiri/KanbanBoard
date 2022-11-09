package com.telecom.applidistribuees.utils;

import java.time.LocalDate;
import java.time.Month;

import com.telecom.applidistribuees.DAO.DeveloperRepository;
import com.telecom.applidistribuees.DAO.TaskRepository;
import com.telecom.applidistribuees.DAO.TaskStatusRepository;
import com.telecom.applidistribuees.DAO.TaskTypeRepository;
import com.telecom.applidistribuees.Entity.Developer;
import com.telecom.applidistribuees.Entity.Task;
import com.telecom.applidistribuees.Entity.TaskStatus;
import com.telecom.applidistribuees.Entity.TaskType;
import com.telecom.applidistribuees.Service.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class LoadDatabase {

	@Bean
	@Profile("test_init")
	CommandLineRunner initDatabase(DeveloperRepository developerRepository,
									TaskRepository taskRepository,
									TaskStatusRepository taskStatusRepository,
									TaskTypeRepository taskTypeRepository,
									TaskService taskService) {
		
		return args -> {			
			initTaskStatusAndTypes(taskStatusRepository, taskTypeRepository);
			
			initDevelopers(developerRepository);					
		};
	}
	
	private void initDevelopers(DeveloperRepository developerRepository) {
		
		Developer me = new Developer();
		me.setEmail("oums@gmail.com");
		me.setFirstname("Oums");
		me.setLastname("Zahiri");
		me.setPassword("oums");
		me.setStartContract(LocalDate.of(2017, Month.NOVEMBER, 1));
		developerRepository.save(me);
		System.out.println(me + " saved to database.");
		
		Developer telecom = new Developer();
		telecom.setEmail("telecom@gmail.com");
		telecom.setFirstname("Telecom");
		telecom.setLastname("Saint-Étienne");
		telecom.setPassword("telecom");
		telecom.setStartContract(LocalDate.of(2017, Month.NOVEMBER, 1));
		developerRepository.save(telecom);
		System.out.println(telecom + " saved to database.");
		
		Developer user = new Developer();
		user.setEmail("user@gmail.com");
		user.setFirstname("User");
		user.setLastname("Lastname");
		user.setPassword("user");
		user.setStartContract(LocalDate.of(2017, Month.NOVEMBER, 1));
		developerRepository.save(user);
		System.out.println(user + " saved to database.");

	}
	
	private void initTaskStatusAndTypes(TaskStatusRepository taskStatusRepository,
										TaskTypeRepository taskTypeRepository) {
		
		TaskStatus todo = new TaskStatus(Constants.TASK_STATUS_ID[0], Constants.TASK_STATUS_LABEL[0]);
		taskStatusRepository.save(todo);
		System.out.println(todo + " saved to database.");
		
		TaskStatus doing = new TaskStatus(Constants.TASK_STATUS_ID[1], Constants.TASK_STATUS_LABEL[1]);
		taskStatusRepository.save(doing);
		System.out.println(doing + " saved to database.");
		
		TaskStatus test = new TaskStatus(Constants.TASK_STATUS_ID[2], Constants.TASK_STATUS_LABEL[2]);
		taskStatusRepository.save(test);
		System.out.println(test + " saved to database.");
		
		TaskStatus done = new TaskStatus(Constants.TASK_STATUS_ID[3], Constants.TASK_STATUS_LABEL[3]);
		taskStatusRepository.save(done);
		System.out.println(done + " saved to database.");
		
		TaskType dev = new TaskType(Constants.TASK_TYPE_ID[0], Constants.TASK_TYPE_LABEL[0]);
		taskTypeRepository.save(dev);
		System.out.println(todo + " saved to database.");
		
		TaskType test_type = new TaskType(Constants.TASK_TYPE_ID[1], Constants.TASK_TYPE_LABEL[1]);
		taskTypeRepository.save(test_type);
		System.out.println(doing + " saved to database.");
		
		TaskType bug = new TaskType(Constants.TASK_TYPE_ID[2], Constants.TASK_TYPE_LABEL[2]);
		taskTypeRepository.save(bug);
		System.out.println(test + " saved to database.");
		
		TaskType management = new TaskType(Constants.TASK_TYPE_ID[3], Constants.TASK_TYPE_LABEL[3]);
		taskTypeRepository.save(management);
		System.out.println(done + " saved to database.");
	}
	
	@Bean
	@Profile("test_manip")
	CommandLineRunner initTestDatabase(DeveloperRepository developerRepository,
									TaskRepository taskRepository,
									TaskStatusRepository taskStatusRepository,
									TaskTypeRepository taskTypeRepository) {
		
		return args -> {			
			initTaskStatusAndTypes(taskStatusRepository, taskTypeRepository);
			
			Developer dev1 = new Developer();
			dev1.setEmail("dev1@dev.dev");
			dev1.setFirstname("dev1");
			dev1.setLastname("dev1");
			dev1.setPassword("dev1");
			dev1.setStartContract(LocalDate.of(2017, Month.NOVEMBER, 1));
			developerRepository.save(dev1);
			System.out.println(dev1 + " saved to database.");
			
			Task task1 = new Task();
			task1.setCreated(LocalDate.now());
			task1.setTitle("task1");
			task1.setNbHoursForecast(0);
			task1.setNbHoursReal(0);
			task1.addDeveloper(dev1);
			task1.setType(taskTypeRepository.findById(Constants.TASK_STATUS_ID[0]).orElse(null));
			task1.setStatus(taskStatusRepository.findById(Constants.TASK_STATUS_ID[0]).orElse(null));
			taskRepository.save(task1);
			System.out.println(task1 + " saved to database.");
			
			System.out.println("Wow it seems OK for Kanban app initialization !");
		};
	}
}
