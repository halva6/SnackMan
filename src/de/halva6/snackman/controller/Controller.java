package de.halva6.snackman.controller;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller extends Application
{

	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;
	public static final int SPRITE_SIZE = 50;
	public static final int SCORE_HEIGHT = 30;

	@Override
	public void start(Stage stage)
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader();
			FileInputStream fxmlStream = new FileInputStream(SceneController.startScreenFXMLPath);

			Pane root = fxmlLoader.load(fxmlStream);
			Scene scene = new Scene(root, Controller.WIDTH * Controller.SPRITE_SIZE,
					Controller.HEIGHT * Controller.SPRITE_SIZE + Controller.SCORE_HEIGHT);
			stage.setScene(scene);
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
