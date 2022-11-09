package com.telecom.applidistribuees.Entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TaskType")
public class TaskType {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;
	

	public TaskType(Long id, String label) {
		this.id = id;
		this.label = label;
	}

	@Column
	public String label;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
}
