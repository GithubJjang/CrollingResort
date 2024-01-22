package com.example.demo.dummy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dummy.model.High1Slope;
import com.example.demo.dummy.model.YongPyongSlope;

public interface High1Repository extends JpaRepository<High1Slope, Integer> {
	High1Slope findBySlopeNameAndSlopeTag(String slopeName, String slopeTag);
	void deleteByslopeName(String slopeName);
}
