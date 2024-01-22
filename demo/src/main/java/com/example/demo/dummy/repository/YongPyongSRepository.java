package com.example.demo.dummy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dummy.model.YongPyongSlope;

public interface YongPyongSRepository extends JpaRepository<YongPyongSlope, Integer> {
YongPyongSlope findByslopeName(String slopeName);
void deleteByslopeName(String slopeName);
}
