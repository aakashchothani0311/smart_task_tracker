package com.application;

import java.io.IOException;

import com.application.util.CustomHeap;

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
				
		primaryStage.setTitle("Login"); 
		primaryStage.setScene(new Scene(root));
		
		primaryStage.setMinWidth(1200);
	    primaryStage.setMinHeight(800);
		primaryStage.show();
	}
	
	public void successfulLogin() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(viewPath + "Main.fxml"));
		Scene scene = new Scene(root);
		
		stg.setTitle("SMART Task Tracker");
		stg.setScene(scene);
	}
	
	public static void main(String[] args) {
		//launch(args);	
		
		CustomHeap<Integer> heap = new CustomHeap<Integer>();
		int[] temp = new int[] {5, 7, 8, 1, 2,5, 10, -2};
		
//		int[] temp = new int[] {7, 5, 2, 1};
		
		for(int t : temp)
			heap.insert(t);
		
		for(int t : heap.toList())
			System.out.println(t);
	}
}
