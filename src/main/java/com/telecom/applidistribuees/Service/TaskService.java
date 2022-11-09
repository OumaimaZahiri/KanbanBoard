package com.telecom.applidistribuees.Service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.telecom.applidistribuees.Entity.ChangeLog;
import com.telecom.applidistribuees.Entity.Task;
import com.telecom.applidistribuees.Entity.TaskStatus;
import com.telecom.applidistribuees.Entity.TaskType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public interface TaskService {

	public Collection<Task> findAllTasks();
	public Task moveRightTask(Task task);
	public Task moveLeftTask(Task task);
	public Task addTask(Task task);
	
	public Task getTask(Long id);
	public TaskType getTaskType(Long id);
	public TaskStatus getTaskStatus(Long id);
	String delete(Task task);
	public List<Task> getAllTasks();
	public List<TaskType> getAllTaskTypes();
	public List<TaskStatus> getAllTaskStatus();
	Collection<ChangeLog> findChangeLogsForTask(Task task);
	Task updateStatus(Task task, TaskStatus newStatus);
}
