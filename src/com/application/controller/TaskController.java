package com.application.controller;

import com.application.model.Task;

import com.application.util.TasksFileUtil;
import com.application.util.UtilClass;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class TaskController {
	
	@FXML RadioButton rb_allTasks;
	@FXML RadioButton rb_completedTasks;
	@FXML RadioButton rb_dueTasks;
	@FXML Button b_createTask;
	@FXML GridPane g_taskGrid;
	@FXML ScrollPane sp_taskList;
	
	private TaskControllerHelper helper;
	private static ArrayList<Task> allTasks;
	
	@FXML
	public void initialize() {
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
		ArrayList<Task> completedTasks = new ArrayList<>();
    
	    for (Task task : allTasks) {
	        if (task.isCompleted()) {
	            completedTasks.add(task);
	        }
	        
	    }
	    populatePane(completedTasks);
		helper.toggleRadio(rb_allTasks, rb_completedTasks, rb_dueTasks, "completed");
	}
	
	@FXML
	private void showTasksDueToday() {
		ArrayList<Task> tasksDue = new ArrayList<>();
	    for (Task task : allTasks) {
	        if (!task.isCompleted() && LocalDate.now().equals(task.getDueDate())) {
	        	tasksDue.add(task);
	        }
	        
	    }
	    populatePane(tasksDue);
    
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
		
		int size = allTasks.size();
		
		for(int i = 0; i < size; i++) {
			Task temp = allTasks.get(i);
			
			if(temp.getUID() == taskID) {
				if(TasksFileUtil.deleteTask(taskID)) {
					allTasks.remove(i);
					populatePane(allTasks);
		        	UtilClass.showAlert(AlertType.INFORMATION, "Success", "Task Deleted Successfully..", "");
				} else
					UtilClass.showAlert(AlertType.ERROR, "Error", "Task not deleted.", "Some error occured while deleting the task.");
				 
				break;
			}
		}
	}
	
	
	void markComplete(ActionEvent evt) {
		System.out.println("Complete");

	    // Get the task ID from the button's ID
	    Button source = (Button) evt.getSource();
	    int taskId = Integer.parseInt(source.getId());

	    // Find the task in the allTasks list
	    Task taskToComplete = allTasks.stream()
	                                   .filter(t -> t.getUID() == taskId)
	                                   .findFirst()
	                                   .orElse(null);

	    if (taskToComplete != null) {
	    	
	        //Confirmation dialog
	        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
	        confirmationAlert.setTitle("Confirm Completion");
	        confirmationAlert.setHeaderText("Are you sure you want to complete this task?");
	        confirmationAlert.setContentText("Once completed, this task cannot be undone.");

	        //Display Confirmation Dialog
	        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

	        if (result == ButtonType.OK) {
	            
	            taskToComplete.setCompleted(true);  

	           
	            TasksFileUtil.saveTask(taskToComplete);

	            //Refresh List
	            if (rb_allTasks.isSelected()) {
	                populatePane(allTasks);
	                System.out.println("All Task Refreshed");
	            } else if (rb_dueTasks.isSelected()) {
	                showTasksDueToday();
	                System.out.println("Due Today Task Refreshed.");

	           
	        } else {
	            
	            System.out.println("Task completion canceled.");
	        }
	       }
	    }
	       
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
