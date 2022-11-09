package com.telecom.applidistribuees;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.telecom.applidistribuees.DAO.TaskRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class ApplidistribueesApplicationTests {

	@Autowired
	private TaskRepository taskRepository;
		
	@Test
	public void contexLoads() throws Exception {
		assertThat(taskRepository).isNotNull();
	}
}
