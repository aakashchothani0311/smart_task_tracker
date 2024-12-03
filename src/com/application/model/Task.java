package com.application.model;

import java.time.LocalDate;

public class Task {
	private int uid;
	private String title;
	private String desc;
	private boolean isCompleted;
	private String priority;
	private LocalDate createdDate;
	private LocalDate dueDate;
	
	public Task(String title, String desc, String priority, LocalDate dueDate){
		this(0, title, desc, false, priority, LocalDate.now(), dueDate);
	}
	
	public Task(int uid, String title, String desc, boolean isCompleted, String priority, LocalDate createdDate, LocalDate dueDate){
		this.uid = uid;
		this.title = title;
		this.desc = desc;
		this.isCompleted = isCompleted;
		this.priority = priority;
		this.createdDate = createdDate;
		this.dueDate = dueDate;
	}
	
	public int getUID() {
		return uid;
	}
	
	public void setUID(int uid) {
		this.uid = uid;
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
	
	public String getPriority() {
		return priority;
	}
	
	public void setPriority(String priority) {
		this.priority = priority;
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