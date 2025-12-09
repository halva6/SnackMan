package de.halva6.snackman.controller;

import java.io.FileInputStream;
import java.io.IOException;

import de.halva6.snackman.view.GameOverScreenView;
import de.halva6.snackman.view.Input;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SceneController
{
	public static final String startScreenFXMLPath = "res/StartScreen.fxml";
	public static final String gameOverFXMLPath = "res/GameOverScreen.fxml";

	public static void gameScene(Node sourceNode)
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

		Stage stage = (Stage) sourceNode.getScene().getWindow();
		stage.setScene(gameScene);
		stage.show();
	}

	public static void startScreenScene(Node sourceNode, String scenePath)
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader();
			FileInputStream fxmlStream = new FileInputStream(scenePath);

			Pane root = fxmlLoader.load(fxmlStream);
			Scene sreen = new Scene(root, Controller.WIDTH * Controller.SPRITE_SIZE,
					Controller.HEIGHT * Controller.SPRITE_SIZE + Controller.SCORE_HEIGHT);

			Stage stage = (Stage) sourceNode.getScene().getWindow();
			stage.setScene(sreen);
			stage.show();
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void gameOverScreenScene(Node sourceNode, String scenePath, String status, String points)
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader();
			FileInputStream fxmlStream = new FileInputStream(scenePath);

			GridPane root = fxmlLoader.load(fxmlStream);
			
			GameOverScreenView gosv = fxmlLoader.getController();
			gosv.setPointsText(points);
			gosv.setStatusText(status);
			
			Scene sreen = new Scene(root, Controller.WIDTH * Controller.SPRITE_SIZE,
					Controller.HEIGHT * Controller.SPRITE_SIZE + Controller.SCORE_HEIGHT);

			Stage stage = (Stage) sourceNode.getScene().getWindow();
			stage.setScene(sreen);
			stage.show();
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
}
