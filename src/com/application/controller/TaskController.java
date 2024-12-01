package com.application.controller;

import com.application.model.Task;
import com.application.util.TasksFileUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class TaskController implements Initializable {
	
	@FXML RadioButton rb_allTasks;
	@FXML RadioButton rb_completedTasks;
	@FXML RadioButton rb_dueTasks;
	@FXML Button b_createTask;
	@FXML GridPane g_taskGrid;
	@FXML ScrollPane sp_taskList;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Task> allTasks = TasksFileUtil.readAllTasks();
		populatePane(allTasks);
	}
	
	public void showAllList() {
		TaskControllerHelper.toggleRadio(rb_allTasks, rb_completedTasks, rb_dueTasks, "all");
	}
	
	public void showCompletedTask() {
		TaskControllerHelper.toggleRadio(rb_allTasks, rb_completedTasks, rb_dueTasks, "completed");
	}
	
	public void showTasksDueToday() {
		TaskControllerHelper.toggleRadio(rb_allTasks, rb_completedTasks, rb_dueTasks, "due");
	}
	
	void editTask(ActionEvent evt) {
		System.out.println("Edit");
	}
	
	void deleteTask(ActionEvent evt) {
		System.out.println("Delete");
	}
	
	void markComplete(ActionEvent evt) {
		System.out.println("complete");
	}
	
	private void populatePane(ArrayList<Task> taskList) {
		g_taskGrid.getChildren().clear();
		
		int size = taskList.size();
		
		if(size == 0) {
			sp_taskList.setVisible(false);
		} else {
			for(int i = 0; i < size; i++) {
				Task task = taskList.get(i);
				g_taskGrid.addRow(i, TaskControllerHelper.createTaskCard(task, this));
			}
			sp_taskList.setVisible(true);
		}
	}
}
