package com.telecom.applidistribuees.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telecom.applidistribuees.Entity.TaskType;

public interface TaskTypeRepository  extends JpaRepository<TaskType, Long>{
	
}
