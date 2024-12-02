package com.application.util;

import com.application.model.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
				String[] lineSplit = formatString(line).split("\\|\\|");
				
				int uid = Integer.parseInt(lineSplit[0]);
				String title = formatString(lineSplit[1]);
				String desc = formatString(lineSplit[2]);
				boolean isCompleted = formatString(lineSplit[3]).equals("true") ? true : false;
				LocalDate createdDate = LocalDate.parse(formatString(lineSplit[4]), formatter);
				LocalDate dueDate = LocalDate.parse(formatString(lineSplit[5]), formatter);
				
				allTasks.add(new Task(uid, title, desc, isCompleted, createdDate, dueDate));
			}
			
			return allTasks;
		} catch (FileNotFoundException e) {
			System.out.println("File tasks not found");
		}
		
		return null;
	}
	
	public static void saveTask(Task task) {
	    File file = new File(filePath);
	    File tempFile = new File(filePath + ".tmp");

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
	         Scanner scanner = new Scanner(file)) {

	        boolean taskUpdated = false;

	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            String[] lineSplit = line.split("\\|\\|");
	            int uid = Integer.parseInt(lineSplit[0].strip());

	            if (uid == task.getUID()) {
	                writer.write(formatTask(task));
	                taskUpdated = true;
	            } else {
	                writer.write(line + System.lineSeparator());
	            }
	        }

	        if (!taskUpdated) {
	            writer.write(formatTask(task));
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    if (file.delete()) {
	        if (!tempFile.renameTo(file)) {
	            System.err.println("Failed to rename temp file to original file.");
	        }
	    } else {
	        System.err.println("Failed to delete original file.");
	    }
	}

    private static String formatTask(Task task) {
        return task.getUID() + "||" +
        	   task.getTitle() + "||" +
               task.getDesc() + "||" +
               task.isCompleted() + "||" +
               task.getCreatedDate().format(formatter) + "||" +
               task.getDueDate().format(formatter) + "\n";
    }
	
	private static String formatString(String s) {
		return s.strip();
	}
}