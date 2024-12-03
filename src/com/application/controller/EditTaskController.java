package com.application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.application.model.Task;
import com.application.util.TasksFileUtil;
import com.application.util.UtilClass;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditTaskController {
	
	@FXML TextField taskTitle;
	@FXML TextArea taskDesc;
	@FXML Text createdDate;
	@FXML DatePicker dueDate;
	
	private Task task;
	
	private static TaskController taskController;
	
	public static void setTaskController(TaskController controller) {
	    taskController = controller;
	}
	
	public void setTask(Task task) {
		this.task  = task;
		
		taskTitle.setText(task.getTitle());
		taskDesc.setText(task.getDesc());
		createdDate.setText(task.getCreatedDate().toString());
		dueDate.setPromptText(task.getDueDate().toString());
	}
	
	@FXML
	public void handleSave() {
		String title = taskTitle.getText();
		String desc = taskDesc.getText();
		
		if(title == null ||title.trim().isEmpty()) {
			UtilClass.showAlert(AlertType.ERROR,"Error", "Invalid Input", "Title field cannot be empty." );
			return;
		}
		
		if(desc == null || desc.trim().isEmpty()) {
			UtilClass.showAlert(AlertType.ERROR, "Error", "Invalid Input", "Title Description field cannot be empty.");
			return;
		}
		
	    task.setTitle(title);
	    task.setDesc(taskDesc.getText());
	    if (dueDate.getValue() != null) {
	        task.setDueDate(dueDate.getValue());
	    } else {
	        task.setDueDate(LocalDate.now());
	    }
	    
	    if (taskController != null) {
	        taskController.handleEdit();
	    }
	    TasksFileUtil.updateTask(task);

	    Stage stage = (Stage) taskTitle.getScene().getWindow();
	    stage.close();
	}
	
}
