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

/**
 * The {@code SceneController} is responsible for switching and initializing
 * different JavaFX scenes of the application, such as the start screen, game
 * scene, and game over screen.
 *
 * All methods are static and operate on the current {@link Stage} derived from
 * a given source {@link Node}.
 */
public class SceneController
{
	/** Logger used for reporting scene loading errors. */
	private static final Logger logger = Logger.getLogger(SceneController.class.getName());

	/** FXML path for the start screen scene. */
	public static final String START_SCREEN_FXML_PATH = "/fxml/StartScreen.fxml";
	/** FXML path for the game over screen scene. */
	public static final String GAME_OVER_FXML_PATH = "/fxml/GameOverScreen.fxml";
	/** FXML path for the level overview scene. */
	public static final String LEVEL_OVERVIEW_FXML_PATH = "/fxml/LevelOverview.fxml";

	/** Path to the global application stylesheet. */
	public static final String STYLE_PATH = "/styles/style.css";

	/** Path to the main menu image. */
	public static final String MAIN_IMAGE = "/img/ui/play_snackman.png";
	/** Base path for level button images. */
	public static final String LEVEL_BUTTONS = "/img/ui/";
	/** Path to the lock image used for locked levels. */
	public static final String LOCK_IMAGE = "/img/ui/Lock.png";

	/**
	 * Creates and displays the main game scene.
	 *
	 * Initializes input handling, rendering canvas, and starts the game loop using
	 * the provided {@link LevelData}.
	 *
	 * @param sourceNode a node belonging to the current scene, used to retrieve the
	 *                   {@link Stage}
	 * @param levelData  the level configuration data to be loaded into the game
	 */
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

	/**
	 * Loads and displays a generic scene defined by an FXML file.
	 *
	 * This method is mainly used for non-game scenes such as the start screen or
	 * level overview.
	 *
	 * @param sourceNode a node belonging to the current scene, used to retrieve the
	 *                   {@link Stage}
	 * @param scenePath  the path to the FXML file of the scene to load
	 */
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

	/**
	 * Loads and displays the game over screen.
	 *
	 * In addition to loading the FXML scene, this method initializes the
	 * {@link GameOverScreenView} controller with game result data.
	 *
	 * @param sourceNode a node belonging to the current scene, used to retrieve the
	 *                   {@link Stage}
	 * @param scenePath  the path to the game over screen FXML file
	 * @param status     the game result status (e.g. "Victory" or "Defeat")
	 * @param points     the achieved score
	 * @param time       the time played
	 */
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