package com.application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.application.model.Task;
import com.application.util.TasksFileUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AddTaskController implements Initializable {
	@FXML TextField taskTitle;
	@FXML TextArea taskDesc;
	@FXML Text createdDate;
	@FXML DatePicker dueDate;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		createdDate.setText(LocalDate.now().toString());
	}
	
	@FXML
	private void handleAddTask() {
		
		String title = taskTitle.getText();
		String description = taskDesc.getText();
		LocalDate dueTask = dueDate.getValue();
		
		Task newTask = new Task(title,description, dueTask);
		
		TasksFileUtil.saveTask(newTask);
	}
	
	
}