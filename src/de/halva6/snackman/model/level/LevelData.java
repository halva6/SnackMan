package de.halva6.snackman.model.level;

public class LevelData
{
	private final int levelId;
	private final int playerStartX;
	private final int playerStartY;

	private final int[] enemyStartX, enemyStartY;

	private final String tileFilePath;

	private final int score;
	private final int time;
	
	private int currentHighScore;
	private int currentBestTime;

	public LevelData(int levelId, int playerStartX, int playerStartY, int[] enemyStartX, int[] enemyStartY,
			String tileFilePath, int score, int time)
	{
		this.levelId = levelId;
		this.playerStartX = playerStartX;
		this.playerStartY = playerStartY;
		this.enemyStartX = enemyStartX;
		this.enemyStartY = enemyStartY;
		this.tileFilePath = tileFilePath;
		this.score = score;
		this.time = time;
	}

	public int getLevelId()
	{
		return levelId;
	}

	public int getPlayerStartX()
	{
		return playerStartX;
	}

	public int getPlayerStartY()
	{
		return playerStartY;
	}

	public int[] getEnemyStartX()
	{
		return enemyStartX;
	}

	public int[] getEnemyStartY()
	{
		return enemyStartY;
	}

	public String getTileFilePath()
	{
		return tileFilePath;
	}

	public int getScore()
	{
		return score;
	}

	public double getTime()
	{
		return time;
	}

	public int getCurrentHighScore()
	{
		return currentHighScore;
	}

	public int getCurrentBestTime()
	{
		return currentBestTime;
	}

	public void setCurrentHighScore(int currentHighScore)
	{
		this.currentHighScore = currentHighScore;
	}

	public void setCurrentBestTime(int currentBestTime)
	{
		this.currentBestTime = currentBestTime;
	}
}
