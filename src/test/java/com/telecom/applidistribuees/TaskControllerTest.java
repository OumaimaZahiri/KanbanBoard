package com.telecom.applidistribuees;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telecom.applidistribuees.Entity.Developer;
import com.telecom.applidistribuees.Entity.Task;
import com.telecom.applidistribuees.Service.DeveloperService;
import com.telecom.applidistribuees.Service.TaskService;
import com.telecom.applidistribuees.utils.Constants;
import com.telecom.applidistribuees.utils.ErrorMessage;
import com.telecom.applidistribuees.utils.TaskMoveAction;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class TaskControllerTest extends ControllerTest {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private DeveloperService developerService;
	
	@Test
	public void testGetTaskTypes() throws Exception {
		
		mvc.perform(get("/task_types")
				.contentType(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			    .andExpect(jsonPath("$[0].id", is(1)))
			    .andExpect(jsonPath("$[0].label", is("BUG")))
			    .andExpect(jsonPath("$[1].id", is(2)))
			    .andExpect(jsonPath("$[1].label", is("FEATURE")));
	}
	
	@Test
	public void testGetTaskStatus() throws Exception {
		
		mvc.perform(get("/task_status")
				.contentType(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			    .andExpect(jsonPath("$[0].id", is(1)))
			    .andExpect(jsonPath("$[0].label", is("TODO")))
			    .andExpect(jsonPath("$[1].id", is(2)))
			    .andExpect(jsonPath("$[1].label", is("DOING")))
			    .andExpect(jsonPath("$[2].id", is(3)))
			    .andExpect(jsonPath("$[2].label", is("TEST")))
			    .andExpect(jsonPath("$[3].id", is(4)))
			    .andExpect(jsonPath("$[3].label", is("DONE")))
			    ;
	}
	
	@Test
	public void testGetTasks() throws Exception {
		
		mvc.perform(get("/tasks")
				.contentType(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			    .andExpect(jsonPath("$[0].title", is("task1")))
			    .andExpect(jsonPath("$[0].nbHoursForecast", is(0)))
			    .andExpect(jsonPath("$[0].nbHoursReal", is(0)))
			    .andExpect(jsonPath("$[0].status.label", is("TODO")))
			    .andExpect(jsonPath("$[0].type.label", is("FEATURE")))
			    .andExpect(jsonPath("$[0].developers[0].email", is("dev1@dev.dev")))
			    ;
	}
	
	@Test
	public void testCreateTask() throws Exception {
		
		Developer developer = this.developerService.findAllDevelopers().iterator().next();
		
		Task task = new Task();
		task.setTitle("task 2");
		task.setNbHoursForecast(0);
		task.setNbHoursReal(0);
		task.setType(this.taskService.getTaskType(Constants.TASK_TYPE_ID[2]));
		task.addDeveloper(developer);
		
		ObjectMapper mapper = new ObjectMapper();
        byte[] taskAsBytes = mapper.writeValueAsBytes(task);
		
		mvc.perform(post("/tasks")
				.contentType(MediaType.APPLICATION_JSON).content(taskAsBytes))				
			    .andExpect(status().isOk())
			    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			    ;
		
		Collection<Task> tasks = this.taskService.findAllTasks();
		Assert.assertEquals(2, tasks.size());
		
		boolean found = false;
		
		for (Task currentTask : tasks) {
			
			if (currentTask.getTitle().equals("task 2")) {
				
				found = true;
				
				Assert.assertEquals(Constants.TASK_STATUS_LABEL[0], currentTask.getStatus().getLabel());
				
				this.taskService.delete(currentTask);
			}
		}
		
		Assert.assertTrue(found);
	}
	
	@Test
	public void testCreateTaskWithErrors() throws Exception {
		
		Developer developer = this.developerService.findAllDevelopers().iterator().next();
		
		Task task = new Task();
		task.setTitle("");
		task.setNbHoursForecast(150);
		task.setNbHoursReal(0);
		task.setType(this.taskService.getTaskType(Constants.TASK_TYPE_ID[2]));
		task.addDeveloper(developer);
		
		ObjectMapper mapper = new ObjectMapper();
        byte[] taskAsBytes = mapper.writeValueAsBytes(task);
		
		mvc.perform(post("/tasks")
				.contentType(MediaType.APPLICATION_JSON).content(taskAsBytes))				
			    .andExpect(status().is(400))
			    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			    .andExpect(jsonPath("errors", new IsCollectionWithSize<ErrorMessage>(is(2))))
			    .andExpect(jsonPath("errors[0].field", is("nbHoursForecast")))
			    .andExpect(jsonPath("errors[0].message", is("NbHoursForecast should not be greater than 144")))
			    .andExpect(jsonPath("errors[1].field", is("title")))
			    .andExpect(jsonPath("errors[1].message", is("Title cannot be empty")))			    
			    ;
		
		Collection<Task> tasks = this.taskService.findAllTasks();
		Assert.assertEquals(1, tasks.size());
	}
	
	@Test
	public void testMoveTask() throws Exception {
		
		Developer developer = this.developerService.findAllDevelopers().iterator().next();
		
		Task task = new Task();
		task.setTitle("task 2");
		task.setNbHoursForecast(0);
		task.setNbHoursReal(0);
		task.setType(this.taskService.getTaskType(Constants.TASK_TYPE_ID[0]));
		task.addDeveloper(developer);
		
		task = this.taskService.addTask(task);
		
		Collection<Task> tasks = this.taskService.findAllTasks();
		
		for (Task currentTask : tasks) {
			
			if (currentTask.getTitle().equals("task2")) {

				ObjectMapper mapper = new ObjectMapper();
				
				TaskMoveAction moveRight = new TaskMoveAction();
				moveRight.setAction(Constants.MOVE_RIGHT_ACTION);
								
		        byte[] moveRightAsBytes = mapper.writeValueAsBytes(moveRight);
				
				mvc.perform(patch("/tasks/" + currentTask.getId())
						.contentType(MediaType.APPLICATION_JSON).content(moveRightAsBytes))				
					    .andExpect(status().isOk())
					    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					    ;
				
				task = this.taskService.getTask(currentTask.getId());
				
				Assert.assertEquals(Constants.TASK_STATUS_LABEL[1], task.getStatus().getLabel());
				
				TaskMoveAction moveLeft = new TaskMoveAction();
				moveLeft.setAction(Constants.MOVE_LEFT_ACTION);
				
				byte[] moveLeftAsBytes = mapper.writeValueAsBytes(moveLeft);
				
				mvc.perform(patch("/tasks/" + currentTask.getId())
						.contentType(MediaType.APPLICATION_JSON).content(moveLeftAsBytes))				
					    .andExpect(status().isOk())
					    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					    ;
				
				task = this.taskService.getTask(currentTask.getId());
				
				Assert.assertEquals(Constants.TASK_STATUS_LABEL[0], task.getStatus().getLabel());
				
				this.taskService.delete(currentTask);
			}
		}
	}

}
