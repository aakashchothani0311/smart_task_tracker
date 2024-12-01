package com.application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.application.model.Task;
import com.application.util.TasksFileUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TaskController implements Initializable {
	
	@FXML RadioButton rb_allTasks;
	@FXML RadioButton rb_completedTasks;
	@FXML RadioButton rb_dueTasks;
	@FXML Button b_createTask;
	@FXML GridPane g_taskGrid;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Task> allTasks = TasksFileUtil.readAllTasks();
	//	populatePane(allTasks);

	}

	public void showAllList() {
		toggleRadio("all");
	}
	
	public void showCompletedTask() {
		toggleRadio("completed");
	}
	
	public void showTasksDueToday() {
		toggleRadio("due");
	}
	
	private void populatePane(ArrayList<Task> taskList) {
		int len = taskList.size();
		
		for(int i = 0; i < len; i++) {
			Task task = taskList.get(i);
			
			Text text = new Text();
			text.setFont(new Font(16));
			text.setText(task.getTitle());
			
			Pane p = new Pane();
			p.getChildren().addAll(text);
			
			if(task.isCompleted())	
				p.setStyle("-fx-background-color: #dbffd4;");
				
			g_taskGrid.addRow(i, p);
		}
	}
	
	private void toggleRadio(String option) {
		rb_allTasks.setSelected(!option.equals("completed") && !option.equals("due"));
		rb_completedTasks.setSelected(!option.equals("all") && !option.equals("due"));
		rb_dueTasks.setSelected(!option.equals("all") && !option.equals("completed"));
	}
	
	@FXML
	private void handleCreateTask(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/application/view/AddTask.fxml"));
	        Pane root = loader.load();

	        Stage stage = new Stage();
	        stage.setTitle("Add New Task");
	        stage.setScene(new Scene(root));
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
