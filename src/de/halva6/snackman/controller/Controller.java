package de.halva6.snackman.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller extends Application
{

	public static final int WIDTH = 25;
	public static final int HEIGHT = 25;
	public static final int SPRITE_SIZE = 32;
	public static final int SCORE_HEIGHT = 30;

	@Override
	public void start(Stage stage)
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(SceneController.START_SCREEN_FXML_PATH));

			Pane root = fxmlLoader.load();
			Scene scene = new Scene(root, Controller.WIDTH * Controller.SPRITE_SIZE,
					Controller.HEIGHT * Controller.SPRITE_SIZE + Controller.SCORE_HEIGHT);
			
			scene.getStylesheets().add(
			        getClass().getResource(SceneController.STYLE_PATH).toExternalForm());

			
			stage.setScene(scene);
			stage.setTitle("SnackMan");
			stage.show();
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}

	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
