package com.application.controller;

import com.application.model.Task;
import com.application.util.CustomHeap;
import com.application.util.TasksFileUtil;
import com.application.util.UtilClass;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class TaskController {
	
	@FXML RadioButton rb_allTasks;
	@FXML RadioButton rb_completedTasks;
	@FXML RadioButton rb_dueTasks;
	@FXML RadioButton rb_priorityView;
	@FXML Button b_createTask;
	@FXML GridPane g_taskGrid;
	@FXML ScrollPane sp_taskList;
	
	private TaskControllerHelper helper;
	private static ArrayList<Task> allTasks;
	
	@FXML
	private void initialize() {
		helper = new TaskControllerHelper();
		
		allTasks = TasksFileUtil.readAllTasks();
		populatePane(allTasks);
	}

	@FXML
	private void showAllList() {
		populatePane(allTasks);
		helper.toggleRadio(rb_allTasks, rb_completedTasks, rb_dueTasks, rb_priorityView, "all");
	}
	
	@FXML
	private void showCompletedTask() {
		ArrayList<Task> completedTasks = new ArrayList<>();
	    for (Task task : allTasks) {
	        if (task.isCompleted())
	            completedTasks.add(task);
	    }
	    
	    populatePane(completedTasks);
		helper.toggleRadio(rb_allTasks, rb_completedTasks, rb_dueTasks, rb_priorityView, "completed");
	}
	
	@FXML
	private void showTasksDueToday() {
		ArrayList<Task> tasksDue = new ArrayList<>();
	    for (Task task : allTasks) {
	        if (!task.isCompleted() && LocalDate.now().equals(task.getDueDate()))
	        	tasksDue.add(task);      
	    }
	    
	    populatePane(tasksDue);
		helper.toggleRadio(rb_allTasks, rb_completedTasks, rb_dueTasks, rb_priorityView, "due");
	}
	
	@FXML
	private void showPriorityView() {
		CustomHeap<Task> heap = new CustomHeap<>();
		
		for (Task task : allTasks) {
			if(!task.isCompleted())
				heap.insert(task);
		}
	    
		ArrayList<Task> priorityList = (ArrayList<Task>) heap.toSortedList();
		populatePane(priorityList);
		
		helper.toggleRadio(rb_allTasks, rb_completedTasks, rb_dueTasks, rb_priorityView, "priority");
	}
	
	@FXML
	private void handleCreateTask(ActionEvent event) {
		helper.showDialog("AddTask.fxml", "Add New Task");
	    AddTaskController.setTaskController(this);
	}
	
	void handleAdd(Task task) {
		allTasks.add(task);
        viewToShow();
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
		viewToShow();
	}
	
	void deleteTask(ActionEvent evt) {
		Button source = (Button)evt.getSource();
		int taskID = Integer.parseInt(source.getId());

		int size = allTasks.size();
		
		for(int i = 0; i < size; i++) {
			Task temp = allTasks.get(i);
			
			if(temp.getUID() == taskID) {
				if(TasksFileUtil.deleteTask(taskID)) {
					allTasks.remove(i);
					viewToShow();
		        	UtilClass.showAlert(AlertType.INFORMATION, "Success", "Task Deleted Successfully.", "");
				} else
					UtilClass.showAlert(AlertType.ERROR, "Error", "Task not deleted.", "Some error occured while deleting the task.");
				 
				break;
			}
		}
	}
	
	void markComplete(ActionEvent evt) {
	    Button source = (Button) evt.getSource();
	    int taskId = Integer.parseInt(source.getId());
	    
	    Task taskToUpdate = allTasks.stream()
	                                   .filter(t -> t.getUID() == taskId)
	                                   .findFirst()
	                                   .orElse(null);
	    
	    boolean currentState = taskToUpdate.isCompleted();

	    taskToUpdate.setCompleted(!currentState);  
   
		if(TasksFileUtil.updateTaskInFile(taskToUpdate)) {
			viewToShow();
			
		    if(currentState) {
		    	source.setText("MARK COMPLETE");
		    	source.setStyle("-fx-background-color: #2e7d32");
		    	UtilClass.showAlert(AlertType.INFORMATION, "Success", "Task marked as incomplete.", "");
		    } else {
		    	source.setText("MARK INCOMPLETE");
		    	source.setStyle("-fx-background-color: #ffbf00");
		    	UtilClass.showAlert(AlertType.INFORMATION, "Success", "Task completed successfully.", "");
		    }   	
		}
	}
	
	private void viewToShow() {
		if (rb_allTasks.isSelected())
	        populatePane(allTasks);
	    else if (rb_completedTasks.isSelected())
	    	showCompletedTask();
	    else if(rb_dueTasks.isSelected())
	        showTasksDueToday();
	    else
	    	showPriorityView();
	}
	       
	private void populatePane(ArrayList<Task> taskList) {
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
