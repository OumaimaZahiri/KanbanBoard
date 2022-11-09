package com.telecom.applidistribuees.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telecom.applidistribuees.Entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>  {

}
