package com.telecom.applidistribuees.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telecom.applidistribuees.Entity.TaskStatus;

public interface TaskStatusRepository  extends JpaRepository<TaskStatus, Long>{

}
