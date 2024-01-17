package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Resort {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String resortName;
	
	public Resort() {
		
	}
	
	// getter - setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSlopeName() {
		return resortName;
	}
	public void setSlopeName(String slopeName) {
		this.resortName = slopeName;
	}
	
	
	

}
