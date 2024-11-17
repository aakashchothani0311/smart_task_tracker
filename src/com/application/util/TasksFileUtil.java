package com.application.util;

import com.application.model.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TasksFileUtil {
	
	private static String filePath = "data/tasks";
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	public static ArrayList<Task> readAllTasks() {
		ArrayList<Task> allTasks = new ArrayList<>();
		
		try(Scanner fin = new Scanner(new File(filePath))){     
			while(fin.hasNextLine()) {
				String line = fin.nextLine();
				System.out.println(line);
				
				String[] lineSplit = formatString(line).split("\\|\\|");
				
				String title = formatString(lineSplit[0]);
				String desc = formatString(lineSplit[1]);
				boolean isCompleted = formatString(lineSplit[2]).equals("true") ? true : false;
				LocalDate createdDate = LocalDate.parse(formatString(lineSplit[3]), formatter);
				
				allTasks.add(new Task(title, desc, isCompleted, createdDate));
			}
			
			return allTasks;
		} catch (FileNotFoundException e) {
			System.out.println("File tasks not found");
		}
		
		return null;
	}
	
	private static String formatString(String s) {
		return s.strip();
	}
}