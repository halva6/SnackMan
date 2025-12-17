package de.halva6.snackman.view;

import de.halva6.snackman.controller.SceneController;
import de.halva6.snackman.model.level.LevelData;
import de.halva6.snackman.model.level.LevelLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class LevelOverviewScreen
{
	@FXML
	private Button level1Button;
	@FXML
	private Text hs1;
	@FXML
	private Text bt1;

	@FXML
	private Button level2Button;
	@FXML
	private Text hs2;
	@FXML
	private Text bt2;

	@FXML
	private Button level3Button;
	@FXML
	private Text hs3;
	@FXML
	private Text bt3;

	@FXML
	private Button mainScreen;

	@FXML
	public void startLevel1(ActionEvent event)
	{
		startLeve(event, 1);
	}

	@FXML
	public void startLevel2(ActionEvent event)
	{
		startLeve(event, 2);
	}

	@FXML
	public void startLevel3(ActionEvent event)
	{
		startLeve(event, 3);
	}

	private void startLeve(ActionEvent event, int level)
	{
		LevelData levelData = LevelLoader.loadLevelById(level);

		if (levelData != null)
		{
			if (isAvailable(level, levelData))
			{
				SceneController.gameScene((Node) event.getSource(), levelData);
			}
		}
	}

	private boolean isAvailable(int level, LevelData levelData)
	{
		if (level > 1)
		{
			level--;
			// these are the stats specified in the level.xml file to check if the level has
			// already been unlocked
			int[] lastStats = LevelLoader.loadExternalLevelStats(level);

			if (lastStats[0] >= levelData.score() && lastStats[1] <= levelData.time())
			{
				return true;
			}
		} else
		{
			return true;
		}

		return false;
	}

	@FXML
	public void backToMainScreen(ActionEvent event)
	{
		SceneController.startScene((Node) event.getSource(), SceneController.START_SCREEN_FXML_PATH);
	}

	@FXML
	public void initialize()
	{
		Image level1 = new Image(getClass().getResourceAsStream(SceneController.LEVEL1));
		Image level2 = new Image(getClass().getResourceAsStream(SceneController.LEVEL2));
		Image level3 = new Image(getClass().getResourceAsStream(SceneController.LEVEL3));

		ImageView ivLevel1 = new ImageView(level1);
		ImageView ivLevel2 = new ImageView(level2);
		ImageView ivLevel3 = new ImageView(level3);

		ivLevel1.setFitHeight(32);
		ivLevel2.setFitHeight(32);
		ivLevel3.setFitHeight(32);

		ivLevel1.setPreserveRatio(true);
		ivLevel2.setPreserveRatio(true);
		ivLevel3.setPreserveRatio(true);

		this.level1Button.setGraphic(ivLevel1);
		this.level2Button.setGraphic(ivLevel2);
		this.level3Button.setGraphic(ivLevel3);

		int[][] statsValues = LevelLoader.loadAllExternalLevelStats(3);

		hs1.setText(setDispayString(statsValues[0][0], "Highscore"));
		hs2.setText(setDispayString(statsValues[1][0], "Highscore"));
		hs3.setText(setDispayString(statsValues[2][0], "Highscore"));

		bt1.setText(setDispayString(statsValues[0][1], "Time"));
		bt2.setText(setDispayString(statsValues[1][1], "Time"));
		bt3.setText(setDispayString(statsValues[2][1], "Time"));
	}

	private String setDispayString(int value, String text)
	{
		return value == 0 ? text + ": /" : text + ": " + value;
	}
}
