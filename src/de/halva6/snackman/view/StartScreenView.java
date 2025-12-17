package de.halva6.snackman.view;

import java.util.Arrays;

import de.halva6.snackman.controller.SceneController;
import de.halva6.snackman.model.level.LevelData;
import de.halva6.snackman.model.level.LevelLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StartScreenView
{
	@FXML
	private Button level1Button;

	@FXML
	private Button level2Button;

	@FXML
	private Button level3Button;

	@FXML
	private ImageView mainImageView;

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
			level--; // The goal is to compare the level statistics with the previous level, so that
						// the effect occurs that you have earned the level.
			int[] stats = LevelLoader.loadExternalLevelStats(level);
			levelData.setCurrentHighScore(stats[0]);
			levelData.setCurrentBestTime(stats[1]);

			System.out.println(Arrays.toString(stats));
			System.out.println("[" + levelData.getScore() + ", " + levelData.getTime() + "]");

			if (stats[0] >= levelData.getScore() && stats[1] <= levelData.getTime())
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
	public void initialize()
	{
		Image img = new Image(getClass().getResourceAsStream(SceneController.MAIN_IMAGE));
		this.mainImageView.setImage(img);

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

	}
}
