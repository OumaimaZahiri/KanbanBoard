/**
 * 
 */
package com.telecom.applidistribuees.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.telecom.applidistribuees.DAO.TaskRepository;
import com.telecom.applidistribuees.DAO.TaskStatusRepository;
import com.telecom.applidistribuees.DAO.TaskTypeRepository;
import com.telecom.applidistribuees.Entity.ChangeLog;
import com.telecom.applidistribuees.Entity.Task;
import com.telecom.applidistribuees.Entity.TaskStatus;
import com.telecom.applidistribuees.Entity.TaskType;
import com.telecom.applidistribuees.utils.Constants;

/**
 * @author oumai
 *
 */

@Service
public class TaskImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskTypeRepository taskTypeRepository;

	@Autowired
	private TaskStatusRepository taskStatusRepository;
	
	@Override
	@Transactional
	public Task addTask(Task task) { 
		task.setStatus(this.getTaskStatus(Constants.TASK_STATUS_ID[0]));
		task.setCreated(LocalDate.now());
		
		return this.taskRepository.save(task); 
	};

	@Override
	@Transactional
	public Task updateStatus(Task task, TaskStatus newStatus) {

		task = this.taskRepository.save(task);
		ChangeLog changeLog = new ChangeLog();
		changeLog.setOccured(LocalDate.now());
		changeLog.setOldStatus(task.getStatus());
		changeLog.setNewStatus(newStatus);
		task.addChangeLog(changeLog);
		task.setStatus(newStatus);
		
		return task;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<Task> findAllTasks() {
		// TODO Auto-generated method stub
		return this.taskRepository.findAll();
	}

	@Override
	@Transactional
	public Task moveRightTask(Task task) {
		TaskStatus oldStatus = task.getStatus();
		TaskStatus newStatus = null;
		
		List<TaskStatus> allStatus = new ArrayList<>();
		
		for (int i=0; i<Constants.TASK_STATUS_ID.length; i++)
			{
				allStatus.add(this.getTaskStatus(Constants.TASK_STATUS_ID[i]));
			}
		
		if (oldStatus!=null)
		{
			if(oldStatus.equals(allStatus.get(-1)))
			{
				throw new IllegalStateException();
			}
			else {
				newStatus = allStatus.get(allStatus.indexOf(oldStatus) + 1);
			}
		}
		
		return this.updateStatus(task, newStatus);
	}

	@Override
	@Transactional
	public Task moveLeftTask(Task task) {
		TaskStatus oldStatus = task.getStatus();
		TaskStatus newStatus = null;
		
		List<TaskStatus> allStatus = new ArrayList<>();
		
		for (int i=0; i<Constants.TASK_STATUS_ID.length; i++)
			{
				allStatus.add(this.getTaskStatus(Constants.TASK_STATUS_ID[i]));
			}
		
		if (oldStatus!=null)
		{
			if(oldStatus.equals(allStatus.get(0)))
			{
				throw new IllegalStateException();
			}
			else {
				newStatus = allStatus.get(allStatus.indexOf(oldStatus) - 1);
			}
		}
		else {
			throw new IllegalArgumentException();
		}
		
		return this.updateStatus(task, newStatus);
	}

	@Override
	@Transactional(readOnly = true)
	public Task getTask(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> getAllTasks() {
		// TODO Auto-generated method stub
		return taskRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public TaskType getTaskType(Long id) {
		// TODO Auto-generated method stub
		return this.taskTypeRepository.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public TaskStatus getTaskStatus(Long id) {
		// TODO Auto-generated method stub
		return this.taskStatusRepository.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TaskType> getAllTaskTypes() {
		// TODO Auto-generated method stub
		return taskTypeRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TaskStatus> getAllTaskStatus() {
		// TODO Auto-generated method stub
		return taskStatusRepository.findAll();
	}

	@Override
	@Transactional
	public String delete(Task task) {
		task.clearChangeLogs();
		taskRepository.delete(task);
		return "Task {taskRepository.findById(id).getTitle()} deleted";
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<ChangeLog> findChangeLogsForTask(Task task) {
		
		Task myTask = this.getTask(task.getId());
		
		if (myTask != null) {
			
			// force initialization
			Hibernate.initialize(myTask.getChangeLogs());
			
			return myTask.getChangeLogs();
		}
		else {
			return new HashSet<>();
		}
	}
	
}
