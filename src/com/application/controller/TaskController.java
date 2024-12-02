package com.application.controller;

import com.application.model.Task;
import com.application.util.TasksFileUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TaskController implements Initializable {
	
	@FXML RadioButton rb_allTasks;
	@FXML RadioButton rb_completedTasks;
	@FXML RadioButton rb_dueTasks;
	@FXML Button b_createTask;
	@FXML GridPane g_taskGrid;
	@FXML ScrollPane sp_taskList;
	
	private static ArrayList<Task> allTasks;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		allTasks = TasksFileUtil.readAllTasks();
		populatePane(allTasks);
	}

	@FXML
	private void showAllList() {
		TaskControllerHelper.toggleRadio(rb_allTasks, rb_completedTasks, rb_dueTasks, "all");
		populatePane(allTasks);
	}
	
	@FXML
	private void showCompletedTask() {
		TaskControllerHelper.toggleRadio(rb_allTasks, rb_completedTasks, rb_dueTasks, "completed");
	}
	
	@FXML
	private void showTasksDueToday() {
		TaskControllerHelper.toggleRadio(rb_allTasks, rb_completedTasks, rb_dueTasks, "due");
	}
	
	@FXML
	private void handleCreateTask(ActionEvent event) throws IOException {
	    Pane root = FXMLLoader.load(getClass().getResource("/com/application/view/AddTask.fxml"));
	    
	    Stage stage = new Stage();
	    stage.setTitle("Add New Task");
	    stage.setScene(new Scene(root));
	    stage.show();
	    
	    AddTaskController.setTaskController(this);
	}
	
	public void handleAdd(Task task) {
		allTasks.add(task);
        populatePane(allTasks);
	}
	
	void editTask(ActionEvent evt) {
	    Button source = (Button) evt.getSource();
	    int taskId = Integer.parseInt(source.getId());
	    
	    Task taskToEdit = allTasks.stream().filter(t -> t.getUID() == taskId).findFirst().orElse(null);
	    
	    if (taskToEdit != null) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/application/view/EditTask.fxml"));
	            Pane root = loader.load();
	            
	            EditTaskController editController = loader.getController();
	            editController.setTask(taskToEdit);
	            editController.setTaskController(this);

	            Stage stage = new Stage();
	            stage.setTitle("Edit Task");
	            stage.setScene(new Scene(root));
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public void handleEdit() {
	    populatePane(allTasks);
	}
	
	void deleteTask(ActionEvent evt) {
		System.out.println("Delete");
		
		Button source = (Button)evt.getSource();
		int taskID = Integer.parseInt(source.getId());
		
		boolean taskRemoved = allTasks.removeIf(task -> task.getUID() == taskID);
		
		if(taskRemoved) {
			TasksFileUtil.deleteTask(taskID);
			
			populatePane(allTasks);
		}
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
