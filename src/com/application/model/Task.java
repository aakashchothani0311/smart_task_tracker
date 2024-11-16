package com.application.model;

import java.time.LocalDate;

public class Task {
	private String title;
	private String desc;
	private boolean isCompleted;
	private LocalDate createdDate;
	
	Task(String title, String desc, boolean isCompleted){
		this(title, desc, isCompleted, LocalDate.now());
	}
	
	Task(String title, String desc, boolean isCompleted, LocalDate createdDate){
		this.title = title;
		this.desc = desc;
		this.isCompleted = isCompleted;
		this.createdDate = createdDate;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public boolean isCompleted() {
		return isCompleted;
	}
	
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
}