package application;

import java.util.Optional;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class Modal {
	
	public static boolean confirmation(String title, String text)
	{

		Alert alertQuitter = new Alert(Alert.AlertType.CONFIRMATION);
		alertQuitter.setTitle(title);
		alertQuitter.setHeaderText(text);


		alertQuitter.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
		Optional<ButtonType> resp = alertQuitter.showAndWait();
		
		if(!resp.isPresent())
			return false;
		else if(resp.get() == ButtonType.OK)
			return true;
		else if(resp.get() == ButtonType.CANCEL)
			return false;
		
		return false;
	}
	
	
	public static Optional<Pair<String, String>> login()
	{
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Login");
		dialog.setHeaderText("Saisissez vos identifiants de connexion");


		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Username");

		PasswordField password = new PasswordField();
		password.setPromptText("Password");

		
		grid.add(new Label("Username :"), 0, 0);
		grid.add(username, 1, 0);
		
		grid.add(new Label("Password :"), 0, 1);
		grid.add(password, 1, 1);

		
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		username.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		Platform.runLater(() -> username.requestFocus());

		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Pair<>(username.getText(), password.getText());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();
		return result;
	}
	
}
