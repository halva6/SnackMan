package de.halva6.snackman.model.level;

public record LevelData(int levelId, int playerStartX, int playerStartY, int[] enemyStartX,
		int[] enemyStartY, String tileFilePath, int score, int time, int currentHighScore, int currentBestTime)
{
	public int nextLevelAvailable(int score, int time)
	{
		// The LevelData.xml file contains the expected values ​​required to unlock the
		// next level. If the values ​​achieved in the game match the expected values
		// ​​from the LevelData file, a value of 1 is returned.
		if (score >= this.score && time <= this.time)
		{
			return 1;
		}

		return 0;
	}
}