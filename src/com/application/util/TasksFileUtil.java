package com.application.util;

import com.application.model.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	            if (line.isEmpty()) {
	                continue;
	            }

	            String[] lineSplit = formatString(line).split("\\|\\|");
	            if (lineSplit.length < 6) {
	                System.err.println("Invalid line format: " + line);
	                continue;
	            }

	            try {
	                int uid = Integer.parseInt(lineSplit[0].strip());
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
	
	public static void saveTask(Task task) {
        appendTaskToFile(task);
    }
    private static void appendTaskToFile(Task task) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String taskLine = formatTask(task);
            writer.write(taskLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateTask(Task updatedTask) {
        modifyFile(updatedTask.getUID(), updatedTask, true);
    }

    private static void modifyFile(int taskId, Task updatedTask, boolean isUpdate) {
        File originalFile = new File(filePath);
        File tempFile = new File(filePath + ".tmp");

        try (
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String currentLine;
            boolean taskFound = false;

            while ((currentLine = reader.readLine()) != null) {
                String line = currentLine.strip();
                if (line.isEmpty()) {
                    continue;
                }

                String[] lineSplit = formatString(line).split("\\|\\|");
                if (lineSplit.length < 6) {
                    System.err.println("Invalid line format: " + line);
                    continue;
                }

                try {
                    int uid = Integer.parseInt(lineSplit[0].strip());
                    if (uid == taskId) {
                        taskFound = true;
                        if (isUpdate && updatedTask != null) {
                            writer.write(formatTask(updatedTask));
                        }
                    } else {
                        writer.write(currentLine + System.lineSeparator());
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing task ID in line: " + line + " - " + e.getMessage());
                    continue;
                }
            }

            if (!taskFound) {
                System.out.println("Task with ID " + taskId + " not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!originalFile.delete()) {
            System.err.println("Failed to delete the original file.");
        }

        if (!tempFile.renameTo(originalFile)) {
            System.err.println("Failed to rename the temporary file.");
        }
    }
	
	public static void deleteTask(int taskId) {
		File originalFile = new File(filePath);
		File tempFile = new File(filePath + ".tmp");
		
		try (
			BufferedReader reader = new BufferedReader(new FileReader(originalFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
				
			String currentLine;
			boolean taskDeleted = false;
			
			while((currentLine = reader.readLine()) != null) {
				String[] lineSplit = currentLine.split("\\|\\|");
				int uid = Integer.parseInt(lineSplit[0].strip());
				
				if(uid == taskId) {
					taskDeleted = true;
				}else {
					writer.write(currentLine + System.lineSeparator());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!originalFile.delete()) {
			System.err.println("Failed to delete the original file.");
		}
		
		if(!tempFile.renameTo(originalFile)) {
			System.err.println("Failed to rename the temporary file.");
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