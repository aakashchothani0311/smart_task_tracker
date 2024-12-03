package com.application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.application.model.Task;
import com.application.util.TasksFileUtil;
import com.application.util.UtilClass;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddTaskController implements Initializable {
	@FXML TextField taskTitle;
	@FXML TextArea taskDesc;
	@FXML Text createdDate;
	@FXML DatePicker dueDate;
	
	private static TaskController taskController;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		createdDate.setText(LocalDate.now().toString());
	}
	
	public static void setTaskController(TaskController controller) {
	    taskController = controller;
	}
	
	@FXML
	private void handleAddTask() {
        String title = taskTitle.getText();
        String description = taskDesc.getText();
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
        }
        
        Task newTask = new Task(20, title, description, dueTask);
        TasksFileUtil.appendTaskToFile(newTask);
        taskController.handleAdd(newTask);
        
        Stage stage = (Stage) taskTitle.getScene().getWindow();
        stage.close();
    }	
}