package de.halva6.snackman.view;

import de.halva6.snackman.controller.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class StartScreenView
{
	@FXML
	private Button startPlayButton;

	@FXML
	public void startGame(ActionEvent event)
	{
		SceneController.gameScene((Node) event.getSource());
	}
}
