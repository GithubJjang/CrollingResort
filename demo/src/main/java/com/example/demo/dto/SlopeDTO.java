package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.Resort;

public class SlopeDTO {

	private int slopeId;// 식별자 역할
	private int resortId;// resort의 PK를 가지고 있음.
	private String difficulty;
	private String slopeName;
	private String day;
	private String night;
	private String dawn;
	private String date;
	
	public SlopeDTO() {
		
	}

	// getter - setter
	public int getId() {
		return slopeId;
	}

	public void setId(int slopeId) {
		this.slopeId = slopeId;
	}

	public int getResort() {
		return resortId;
	}

	public void setResort(int resortId) {
		this.resortId = resortId;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	// 빌더 패턴
	public SlopeDTO buildId(int slopeId) {
		this.slopeId=slopeId;
		return this;
	}
	
	public SlopeDTO buildResort(int resortId) {
		this.resortId=resortId;
		return this;
	}

	public SlopeDTO buildSlopeName(String slopeName) {
		this.slopeName=slopeName;
		return this;
	}

	public SlopeDTO buildDifficulty(String difficulty) {
		this.difficulty=difficulty;
		return this;
	}

	public SlopeDTO buildDay(String day) {
		this.day=day;
		return this;
	}

	public SlopeDTO buildNight(String night) {
		this.night=night;
		return this;
	}
	
	public SlopeDTO buildDawn(String dawn) {
		this.dawn=dawn;
		return this;
	}
	
	public SlopeDTO buildDate(LocalDate date) {
		this.date = date.toString();
		return this;
	}
	
	

}
