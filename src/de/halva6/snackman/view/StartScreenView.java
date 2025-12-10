package de.halva6.snackman.view;

import de.halva6.snackman.controller.SceneController;
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
		SceneController.LEVEL_NUMBER = 1;
		SceneController.gameScene((Node) event.getSource());
	}

	@FXML
	public void startLevel2(ActionEvent event)
	{
		SceneController.LEVEL_NUMBER = 1;
		SceneController.gameScene((Node) event.getSource());
	}

	@FXML
	public void startLevel3(ActionEvent event)
	{
		SceneController.LEVEL_NUMBER = 1;
		SceneController.gameScene((Node) event.getSource());
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
