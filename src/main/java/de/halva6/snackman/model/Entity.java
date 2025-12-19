package de.halva6.snackman.model;

import de.halva6.snackman.controller.Controller;
import de.halva6.snackman.view.Map;

public abstract class Entity
{
	protected int p_x, p_y;
	protected int m_x, m_y;
	protected int speed_x = 0, speed_y = 0;
	protected Direction reqDirection;
	protected Direction entityDirection;

	protected final int[][] map;

	public Entity(int m_x, int m_y, int[][] map)
	{
		this.m_x = m_x;
		this.m_y = m_y;

		this.p_x = m_x * Controller.SPRITE_SIZE;
		this.p_y = m_y * Controller.SPRITE_SIZE;

		this.map = map;
	}

	public abstract void move();

	public int getPosX()
	{
		return p_x;
	}

	public int getPosY()
	{
		return p_y;
	}

	public Direction getEntityDirection()
	{
		return entityDirection;
	}

	public void setReqDirection(Direction reqDirection)
	{
		this.reqDirection = reqDirection;
	}

	public boolean isMoving()
	{
		return !(speed_x == 0 && speed_y == 0);
	}

	protected boolean wallCollision(Direction d)
	{
		boolean c = false;
		switch (d)
		{
		case UP:
			c = map[m_y - 1][m_x] < Map.SNACK_NUMBER;
			break;
		case DOWN:
			c = map[m_y + 1][m_x] < Map.SNACK_NUMBER;
			break;
		case LEFT:
			c = map[m_y][m_x - 1] < Map.SNACK_NUMBER;
			break;
		case RIGHT:
			c = map[m_y][m_x + 1] < Map.SNACK_NUMBER;
			break;
		}

		return c;
	}

	protected void setSpeedInDirection(Direction d)
	{
		switch (d)
		{
		case UP:
			speed_x = 0;
			speed_y = -1;
			break;
		case DOWN:
			speed_x = 0;
			speed_y = 1;
			break;
		case LEFT:
			speed_x = -1;
			speed_y = 0;
			break;
		case RIGHT:
			speed_x = 1;
			speed_y = 0;
			break;
		}
	}
}
