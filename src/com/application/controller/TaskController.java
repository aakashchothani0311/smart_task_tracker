package com.application.controller;

import com.application.model.Task;
import com.application.util.TasksFileUtil;

import java.io.IOException;
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
	
	private TaskControllerHelper helper;
	private static ArrayList<Task> allTasks;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		helper = new TaskControllerHelper();
		
		
		allTasks = TasksFileUtil.readAllTasks();
		populatePane(allTasks);
	}

	@FXML
	private void showAllList() {
		helper.toggleRadio(rb_allTasks, rb_completedTasks, rb_dueTasks, "all");
		populatePane(allTasks);
	}
	
	@FXML
	private void showCompletedTask() {
		helper.toggleRadio(rb_allTasks, rb_completedTasks, rb_dueTasks, "completed");
	}
	
	@FXML
	private void showTasksDueToday() {
		helper.toggleRadio(rb_allTasks, rb_completedTasks, rb_dueTasks, "due");
	}
	
	@FXML
	private void handleCreateTask(ActionEvent event) throws IOException {
		helper.showDialog("AddTask.fxml", "Add New Task");
	    AddTaskController.setTaskController(this);
	}
	
	void handleAdd(Task task) {
		allTasks.add(task);
        populatePane(allTasks);
	}
	
	void editTask(ActionEvent evt) {
	    Button source = (Button) evt.getSource();
	    int taskId = Integer.parseInt(source.getId());
	    
	    for(Task taskToEdit : allTasks) {
	    	if(taskToEdit.getUID() == taskId) {
	    		EditTaskController et = (EditTaskController) helper.showDialog("EditTask.fxml", "Edit Task");
	    		et.setTaskController(this);
	    		et.setTask(taskToEdit);
	    		break;
	    	}
	    }
	}
	
	void handleEdit() {
	    populatePane(allTasks);
	}
	
	void deleteTask(ActionEvent evt) {
		Button source = (Button)evt.getSource();
		int taskID = Integer.parseInt(source.getId());
		
		boolean taskRemoved = allTasks.removeIf(task -> task.getUID() == taskID);
		
		if(taskRemoved) {
		//	TasksFileUtil.deleteTask(taskID);
			populatePane(allTasks);
		}
	}
	
	void markComplete(ActionEvent evt) {
		System.out.println("complete");
	}
	
	public void populatePane(ArrayList<Task> taskList) {
		g_taskGrid.getChildren().clear();
		
		int size = taskList.size();
		
		if(size == 0) {
			sp_taskList.setVisible(false);
		} else {
			for(int i = 0; i < size; i++) {
				Task task = taskList.get(i);
				g_taskGrid.addRow(i, helper.createTaskCard(task, this));
			}
			sp_taskList.setVisible(true);
		}
	}
}
