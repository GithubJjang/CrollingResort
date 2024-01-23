package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Slope {
	
	// 수정보류
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	private Resort resort;
	
	private String difficulty;
	private String slopeName;
	private String day;
	private String night;
	private String dawn;
	// 수정
	private LocalDate date;
	
	public Slope() {
		
	}
	
	// getter - setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Resort getResort() {
		return resort;
	}
	public void setResort(Resort resort) {
		this.resort = resort;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public String getSlopeName() {
		return slopeName;
	}
	public void setSlopeName(String slopeName) {
		this.slopeName = slopeName;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getNight() {
		return night;
	}
	public void setNight(String night) {
		this.night = night;
	}
	public String getDawn() {
		return dawn;
	}
	public void setDawn(String dawn) {
		this.dawn = dawn;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	// 빌더패턴
	// id는 auto_increment라서 안해도 됨.
	
	public Slope buildResort(Resort resort) {
		this.resort=resort;
		return this;
	}

	public Slope buildSlopeName(String slopeName) {
		this.slopeName=slopeName;
		return this;
	}

	public Slope buildDifficulty(String difficulty) {
		this.difficulty=difficulty;
		return this;
	}

	public Slope buildDay(String day) {
		this.day=day;
		return this;
	}

	public Slope buildNight(String night) {
		this.night=night;
		return this;
	}
	
	public Slope buildDawn(String dawn) {
		this.dawn=dawn;
		return this;
	}
	
	public Slope buildDate(LocalDate date) {
		this.date=date;
		return this;
	}
}
