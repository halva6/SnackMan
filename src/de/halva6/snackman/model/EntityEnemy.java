package de.halva6.snackman.model;

import java.util.Random;

import de.halva6.snackman.controller.Controller;
import de.halva6.snackman.view.Map;

public class EntityEnemy extends Entity
{
	// random values
	private final Random random = new Random();

	public EntityEnemy(int m_x, int m_y, int[][] map)
	{
		super(m_x, m_y, map);
		this.entityDirection = getRandomDirection();
	}

	@Override
	public void move()
	{
		boolean frontWall = false;
		boolean wall = false;
		boolean tunnel = false;

		this.reqDirection = getRandomDirection();

		if (this.p_y % Controller.SPRITE_SIZE == 0 && this.p_x % Controller.SPRITE_SIZE == 0)
		{
			m_x = (int) this.p_x / Controller.SPRITE_SIZE;
			m_y = (int) this.p_y / Controller.SPRITE_SIZE;
			if ((map[m_y - 1][m_x] < Map.SNACK_NUMBER && map[m_y + 1][m_x] < Map.SNACK_NUMBER)
					|| (map[m_y][m_x - 1] < Map.SNACK_NUMBER && map[m_y][m_x + 1] < Map.SNACK_NUMBER))
			{
				tunnel = true;
			}

			frontWall = wallCollision(entityDirection);

			wall = wallCollision(reqDirection);

			if (tunnel && frontWall)
			{
				entityDirection = deadEndDirection();
			}

			if (!wall && !tunnel)
			{
				entityDirection = reqDirection;
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

	private Direction getRandomDirection()
	{
		int r = random.nextInt(Direction.values().length);
		return Direction.values()[r];
	}

	private Direction deadEndDirection()
	{
		if (map[m_y - 1][m_x] < Map.SNACK_NUMBER && map[m_y + 1][m_x] < Map.SNACK_NUMBER
				&& map[m_y][m_x - 1] < Map.SNACK_NUMBER)
		{
			return Direction.RIGHT;
		} else if (map[m_y - 1][m_x] < Map.SNACK_NUMBER && map[m_y + 1][m_x] < Map.SNACK_NUMBER
				&& map[m_y][m_x + 1] < Map.SNACK_NUMBER)
		{
			return Direction.LEFT;
		} else if (map[m_y + 1][m_x] < Map.SNACK_NUMBER && map[m_y][m_x - 1] < Map.SNACK_NUMBER
				&& map[m_y][m_x + 1] < Map.SNACK_NUMBER)
		{
			return Direction.UP;
		} else if (map[m_y - 1][m_x] < Map.SNACK_NUMBER && map[m_y][m_x - 1] < Map.SNACK_NUMBER
				&& map[m_y][m_x + 1] < Map.SNACK_NUMBER)
		{
			return Direction.DOWN;
		}

		return null;
	}

}
