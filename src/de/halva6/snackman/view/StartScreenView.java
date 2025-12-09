package de.halva6.snackman.view;

import de.halva6.snackman.controller.Controller;
import de.halva6.snackman.controller.GameLoop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StartScreenView
{
	@FXML
	private Button startPlayButton;

	@FXML
	public void startGame(ActionEvent event)
	{
		System.out.println("Start");

		Scene newScene = initGameScene();

		// get current stage
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(newScene);
		stage.show();
	}

	private Scene initGameScene()
	{
		Group root = new Group();

		Input input = new Input();

		Scene gameScene = new Scene(root, Controller.WIDTH * Controller.SPRITE_SIZE,
				Controller.HEIGHT * Controller.SPRITE_SIZE + Controller.SCORE_HEIGHT, Color.BLACK);
		gameScene.setOnKeyPressed(input.getEvent());
		gameScene.setOnKeyReleased(input.getEvent());

		Canvas canvas = new Canvas(Controller.WIDTH * Controller.SPRITE_SIZE,
				Controller.HEIGHT * Controller.SPRITE_SIZE + Controller.SCORE_HEIGHT);
		root.getChildren().add(canvas);

		@SuppressWarnings("unused")
		GameLoop gameLoop = new GameLoop(input, canvas);

		return gameScene;
	}
}
