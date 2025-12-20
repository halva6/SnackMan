package de.halva6.snackman.view;

import de.halva6.snackman.controller.SceneController;
import de.halva6.snackman.model.level.LevelData;
import de.halva6.snackman.model.level.LevelLoader;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
	private Text informationText;

	@FXML
	private Button mainScreen;

	private int[][] statsValues;

	@FXML
	public void startLevel1(ActionEvent event)
	{
		startLevel(event, 1);
	}

	@FXML
	public void startLevel2(ActionEvent event)
	{
		startLevel(event, 2);
	}

	@FXML
	public void startLevel3(ActionEvent event)
	{
		startLevel(event, 3);
	}

	private void startLevel(ActionEvent event, int level)
	{
		LevelData levelData = LevelLoader.loadLevelById(level);

		if (levelData != null)
		{
			if (level > 1)
			{
				if (statsValues[level - 2][2] != 0)
				{
					SceneController.gameScene((Node) event.getSource(), levelData);
				} else
				{
					LevelData previousLevelData = LevelLoader.loadLevelById(level - 1);

					informationText.setText(
							"You must first complete the previous level in " + previousLevelData.time() + " seconds.");
					FadeTransition ft = new FadeTransition(Duration.seconds(5), informationText);
					ft.setFromValue(1); // start: invisible
					ft.setToValue(0); // end: visible
					ft.play();
				}
			} else
			{
				SceneController.gameScene((Node) event.getSource(), levelData);
			}
		}
	}

	@FXML
	public void backToMainScreen(ActionEvent event)
	{
		SceneController.startScene((Node) event.getSource(), SceneController.START_SCREEN_FXML_PATH);
	}

	@FXML
	public void initialize()
	{
		Button[] levelButtons = new Button[] { level1Button, level2Button, level3Button };
		Text[] highScoreTexts = new Text[] { hs1, hs2, hs3 };
		Text[] bestTimeTexts = new Text[] { bt1, bt2, bt3 };

		int numberOfLevels = levelButtons.length;
		statsValues = LevelLoader.loadAllExternalLevelStats(numberOfLevels);

		for (int i = 0; i < numberOfLevels; i++)
		{
			Image levelImg = new Image(
					getClass().getResourceAsStream(SceneController.LEVEL_BUTTONS + (i + 1) + ".png"));
			ImageView ivLevel = new ImageView(levelImg);
			ivLevel.setFitHeight(32);
			ivLevel.setPreserveRatio(true);

			if (i > 0)
			{
				if (statsValues[i - 1][2] == 0)
				{
					ColorAdjust gray = new ColorAdjust();
					gray.setSaturation(-1);

					ivLevel.setEffect(gray);
					ivLevel.setOpacity(0.7);
				}
			}

			levelButtons[i].setGraphic(ivLevel);

			highScoreTexts[i].setText(statsValues[i][0] + "");
			bestTimeTexts[i].setText(setDispayString(statsValues[i][1], "Time"));
		}
	}

	private String setDispayString(int value, String text)
	{
		return value == 0 ? text + ": /" : text + ": " + value + " s";
	}
}
