/**
 * 
 */
package com.telecom.applidistribuees.Entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author oumai
 *
 */
@Entity
@RequiredArgsConstructor
@Table(name = "Task")
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String title;

	@ManyToOne
	private TaskStatus status;

	@ManyToOne
	private TaskType type;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
    private Set<Developer> developers;

	@OneToMany(mappedBy="task", cascade={CascadeType.ALL}, orphanRemoval=true)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<ChangeLog> changeLogs;

	private Integer nbHoursForecast;

	private Integer nbHoursReal;

	private LocalDate created;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @return the status
	 */
	public TaskStatus getStatus() {
		return status;
	}
	/**
	 * @return the type
	 */
	public TaskType getType() {
		return type;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @return the nbHoursForecast
	 */
	public Integer getNbHoursForecast() {
		return nbHoursForecast;
	}
	/**
	 * @return the nbHoursReal
	 */
	public Integer getNbHoursReal() {
		return nbHoursReal;
	}
	/**
	 * @return the created
	 */
	public LocalDate getCreated() {
		return created;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	/**
	 * @return the developers
	 */
	public Set<Developer> getDevelopers() {
		return developers;
	}
	/**
	 * @param developers the developers to set
	 */
	public void setDevelopers(Set<Developer> developers) {
		this.developers = developers;
	}
	/**
	 * @param changeLogs the changeLogs to set
	 */
	public void setChangeLogs(Set<ChangeLog> changeLogs) {
		this.changeLogs = changeLogs;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(TaskType type) {
		this.type = type;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @param nbHoursForecast the nbHoursForecast to set
	 */
	public void setNbHoursForecast(Integer nbHoursForecast) {
		this.nbHoursForecast = nbHoursForecast;
	}
	/**
	 * @param nbHoursReal the nbHoursReal to set
	 */
	public void setNbHoursReal(Integer nbHoursReal) {
		this.nbHoursReal = nbHoursReal;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(LocalDate created) {
		this.created = created;
	}
	
	public void addDeveloper(Developer developer) {
		this.developers.add(developer);
	}


	public void addChangeLog(ChangeLog changeLog) {
		changeLog.setTask(this);
		this.changeLogs.add(changeLog);
	}

	public void clearChangeLogs() {	
		for (ChangeLog changeLog :  this.changeLogs) {
			changeLog.setTask(null);
		}
		this.changeLogs.clear();
	}
	
	public Set<ChangeLog> getChangeLogs() {
		return this.changeLogs;
	}
}
