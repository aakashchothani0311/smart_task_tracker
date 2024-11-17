package com.application;

import com.application.util.TasksFileUtil;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	private static Stage stg;
	private String viewPath = "/com/application/view/";
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		stg = primaryStage;
		
		Parent root = FXMLLoader.load(getClass().getResource(viewPath + "Main.fxml"));
		Scene scene = new Scene(root);
				
		primaryStage.setTitle("SMART Task Tracker"); 
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.show();
	}
	
	public void successfulLogin() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(viewPath + "Main.fxml"));
		Scene scene = new Scene(root);
		
		//stg.setMaximized(true);
		
		stg.setTitle("My Tasks");
		stg.setScene(scene);
	}
	
	public static void main(String[] args) {
		launch(args);
//		TasksFileUtil.readAllTasks();
	}
}
