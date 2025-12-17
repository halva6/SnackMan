package de.halva6.snackman.view;

import de.halva6.snackman.controller.SceneController;
import de.halva6.snackman.model.level.LevelData;
import de.halva6.snackman.model.level.LevelLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class GameOverScreenView
{
	@FXML
	private Button restart;

	@FXML
	private Button backToMenu;

	@FXML
	private Text statusText;

	@FXML
	private Text pointsText;

	@FXML
	private Text timeText;

	@FXML
	public void initialize()
	{

	}

	@FXML
	public void restartGame(ActionEvent event)
	{
		LevelData levelData = LevelLoader.loadLevelById(LevelLoader.levelNumber);
		if (levelData != null)
		{
			SceneController.gameScene((Node) event.getSource(), levelData);
		}
	}

	@FXML
	public void goBackToMenu(ActionEvent event)
	{
		SceneController.startScreenScene((Node) event.getSource(), SceneController.START_SCREEN_FXML_PATH);
	}

	public void setStatusText(String text)
	{
		this.statusText.setText(text);
	}

	public void setPointsText(String text)
	{
		this.pointsText.setText(text);
	}

	public void setTimeText(String text)
	{
		this.timeText.setText(text);
	}

}
