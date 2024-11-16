package com.application.util;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class tasksFileUtil {
	
	public static void readAllTasks() {
		try {
			FileReader fr = new FileReader("/data/tasks1.json");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}