package com.telecom.applidistribuees;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.telecom.applidistribuees.DAO.TaskRepository;
import com.telecom.applidistribuees.Entity.ChangeLog;
import com.telecom.applidistribuees.Entity.Developer;
import com.telecom.applidistribuees.Entity.Task;
import com.telecom.applidistribuees.Entity.TaskStatus;
import com.telecom.applidistribuees.Entity.TaskType;
import com.telecom.applidistribuees.Service.DeveloperService;
import com.telecom.applidistribuees.Service.TaskService;
import com.telecom.applidistribuees.utils.Constants;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class TaskTest {

	@Autowired
    private TaskRepository taskRepository;

	@Autowired
    private TaskService taskService;

	private DeveloperService developerService;
	
	@Test
	public void testgetAllTasks() {
		
		Collection<Task> tasks = this.taskService.getAllTasks();
		Assert.assertEquals(1, tasks.size());
	}

	@Test
	public void testFindAllTaskTypes() {
		
		Collection<TaskType> taskTypes = this.taskService.getAllTaskTypes();
		Assert.assertEquals(2, taskTypes.size());
	}
	
	@Test
	public void testFindAllTaskStatus() {
		
		Collection<TaskStatus> taskStatus = this.taskService.getAllTaskStatus();
		Assert.assertEquals(4, taskStatus.size());
	}
	
	@Test
	public void testSaveTask() {
		Task task = new Task();
		
		TaskStatus status = new TaskStatus(Constants.TASK_STATUS_ID[0], Constants.TASK_STATUS_LABEL[0]);
		TaskType type = new TaskType(Constants.TASK_TYPE_ID[0], Constants.TASK_TYPE_LABEL[0]);
        task.setTitle("My task");
        task.setStatus(status);
        task.setType(type);
        

		Developer developer = this.developerService.findAllDevelopers().iterator().next();
		
		task.addDeveloper(developer);
		task.setNbHoursForecast(3);
		task.setNbHoursReal(4);
		task.setCreated(LocalDate.now());
		this.taskRepository.save(task);

		List<Task> tasks = this.taskRepository.findAll();	
		Assert.assertEquals(3, tasks.size());		
		this.taskRepository.delete(task);
	}
	
	@Test
	public void testAddDeveloperToTask() {
		
		Developer developer = new Developer();
		Task task = new Task();
		task.addDeveloper(developer);
		Assert.assertEquals(1, task.getDevelopers().size());
	}
	
	@Test
	public void testUpdateTaskStatus() {
		
		Task task = this.taskService.findAllTasks().iterator().next();
		TaskStatus status1 = this.taskService.getTaskStatus(1L);
		TaskStatus status2 = this.taskService.getTaskStatus(2L);
		
		task = this.taskService.updateStatus(task, status2);
		
		Assert.assertEquals(status2, task.getStatus());
		
		Collection<ChangeLog> changeLogs = this.taskService.findChangeLogsForTask(task);
		
		Assert.assertEquals(1, changeLogs.size());
		
		ChangeLog changeLog = changeLogs.iterator().next();
		
		Assert.assertEquals(status1, changeLog.getOldStatus());
		Assert.assertEquals(status2, changeLog.getNewStatus());		
	}
	
	@Test
	public void testMoveRightTask() {
		
		Developer developer = this.developerService.findAllDevelopers().iterator().next();
		
		TaskStatus todoStatus = this.taskService.getTaskStatus(Constants.TASK_STATUS_ID[0]);
		TaskStatus doingStatus = this.taskService.getTaskStatus(Constants.TASK_STATUS_ID[1]);
		TaskStatus testStatus = this.taskService.getTaskStatus(Constants.TASK_STATUS_ID[2]);
		TaskStatus doneStatus = this.taskService.getTaskStatus(Constants.TASK_STATUS_ID[3]);
		
		Task task = new Task();
		task.setNbHoursForecast(3);
		task.setNbHoursReal(4);
		task.setTitle("my task");
		task.setStatus(todoStatus);
		task.addDeveloper(developer);
		
		task = this.taskService.addTask(task);
		
		task = this.taskService.moveRightTask(task);
		
		Assert.assertEquals(doingStatus, task.getStatus());
		
		Collection<ChangeLog> changeLogs = this.taskService.findChangeLogsForTask(task);
		
		Assert.assertEquals(1, changeLogs.size());
		
		ChangeLog changeLog = changeLogs.iterator().next();
		Assert.assertEquals(todoStatus, changeLog.getOldStatus());
		Assert.assertEquals(doingStatus, changeLog.getNewStatus());
		
		task = this.taskService.moveRightTask(task);
		Assert.assertEquals(testStatus, task.getStatus());
		
		task = this.taskService.moveRightTask(task);
		Assert.assertEquals(doneStatus, task.getStatus());
		Assert.assertEquals(3, task.getChangeLogs().size());
		
		this.taskService.delete(task);
	}
	
	@Test
	public void testMoveLeftTask() {
		
		Developer developer = this.developerService.findAllDevelopers().iterator().next();
		
		TaskStatus todoStatus = this.taskService.getTaskStatus(Constants.TASK_STATUS_ID[0]);
		TaskStatus doingStatus = this.taskService.getTaskStatus(Constants.TASK_STATUS_ID[1]);
		TaskStatus testStatus = this.taskService.getTaskStatus(Constants.TASK_STATUS_ID[2]);
		TaskStatus doneStatus = this.taskService.getTaskStatus(Constants.TASK_STATUS_ID[3]);
		
		Task task = new Task();
		task.setNbHoursForecast(3);
		task.setNbHoursReal(4);
		task.setTitle("my left task");
		task.setStatus(todoStatus);
		task.addDeveloper(developer);
		
		task = this.taskService.addTask(task);
		
		task = this.taskService.moveRightTask(task); // => DOING
		task = this.taskService.moveRightTask(task); // => TEST
		task = this.taskService.moveRightTask(task); // => DONE
		
		task = this.taskService.moveLeftTask(task);
		
		Assert.assertEquals(testStatus, task.getStatus());
		
		Collection<ChangeLog> changeLogs = this.taskService.findChangeLogsForTask(task);
		
		Assert.assertEquals(4, changeLogs.size());
		
		boolean lastChangeLogFound = false;
		
		for (ChangeLog changeLog : changeLogs) {
		
			if (doneStatus.equals(changeLog.getOldStatus())) {
				lastChangeLogFound = true;	
				Assert.assertEquals(testStatus, changeLog.getNewStatus());
			}
		}
		
		Assert.assertTrue(lastChangeLogFound);
		
		task = this.taskService.moveLeftTask(task);
		Assert.assertEquals(doingStatus, task.getStatus());
		
		task = this.taskService.moveLeftTask(task);
		Assert.assertEquals(todoStatus, task.getStatus());
		
		Assert.assertEquals(6, task.getChangeLogs().size());
		
		this.taskService.delete(task);
	}
}
