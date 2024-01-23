package com.example.demo.dummy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dummy.model.PhyungChangSlope;
import com.example.demo.dummy.model.YongPyongSlope;

public interface PhyungChangRepository extends JpaRepository<PhyungChangSlope, Integer> {
PhyungChangSlope findByslopeName(String slopeName);
void deleteByslopeName(String slopeName);
}
