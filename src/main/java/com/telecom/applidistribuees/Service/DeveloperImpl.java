/**
 * 
 */
package com.telecom.applidistribuees.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.applidistribuees.DAO.DeveloperRepository;
import com.telecom.applidistribuees.Entity.Developer;

/**
 * @author oumai
 *
 */
@Service
public class DeveloperImpl implements DeveloperService {
	
	@Autowired
	private DeveloperRepository developerRepository;
	
	public List<Developer> findAllDevelopers() {
		
		return this.developerRepository.findAll();
	}
}
