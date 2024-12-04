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
import java.time.format.DateTimeParseException;

public class TasksFileUtil {
	
	private static String filePath = "data/tasks";
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	public static ArrayList<Task> readAllTasks() {
	    ArrayList<Task> allTasks = new ArrayList<>();

	    try (Scanner fin = new Scanner(new File(filePath))) {
	        while (fin.hasNextLine()) {
	            String line = fin.nextLine().strip();
	           /* if (line.isEmpty()) {
	                continue;
	            }

	            String[] lineSplit = formatString(line).split("\\|\\|");
	            if (lineSplit.length < 6) {
	                System.err.println("Invalid line format: " + line);
	                continue;
	            }*/

	            String[] lineSplit = formatString(line).split("\\|\\|");
	            
	            try {
	                int uid = Integer.parseInt(formatString(lineSplit[0]));
	                String title = formatString(lineSplit[1]);
	                String desc = formatString(lineSplit[2]);
	                boolean isCompleted = formatString(lineSplit[3]).equals("true");
	                LocalDate createdDate = LocalDate.parse(formatString(lineSplit[4]), formatter);
	                LocalDate dueDate = LocalDate.parse(formatString(lineSplit[5]), formatter);

	                allTasks.add(new Task(uid, title, desc, isCompleted, createdDate, dueDate));
	            } catch (NumberFormatException | DateTimeParseException e) {
	                System.err.println("Error parsing line: " + line + " - " + e.getMessage());
	            }
	        }
	    } catch (FileNotFoundException e) {
	        System.err.println("File tasks not found: ");
	    }

	    return allTasks;
	}
	
    public static void appendTaskToFile(Task task) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String taskLine = formatTask(task);
            writer.write(taskLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateTaskInFile(Task updatedTask) {
        updatefile(updatedTask, false);
    }
	
	public static void deleteTask(Task task) {
		updatefile(task, true);
	}
	
	private static void updatefile(Task task, boolean delete) {
		File originalFile = new File(filePath);
		File tempFile = new File(filePath + ".tmp");
		
		int taskId = task.getUID();
		
		try (
			Scanner fin = new Scanner(originalFile);
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
			
			while(fin.hasNextLine()) {
				String line = fin.nextLine();
				String[] lineSplit = line.split("\\|\\|");
				
				int uid = Integer.parseInt(lineSplit[0]);
				
				if(uid == taskId) {
					if(!delete)
						writer.write(formatTask(task));
				} else
					writer.write(line + "\n");
			}
			
			originalFile.delete();
			tempFile.renameTo(originalFile);
		} catch (IOException e) {
			e.printStackTrace();
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