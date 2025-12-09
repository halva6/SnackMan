package de.halva6.snackman.model;

import java.util.Random;

import de.halva6.snackman.controller.Controller;

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
			if ((map[m_y - 1][m_x] == 0 && map[m_y + 1][m_x] == 0)
					|| (map[m_y][m_x - 1] == 0 && map[m_y][m_x + 1] == 0))
			{
				tunnel = true;
			}

			frontWall = collision(entityDirection);

			wall = collision(reqDirection);

			if (!wall && !tunnel)
			{
				entityDirection = reqDirection;
			}

			if (tunnel && frontWall)
			{
				entityDirection = getOppsiteDirection(entityDirection);
			}

		}

		// sets the speed values ​​depending on the direction the entity should move
		if (!frontWall)
		{
			setSpeedInDirection(entityDirection);
		}else 
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

	private Direction getOppsiteDirection(Direction d)
	{
		switch (d)
		{
		case Direction.UP:
			entityDirection = Direction.DOWN;
			break;
		case Direction.DOWN:
			entityDirection = Direction.UP;
			break;
		case Direction.LEFT:
			entityDirection = Direction.RIGHT;
			break;
		case Direction.RIGHT:
			entityDirection = Direction.LEFT;
			break;
		}
		return d;
	}

	// The idea is that the opponent simply picks a randomly available coordinate
	// and then tries to move there as quickly as possible.

}
