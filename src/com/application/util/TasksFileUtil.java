package com.application.util;

import com.application.model.Task;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TasksFileUtil {
	
	private static Path readFilePath = Paths.get("data/tasks");
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	public static ArrayList<Task> readAllTasks() {
	    ArrayList<Task> allTasks = new ArrayList<>();

	    try {
	    	List<String> allLines = Files.readAllLines(readFilePath);
	    	
	    	int taskSize = allLines.size();
	    	
    		for(int i = 1; i < taskSize; i++) {
    			String line = allLines.get(i);
    			String[] lineSplit = formatString(line).split("\\|\\|");
	            
                int uid = Integer.parseInt(formatString(lineSplit[0]));
                String title = formatString(lineSplit[1]);
                String desc = formatString(lineSplit[2]);
                boolean isCompleted = formatString(lineSplit[3]).equals("true");
                String priority = formatString(lineSplit[4]);
                LocalDate createdDate = LocalDate.parse(formatString(lineSplit[5]), formatter);
                LocalDate dueDate = LocalDate.parse(formatString(lineSplit[6]), formatter);

                allTasks.add(new Task(uid, title, desc, isCompleted, priority, createdDate, dueDate));
    		}
	    	
	    	return allTasks;
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	    	return allTasks;
	    }
	}
	
    public static boolean appendTaskToFile(Task task) {
    	try {
    		List<String> allLines = Files.readAllLines(readFilePath);
    		
    		String[] idxArr = allLines.get(0).split(":");
        	
        	int currId = Integer.parseInt(idxArr[1]);
        	idxArr[1] = String.valueOf(currId + 1);
        	
        	allLines.set(0, idxArr[0] + ":" + idxArr[1]);
        	
        	task.setUID(currId);
        	allLines.add(formatTask(task));
        	
        	Files.write(readFilePath, allLines);
    		
    		return true;
    	} catch (IOException ex) {
    		ex.printStackTrace();
    		return false;
    	}
    }

    public static boolean updateTaskInFile(Task updatedTask) {
        try {
    		List<String> allLines = Files.readAllLines(readFilePath);
    		int size = allLines.size();
    		
    		for(int i = 1; i < size; i++) {
    			String line = allLines.get(i);
    			int id = Integer.parseInt(formatString(line).split("\\|\\|")[0]);
    			
    			if(id == updatedTask.getUID()) {
    				allLines.set(i, formatTask(updatedTask));
    				break;
    			}
    		}
    		
    		Files.write(readFilePath, allLines);
    		return true;
        } catch (IOException ex) {
    		ex.printStackTrace();
    		return false;
    	}
    }
	
	public static boolean deleteTask(int taskID) {
        try {
    		List<String> allLines = Files.readAllLines(readFilePath);
    		int size = allLines.size();
    		
    		for(int i = 1; i < size; i++) {
    			String line = allLines.get(i);
    			int id = Integer.parseInt(formatString(line).split("\\|\\|")[0]);
    			
    			if(id == taskID) {
    				allLines.remove(i);
    				break;
    			}
    		}
		
			Files.write(readFilePath, allLines);
			return true;
    	} catch (IOException ex) {
			ex.printStackTrace();
			return false;
    	}
	}
	
    private static String formatTask(Task task) {
        return task.getUID() + "||" +
        	   task.getTitle() + "||" +
               task.getDesc() + "||" +
               task.isCompleted() + "||" +
               task.getPriority() + "||" +
               task.getCreatedDate().format(formatter) + "||" +
               task.getDueDate().format(formatter);
    }
	
	private static String formatString(String s) {
		return s.strip();
	}
}