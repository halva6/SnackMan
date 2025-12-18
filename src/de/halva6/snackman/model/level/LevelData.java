package de.halva6.snackman.model.level;

public record LevelData(int levelId, int playerStartX, int playerStartY, int[] enemyStartX, int[] enemyStartY,
		String tileFilePath, int score, int time, int currentHighScore, int currentBestTime)
{
	public int nextLevelAvailable()
	{
		// these are the stats specified in the level.xml file to check if the level has
		// already been unlocked
		int[] lastStats = LevelLoader.loadExternalLevelStats(levelId + 1);

		if (lastStats[0] >= score && lastStats[1] <= time)
		{
			return 1;
		}

		return 0;
	}
}