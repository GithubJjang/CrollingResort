package com.example.demo.dummy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dummy.model.GonjiSlope;

public interface GonjiRepository extends JpaRepository<GonjiSlope, Integer>{
	GonjiSlope findBySlopeName(String slopeName);
	void deleteByslopeName(String slopeName);

}
