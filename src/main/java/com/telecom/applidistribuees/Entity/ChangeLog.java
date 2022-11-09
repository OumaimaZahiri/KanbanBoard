/**
 * 
 */
package com.telecom.applidistribuees.Entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author oumai
 *
 */

@Entity
@Table(name = "ChangeLog")
public class ChangeLog {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * @return the newStatus
	 */
	public TaskStatus getNewStatus() {
		return newStatus;
	}

	/**
	 * @return the oldStatus
	 */
	public TaskStatus getOldStatus() {
		return oldStatus;
	}

	private LocalDate occured;
	
	@ManyToOne
	@JsonIgnoreProperties("changeLogs")
	private Task task;
	
	@ManyToOne
	private TaskStatus newStatus;
	
	@ManyToOne
	private TaskStatus oldStatus;
	
	
	public ChangeLog() {
		
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the occured
	 */
	public LocalDate getOccured() {
		return occured;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param occured the occured to set
	 */
	public void setOccured(LocalDate occured) {
		this.occured = occured;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void setOldStatus(TaskStatus status) {
		this.oldStatus = status;
	}

	public void setNewStatus(TaskStatus newStatus) {
		this.newStatus = newStatus;
	}
}
