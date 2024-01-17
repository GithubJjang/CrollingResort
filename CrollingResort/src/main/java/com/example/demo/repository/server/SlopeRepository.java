package com.example.demo.repository.server;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Resort;
import com.example.demo.model.Slope;

public interface SlopeRepository extends JpaRepository<Slope, Integer> {
	Slope findBySlopeNameAndResortAndDate(String slopeName, Resort resort, LocalDate date);
	//void deleteBySlopeNameAndResort(String key, Resort resort);
	void deleteBySlopeNameAndResortAndDate(String key, Resort resort,LocalDate date);
}
