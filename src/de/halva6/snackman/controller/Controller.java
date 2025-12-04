package de.halva6.snackman.controller;

import de.halva6.snackman.view.Input;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Controller extends Application
{
	
	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;
	public static final int SPRITE_SIZE = 50;

	@Override
	public void start(Stage stage)
	{
		Input input = new Input();
		
		Group root = new Group();
		Scene gameScene = new Scene(root, WIDTH * SPRITE_SIZE, HEIGHT * SPRITE_SIZE, Color.BLACK);
		gameScene.setOnKeyPressed(input.getEvent());
		gameScene.setOnKeyReleased(input.getEvent());

		stage.setScene(gameScene);
		
		Canvas canvas = new Canvas(WIDTH * SPRITE_SIZE, HEIGHT * SPRITE_SIZE);
		root.getChildren().add(canvas);
		
		@SuppressWarnings("unused")
		GameLoop gameLoop = new GameLoop(input, canvas);
		
		
		stage.setTitle("SnackMan");
		stage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
