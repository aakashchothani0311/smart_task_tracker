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

public class AddTaskController {
	@FXML TextField taskTitle;
	@FXML TextArea taskDesc;
	@FXML ComboBox<String> taskPriority;
	@FXML Text createdDate;
	@FXML DatePicker dueDate;
	
	private static TaskController taskController;
	
    @FXML
	private void initialize() {
		createdDate.setText(LocalDate.now().toString());
		taskPriority.setItems(FXCollections.observableArrayList("Low", "Medium", "High"));
		taskPriority.setValue("Low");
	}
	
	public static void setTaskController(TaskController controller) {
	    taskController = controller;
	}
	
	@FXML
	private void handleAddTask() {
        String title = taskTitle.getText();
        String description = taskDesc.getText();
        String priority = (String) taskPriority.getValue();
        LocalDate dueTask = dueDate.getValue();
        
        if (title == null || title.trim().isEmpty()) {
            UtilClass.showAlert(AlertType.ERROR, "Error", "Invalid Input", "Task title cannot be empty.");
            return;
        }

        if (description == null || description.trim().isEmpty()) {
            UtilClass.showAlert(AlertType.ERROR, "Error", "Invalid Input", "Task description cannot be empty.");
            return;
        }
        
        if (dueTask == null) {
            UtilClass.showAlert(AlertType.ERROR, "Error", "Invalid Input", "Due date must be selected.");
            return;
        } else {
        	if(dueTask.isBefore(LocalDate.now())) {
        		UtilClass.showAlert(AlertType.ERROR, "Error", "Invalid Input", "Due date must be after created date.");
                return;
        	}
        }
        
        Task newTask = new Task(title, description, priority, dueTask);
        boolean taskAdded = TasksFileUtil.appendTaskToFile(newTask);
        
        if(taskAdded) {
        	taskController.handleAdd(newTask);
        	UtilClass.showAlert(AlertType.INFORMATION, "Success", "Task Added Successfully.", "");
        } else
        	UtilClass.showAlert(AlertType.ERROR, "Error", "Task not created.", "Some error occured while creating the new task.");
        
        Stage stage = (Stage) taskTitle.getScene().getWindow();
        stage.close();
    }	
}