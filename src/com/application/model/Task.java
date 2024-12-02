package com.application.model;

import java.time.LocalDate;

public class Task {
	private String title;
	private String desc;
	private boolean isCompleted;
	private LocalDate createdDate;
	private LocalDate dueDate;
	
	public Task(String title, String desc, LocalDate dueDate){
		this(title, desc, false, LocalDate.now(), dueDate);
	}
	
	public Task(String title, String desc, boolean isCompleted, LocalDate createdDate, LocalDate dueDate){
		this.title = title;
		this.desc = desc;
		this.isCompleted = isCompleted;
		this.createdDate = createdDate;
		this.dueDate = dueDate;
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
	
	public LocalDate getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
}