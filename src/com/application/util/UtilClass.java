package com.application.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class UtilClass {
	public static Optional<ButtonType> showAlert(AlertType type, String title, String headerText, String contextText) {
		Alert alert = new Alert(type);
		alert.setTitle(contextText);
		alert.setHeaderText(headerText);
		
		if(!contextText.equals(""))
			alert.setContentText(contextText);
		
		return alert.showAndWait();
	}
}
