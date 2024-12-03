package com.application.controller;

import java.time.LocalDate;

import com.application.model.Task;
import com.application.util.TasksFileUtil;
import com.application.util.UtilClass;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditTaskController {
	
	@FXML TextField taskTitle;
	@FXML TextArea taskDesc;
	@FXML ComboBox<String> taskPriority;
	@FXML Text createdDate;
	@FXML DatePicker dueDate;
	
	private Task task;
	private TaskController taskController;
	
	@FXML
    private void initialize() {
	 taskPriority.setItems(FXCollections.observableArrayList("Low", "Medium", "High"));
    }
	 
	public void setTaskController(TaskController controller) {
	    taskController = controller;
	}
	
	public void setTask(Task task) {
		this.task = task;
		
		taskTitle.setText(task.getTitle());
		taskDesc.setText(task.getDesc());
		taskPriority.setValue(task.getPriority());
		createdDate.setText(task.getCreatedDate().toString());
		dueDate.setValue(task.getDueDate());
	}
	
	@FXML
	private void handleTaskUpdate() {
		String title = taskTitle.getText();
		String desc = taskDesc.getText();
		String priority = (String)taskPriority.getValue();
		LocalDate dd = dueDate.getValue();
		
		if(title == null || title.trim().isEmpty()) {
			UtilClass.showAlert(AlertType.ERROR, "Error", "Invalid Input", "Title field cannot be empty.");
			return;
		}
		
		if(desc == null || desc.trim().isEmpty()) {
			UtilClass.showAlert(AlertType.ERROR, "Error", "Invalid Input", "Title Description field cannot be empty.");
			return;
		}
		
		if(dd == null) {
			UtilClass.showAlert(AlertType.ERROR, "Error", "Invalid Input", "Due Date field cannot be empty.");
			return;
		}		
		
	    task.setTitle(title);
	    task.setDesc(taskDesc.getText());
	    task.setPriority(priority);
	    task.setDueDate(dd);
	        
	    TasksFileUtil.updateTaskInFile(task);
	    taskController.handleEdit();

	    Stage stage = (Stage) taskTitle.getScene().getWindow();
	    stage.close();
	}
	
}
