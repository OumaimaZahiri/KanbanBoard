package com.telecom.applidistribuees.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telecom.applidistribuees.Entity.ChangeLog;

public interface ChangeLogRepository  extends JpaRepository<ChangeLog, Long>{

}
