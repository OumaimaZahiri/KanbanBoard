package com.telecom.applidistribuees;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.telecom.applidistribuees.DAO.DeveloperRepository;
import com.telecom.applidistribuees.Entity.Developer;


@SpringBootTest
@ActiveProfiles(profiles = "test")
public class DeveloperTest {

	@Autowired
    private DeveloperRepository developerRepository;
	
	@Test
	public void testFindAllDevelopers() {
		
		List<Developer> developers = this.developerRepository.findAll();
		Assert.assertEquals(2, developers.size());
	}
	
	@Test
	public void testSaveDeveloper() {
		Developer developer = new Developer();
        developer.setFirstname("Sully");
        developer.setLastname("Pero");
        developer.setEmail("sully.pero@allez-les-verts.fr");
        developer.setPassword("Sully123");
        developer.setStartContract(LocalDate.now());		
		this.developerRepository.save(developer);	
		List<Developer> developers = this.developerRepository.findAll();	
		Assert.assertEquals(3, developers.size());		
		this.developerRepository.delete(developer);
	}
}