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
	public void start(Stage primaryStage) {
		stg = primaryStage;
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource(viewPath + "Main.fxml"));
			stg.setTitle("Login"); 
			stg.setScene(new Scene(root));
			stg.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void successfulLogin() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(viewPath + "Main.fxml"));
			stg.setTitle("SMART Task Tracker");
			stg.setMinWidth(1200);
		    stg.setMinHeight(800);
			stg.setScene(new Scene(root));
			stg.show();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {
		launch(args);	
		
//		CustomHeap<Integer> heap = new CustomHeap<Integer>();
//		int[] temp = new int[] {5, 7, 8, 1, 2,5, 10, -2};
//		
////		int[] temp = new int[] {7, 5, 2, 1};
//		
//		for(int t : temp)
//			heap.insert(t);
//		
//		for(int t : heap.toList())
//			System.out.println(t);
	}
}
