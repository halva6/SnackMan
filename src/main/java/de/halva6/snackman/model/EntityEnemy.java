package de.halva6.snackman.model;

import java.util.Random;

import de.halva6.snackman.controller.Controller;
import de.halva6.snackman.view.Map;

/**
 * Represents an enemy entity in the game (e.g., a ghost).
 * <p>
 * The enemy moves autonomously based on simple AI rules: it chooses random
 * directions unless it encounters a dead end, in which case it
 * deterministically selects the only open path.
 * </p>
 */
public class EntityEnemy extends Entity
{
	// random values
	private final Random random = new Random();

	/**
	 * Creates a new enemy entity at the specified map coordinates.
	 *
	 * The initial direction is randomly chosen.
	 *
	 * @param m_x the initial x-position on the map grid
	 * @param m_y the initial y-position on the map grid
	 * @param map the map data used for collision detection
	 */
	public EntityEnemy(int m_x, int m_y, int[][] map)
	{
		super(m_x, m_y, map);
		this.entityDirection = getRandomDirection();
	}

	/**
	 * Updates the enemy's position and movement each frame.
	 * <p>
	 * The enemy chooses a new direction when it reaches a grid intersection. It can
	 * detect tunnels (corridors) and handles dead ends to avoid getting stuck.
	 * </p>
	 */
	@Override
	public void move()
	{
		boolean frontWall = false;
		boolean wall = false;
		boolean tunnel = false;

		if (this.p_y % Controller.SPRITE_SIZE == 0 && this.p_x % Controller.SPRITE_SIZE == 0)
		{
			m_x = this.p_x / Controller.SPRITE_SIZE;
			m_y = this.p_y / Controller.SPRITE_SIZE;

			// check if in a tunnel
			if ((map[m_y - 1][m_x] < Map.SNACK_NUMBER && map[m_y + 1][m_x] < Map.SNACK_NUMBER)
					|| (map[m_y][m_x - 1] < Map.SNACK_NUMBER && map[m_y][m_x + 1] < Map.SNACK_NUMBER))
			{
				tunnel = true;
			}

			frontWall = wallCollision(entityDirection);

			if (tunnel && frontWall)
			{
				Direction newDirection = deadEndDirection();
				if (newDirection != null)
				{
					entityDirection = newDirection;
				}
			} else if (!tunnel)
			{
				this.reqDirection = getRandomDirection();
				wall = wallCollision(reqDirection);

				if (!wall)
				{
					entityDirection = reqDirection;
				}
			}
		}

		// sets the speed values ​​depending on the direction the entity should move
		if (!frontWall)
		{
			setSpeedInDirection(entityDirection);
		} else
		{
			speed_x = 0;
			speed_y = 0;
		}
		this.p_x += speed_x;
		this.p_y += speed_y;
	}

	/**
	 * Returns a random direction from the {@link Direction} enum.
	 *
	 * @return a randomly chosen movement direction
	 */
	private Direction getRandomDirection()
	{
		int r = random.nextInt(Direction.values().length);
		return Direction.values()[r];
	}

	/**
	 * Determines the correct movement direction if the enemy is in a dead end.
	 * <p>
	 * Checks which surrounding tiles are blocked and returns the only open path.
	 * Returns {@code null} if no dead-end situation is detected.
	 * </p>
	 *
	 * @return the new direction to move in a dead-end situation, or {@code null} if
	 *         none
	 */
	private Direction deadEndDirection()
	{
		boolean upBlocked = map[m_y - 1][m_x] < Map.SNACK_NUMBER;
		boolean downBlocked = map[m_y + 1][m_x] < Map.SNACK_NUMBER;
		boolean leftBlocked = map[m_y][m_x - 1] < Map.SNACK_NUMBER;
		boolean rightBlocked = map[m_y][m_x + 1] < Map.SNACK_NUMBER;

		if (upBlocked && downBlocked && leftBlocked && !rightBlocked)
		{
			return Direction.RIGHT;
		} else if (upBlocked && downBlocked && !leftBlocked && rightBlocked)
		{
			return Direction.LEFT;
		} else if (!upBlocked && downBlocked && leftBlocked && rightBlocked)
		{
			return Direction.UP;
		} else if (upBlocked && !downBlocked && leftBlocked && rightBlocked)
		{
			return Direction.DOWN;
		}

		// shouldn't if level is designed correctly
		return null;
	}

}
