package com.telecom.applidistribuees.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telecom.applidistribuees.Entity.Task;
import com.telecom.applidistribuees.Service.TaskService;
import com.telecom.applidistribuees.utils.Constants;
import com.telecom.applidistribuees.utils.TaskMoveAction;

@RestController
@RequestMapping("/task")
public class TaskController {
	
	@Autowired 
	private TaskService taskService;
	
	@GetMapping("/alltasks")
	public List<Task> getAllTasks() {
		return this.taskService.getAllTasks();
	}
	@PostMapping("/create")
	public Task create(@RequestBody Task task) {
		return this.taskService.addTask(task);
	}
	
	@GetMapping("/readtask/{id}")
	public Task getTask(@PathVariable Long id) {
		return taskService.getTask(id);
	}
	
	@PutMapping("/update/{id}")
	public Task moveTask(@PathVariable Long id, @RequestBody TaskMoveAction taskMoveAction) {
		
		Task task = this.taskService.getTask(id);
				
		if (Constants.MOVE_LEFT_ACTION.equals(taskMoveAction.getAction())) { 	
			task = this.taskService.moveLeftTask(task);
		}
		
		else if (Constants.MOVE_RIGHT_ACTION.equals(taskMoveAction.getAction())) {
			task = this.taskService.moveRightTask(task);
		}
		
		else {
			throw new IllegalStateException();
		}
		
		return task;
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		Task task = this.taskService.getTask(id);
		this.taskService.delete(task);
	}
}
