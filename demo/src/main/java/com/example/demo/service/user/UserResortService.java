package com.example.demo.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Resort;
import com.example.demo.repository.user.UserResortRepository;

@Service
public class UserResortService {
	
	@Autowired
	private UserResortRepository userResortRepository;
	
	// 1. 모든 리조트 가져오기.
	public List<Resort> getResorts() {
		return userResortRepository.findAll();
	}

	// 2. 특정 id에 해당하는 리조트만 가져오기.
	public Resort getResort(int id) {		
		Resort resort = userResortRepository.findById(id);
		
		return resort;
	}
	
}
