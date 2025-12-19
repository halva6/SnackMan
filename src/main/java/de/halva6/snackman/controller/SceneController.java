package de.halva6.snackman.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.halva6.snackman.model.level.LevelData;
import de.halva6.snackman.view.GameOverScreenView;
import de.halva6.snackman.view.Input;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SceneController
{
	private static final Logger logger = Logger.getLogger(SceneController.class.getName());
	public static final String START_SCREEN_FXML_PATH = "/fxml/StartScreen.fxml";
	public static final String GAME_OVER_FXML_PATH = "/fxml/GameOverScreen.fxml";
	public static final String LEVEL_OVERVIEW_FXML_PATH = "/fxml/LevelOverview.fxml";


	public static final String STYLE_PATH = "/styles/style.css";

	public static final String MAIN_IMAGE = "/img/ui/play_snackman.png";
	public static final String LEVEL_BUTTONS = "/img/ui/";
	public static final String LOCK_IMAGE = "/img/ui/Lock.png";

	public static void gameScene(Node sourceNode, LevelData levelData)
	{
		Group root = new Group();

		Input input = new Input();

		Scene gameScene = new Scene(root, Controller.WIDTH * Controller.SPRITE_SIZE,
				Controller.HEIGHT * Controller.SPRITE_SIZE + Controller.SCORE_HEIGHT, Color.BLACK);
		gameScene.setOnKeyPressed(input.getEvent());
		gameScene.setOnKeyReleased(input.getEvent());
		gameScene.getStylesheets().add(SceneController.class.getResource(STYLE_PATH).toExternalForm());

		Canvas canvas = new Canvas(Controller.WIDTH * Controller.SPRITE_SIZE,
				Controller.HEIGHT * Controller.SPRITE_SIZE + Controller.SCORE_HEIGHT);
		root.getChildren().add(canvas);

		@SuppressWarnings("unused")
		GameLoop gameLoop = new GameLoop(root, input, canvas, levelData);

		Stage stage = (Stage) sourceNode.getScene().getWindow();
		stage.setScene(gameScene);
		stage.show();
	}

	public static void startScene(Node sourceNode, String scenePath)
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(SceneController.class.getResource(scenePath));

			Pane root = fxmlLoader.load();
			Scene screen = new Scene(root, Controller.WIDTH * Controller.SPRITE_SIZE,
					Controller.HEIGHT * Controller.SPRITE_SIZE + Controller.SCORE_HEIGHT);

			screen.getStylesheets().add(SceneController.class.getResource(STYLE_PATH).toExternalForm());

			Stage stage = (Stage) sourceNode.getScene().getWindow();
			stage.setScene(screen);
			stage.show();
		} catch (IOException e)
		{
			logger.log(Level.SEVERE, "Failed to load " + scenePath + " - scene", e);
			System.exit(0);
		}
	}


	public static void gameOverScreenScene(Node sourceNode, String scenePath, String status, String points, String time)
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(SceneController.class.getResource(scenePath));

			Pane root = fxmlLoader.load();

			GameOverScreenView gosv = fxmlLoader.getController();
			gosv.setPointsText(points);
			gosv.setStatusText(status);
			gosv.setTimeText(time);

			Scene screen = new Scene(root, Controller.WIDTH * Controller.SPRITE_SIZE,
					Controller.HEIGHT * Controller.SPRITE_SIZE + Controller.SCORE_HEIGHT);

			screen.getStylesheets().add(SceneController.class.getResource(STYLE_PATH).toExternalForm());

			Stage stage = (Stage) sourceNode.getScene().getWindow();
			stage.setScene(screen);
			stage.show();
		} catch (IOException e)
		{
			logger.log(Level.SEVERE, "Failed to load game over screen scene", e);
			System.exit(0);
		}
	}
}