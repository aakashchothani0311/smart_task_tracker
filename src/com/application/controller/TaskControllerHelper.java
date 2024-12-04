package com.application.controller;

import java.io.IOException;

import com.application.model.Task;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TaskControllerHelper {
	
	private static String viewPath = "/com/application/view/";
	
	GridPane createTaskCard(Task task, TaskController controller) {
		Text title = new Text();
		title.setFont(new Font(24));
		title.setText(task.getTitle());
		
		Text desc = new Text();
		desc.setText(task.getDesc());
		desc.setWrappingWidth(800.0);
		
		Text priority = new Text();
		priority.setText("Priority: " + task.getPriority());
		
		Text createdDate = new Text();
		createdDate.setText("Created Date: " + task.getCreatedDate());
		
		Text dueDate = new Text();
		dueDate.setText("Due Date: " + task.getDueDate());
		
		Button edit = createTaskAction("EDIT", String.valueOf(task.getUID()), "#0288d1");
		edit.setOnAction(evt -> controller.editTask(evt));
		
		Button delete = createTaskAction("DELETE", String.valueOf(task.getUID()), "#d32f2f");
		delete.setOnAction(evt -> controller.deleteTask(evt));
		
		Button complete = createTaskAction("MARK COMPLETE", String.valueOf(task.getUID()), "#2e7d32");
		complete.setOnAction(evt -> controller.markComplete(evt));
		
		ButtonBar bb = new ButtonBar();
		bb.getButtons().addAll(edit, delete, complete);
		 
        GridPane gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setPadding(new Insets(20));
		
		if(task.isCompleted())
			gp.setStyle("-fx-border-color: #3edd1e; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-color: #f4fff2");
		else {
			if(task.getPriority().equals("High"))
				gp.setStyle("-fx-border-color: #d32f2f; -fx-border-width: 2; -fx-border-radius: 10");
			else if(task.getPriority().equals("Medium"))
				gp.setStyle("-fx-border-color: #ffbf00; -fx-border-width: 2; -fx-border-radius: 10");
			else
				gp.setStyle("-fx-border-color: #d1d1d1; -fx-border-width: 2; -fx-border-radius: 10");
		}
		
		gp.add(title, 0, 0);
		gp.add(desc, 0, 1);
		gp.setColumnSpan(desc, 3);
		gp.add(priority, 0, 2);
		gp.add(createdDate, 1, 2);
		gp.add(dueDate, 2, 2);
		gp.add(bb, 0, 3);
		gp.setColumnSpan(bb, 3);
		
		return gp;	
	}
	
	private Button createTaskAction(String label, String id, String bgColor) {
		Button b = new Button(label);
		b.setId(id);
		b.setStyle("-fx-background-color: " + bgColor);
		b.setTextFill(javafx.scene.paint.Color.WHITE);
		b.setFont(new Font("System Bold", 13.0));
		return b;
	}
	
	Object showDialog(String viewName, String title) {
	    try {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath + viewName));
	        Pane root = loader.load();
			
			Stage stage = new Stage();
		    stage.setTitle(title);
		    stage.setScene(new Scene(root));
		    stage.show();
		    
		    return loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	void toggleRadio(RadioButton rb_allTasks, RadioButton rb_completedTasks, RadioButton rb_dueTasks, String option) {
		rb_allTasks.setSelected(!option.equals("completed") && !option.equals("due"));
		rb_completedTasks.setSelected(!option.equals("all") && !option.equals("due"));
		rb_dueTasks.setSelected(!option.equals("all") && !option.equals("completed"));
	}
}
