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

public class StartScreenView
{
	@FXML
	private Button levelSelection;
	
	@FXML
	private Button exitGame;
	
	@FXML
	private Button randomLevel;
	
	@FXML
	private ImageView mainImageView;
	
	@FXML
	public void gotToLevelSelection(ActionEvent event) 
	{
		SceneController.startScene((Node) event.getSource(), SceneController.LEVEL_OVERVIEW_FXML_PATH);
	}

	@FXML
	public void quitGame() 
	{
		System.exit(0);
	}
	
	@FXML
	public void startRandomLevel(ActionEvent event) 
	{
		
		LevelData levelData = LevelLoader.loadRandomLevel();
		SceneController.gameScene((Node) event.getSource(), levelData);
	}
	
	@FXML
	public void initialize()
	{
		Image img = new Image(getClass().getResourceAsStream(SceneController.MAIN_IMAGE));
		this.mainImageView.setImage(img);
	}
}
