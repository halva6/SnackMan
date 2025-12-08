package de.halva6.snackman.model;

import de.halva6.snackman.controller.Controller;

public abstract class Entity
{
	protected int p_x, p_y;
	protected int speed_x = 0;
	protected int speed_y = 0;
	protected Direction reqDirection;
	protected Direction entityDirection;
	
	protected final int[][] map;
	
	public Entity(int m_x, int m_y, Direction movingDirection, int[][] map)
	{
		this.p_x = m_x * Controller.SPRITE_SIZE;
		this.p_y = m_y * Controller.SPRITE_SIZE;

		this.entityDirection = movingDirection;
		this.reqDirection = movingDirection;
		this.map = map;
	}
	
	public abstract void move();
	
	public double getPosX()
	{
		return p_x;
	}

	public double getPosY()
	{
		return p_y;
	}

	public Direction getEntitiyDirection()
	{
		return entityDirection;
	}

	public void setReqDirection(Direction reqDirection)
	{
		this.reqDirection = reqDirection;
	}
}
