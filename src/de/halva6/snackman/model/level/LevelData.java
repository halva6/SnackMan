package de.halva6.snackman.model.level;

public record LevelData(int levelId, int playerStartX, int playerStartY, int[] enemyStartX, int[] enemyStartY,
		String tileFilePath, int score, int time, int currentHighScore, int currentBestTime)
{

}