package de.halva6.snackman.view;

import de.halva6.snackman.controller.Controller;
import de.halva6.snackman.controller.SceneController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PauseView
{
	private VBox root = new VBox();

	private Text heading = new Text("Pause");
	private Button restart = new Button("restart");
	private Button backMenu = new Button("back to menu");
	private Button backGame = new Button("back to game");

	private boolean exitPause, exitGame = false;

	public PauseView()
	{
		root.setSpacing(20);
		root.setAlignment(Pos.CENTER);
		root.setPrefSize(Controller.WIDTH * Controller.SPRITE_SIZE,
				Controller.HEIGHT * Controller.SPRITE_SIZE + Controller.SCORE_HEIGHT);
		root.getStyleClass().add("semi-transparent-pane");

		root.getChildren().add(heading);
		root.getChildren().add(restart);
		root.getChildren().add(backMenu);
		root.getChildren().add(backGame);
		
		

		restart.setOnAction(event -> {
			SceneController.gameScene((Node) event.getSource());
			exitGame = true;
		});

		backMenu.setOnAction(event -> {
			SceneController.startScreenScene((Node) event.getSource(), SceneController.START_SCREEN_FXML_PATH);
			exitGame = true;
		});
		backGame.setOnAction(event -> exitPause = true);

		heading.getStyleClass().add("heading-text");
		restart.getStyleClass().add("button-style");
		backMenu.getStyleClass().add("button-style");
		backGame.getStyleClass().add("button-style");

		setVisible(false);

	}

	public VBox getPauseView()
	{
		return this.root;
	}

	public void setVisible(boolean visible)
	{
		this.root.setVisible(visible);
	}

	public boolean getExitPause()
	{
		return this.exitPause;
	}

	public boolean getExitGame()
	{
		return this.exitGame;
	}

	public void setExitPause(boolean exitPause)
	{
		this.exitPause = exitPause;
	}
}
