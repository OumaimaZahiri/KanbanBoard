package com.telecom.applidistribuees.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.telecom.applidistribuees.Entity.Developer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public interface DeveloperService {
	public List<Developer> findAllDevelopers();
}
