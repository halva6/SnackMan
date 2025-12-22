package de.halva6.snackman.model.level;

/**
 * Represents the configuration and state data of a game level.
 * <p>
 * This record stores information about the level ID, starting positions of the
 * player and enemies, the tile map file path, level goals (score and time), and
 * the current high score and best time.
 * </p>
 *
 * @param levelId          the unique identifier of the level
 * @param playerStartX     the starting x-position of the player on the map grid
 * @param playerStartY     the starting y-position of the player on the map grid
 * @param enemyStartX      an array of starting x-positions for all enemies
 * @param enemyStartY      an array of starting y-positions for all enemies
 * @param tileFilePath     the file path to the level's tile map (csv file)
 * @param score            the target score required to complete the level
 * @param time             the target time limit for the level
 * @param currentHighScore the highest score achieved on this level
 * @param currentBestTime  the best time achieved on this level
 */
public record LevelData(int levelId, int playerStartX, int playerStartY, int[] enemyStartX, int[] enemyStartY,
		String tileFilePath, int score, int time, int currentHighScore, int currentBestTime)
{
	/**
	 * Determines whether the next level is unlocked based on the current score and
	 * time achieved by the player.
	 * <p>
	 * Compares the achieved score and time with the target values stored in this
	 * LevelData. Returns 1 if the next level is available (score &gt;= target score
	 * and time &lt;= target time), otherwise returns 0.
	 * </p>
	 *
	 * @param score the score achieved in the level
	 * @param time  the time taken to complete the level
	 * @return 1 if the next level is unlocked, 0 otherwise
	 */
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