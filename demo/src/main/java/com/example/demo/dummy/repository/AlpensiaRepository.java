package com.example.demo.dummy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dummy.model.AlpensiaSlope;

public interface AlpensiaRepository extends JpaRepository<AlpensiaSlope, Integer>{
	AlpensiaSlope findBySlopeName(String slopeName);
	void deleteByslopeName(String slopeName);

}
