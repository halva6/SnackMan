package de.halva6.snackman.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The {@code Controller} class serves as the main entry point of the
 * application.
 * <p>
 * It initializes the primary JavaFX {@link Stage}, loads the start screen, and
 * defines global constants used throughout the game, such as screen dimensions,
 * sprite sizes, and asset paths.
 * </p>
 */
public class Controller extends Application
{
	/** Number of horizontal tiles in the game field. */
	public static final int WIDTH = 27;
	/** Number of vertical tiles in the game field. */
	public static final int HEIGHT = 27;
	/** Size of a single sprite in pixels. */
	public static final int SPRITE_SIZE = 32;
	/** Height of the score display area in pixels. */
	public static final int SCORE_HEIGHT = 30;

	/** Base path to the tile map images. */
	public static final String TILEMAP_PATH = "/img/tiles/";

	/** Base path to the Pac-Man sprite images. */
	public static final String PACMAN_PATH = "/img/pacman/";
	/** Duration of a single Pac-Man animation frame in seconds. */
	public static final double PACMAN_ANIMATION_DUARTION = 0.1;

	/** Base path to the ghost sprite images. */
	public static final String GHOST_PATH = "/img/ghost/";
	/** Duration of a single ghost animation frame in seconds. */
	public static final double GHOST_ANIMATION_DUARTION = 0.2;

	@Override
	public void start(Stage stage)
	{
		try
		{
			// Loading the start screen FXML file
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(SceneController.START_SCREEN_FXML_PATH));

			Pane root = fxmlLoader.load();
			Scene scene = new Scene(root, Controller.WIDTH * Controller.SPRITE_SIZE,
					Controller.HEIGHT * Controller.SPRITE_SIZE + Controller.SCORE_HEIGHT);

			scene.getStylesheets().add(getClass().getResource(SceneController.STYLE_PATH).toExternalForm());

			stage.setScene(scene);
			stage.setTitle("SnackMan");
			stage.show();
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Launches the JavaFX runtime and calls the {@link #start(Stage)} method.
	 */
	public static void main(String[] args)
	{
		launch(args);
	}
}
