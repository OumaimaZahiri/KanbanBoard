package com.telecom.applidistribuees.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telecom.applidistribuees.Entity.Developer;
import com.telecom.applidistribuees.Service.DeveloperService;

@RestController
public class DeveloperController {

	@Autowired
	private DeveloperService developerService;
	
	@GetMapping("/developers")
	List<Developer> findAllDevelopers() {
		
		return this.developerService.findAllDevelopers();
		
	}
}
