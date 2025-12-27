package de.halva6.snackman.model;

import de.halva6.snackman.controller.Controller;
import de.halva6.snackman.view.Map;

/**
 * Abstract base class for all movable entities in the game.
 * <p>
 * An entity represents any object that can move on the map grid, such as the
 * player or enemies. It maintains both grid-based and pixel-based positions,
 * movement speed, and direction handling.
 * </p>
 */
public abstract class Entity
{
	protected int p_x, p_y;
	protected int m_x, m_y;
	protected int speed_x = 0, speed_y = 0;
	protected Direction reqDirection;
	protected Direction entityDirection;

	protected final int[][] map;

	private boolean isOverFlow = false;

	/**
	 * Creates a new entity at the given map position.
	 *
	 * @param m_x the initial x-position on the map grid
	 * @param m_y the initial y-position on the map grid
	 * @param map the map data used for collision detection
	 */
	public Entity(int m_x, int m_y, int[][] map)
	{
		this.m_x = m_x;
		this.m_y = m_y;

		this.p_x = m_x * Controller.SPRITE_SIZE;
		this.p_y = m_y * Controller.SPRITE_SIZE;

		this.map = map;
	}

	/**
	 * Updates the position and movement of the entity.
	 * <p>
	 * This method must be implemented by subclasses to define entity-specific
	 * movement behavior.
	 * </p>
	 */
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

	/**
	 * Sets the requested movement direction.
	 *
	 * @param reqDirection the desired direction of movement
	 */
	public void setReqDirection(Direction reqDirection)
	{
		this.reqDirection = reqDirection;
	}

	/**
	 * Indicates whether the entity is currently moving.
	 *
	 * @return {@code true} if the entity has a non-zero speed; {@code false}
	 *         otherwise
	 */
	public boolean isMoving()
	{
		return !(speed_x == 0 && speed_y == 0);
	}

	/**
	 * Checks whether a collision with a wall would occur when moving in the given
	 * direction.
	 *
	 * @param direction the direction to check for collision
	 * @return {@code true} if a wall collision would occur; {@code false} otherwise
	 */
	protected boolean wallCollision(Direction direction)
	{

		boolean c = false;

		if (!isOverFlow)
		{
			switch (direction)
			{
			case UP:
				c = map[m_y - 1][m_x] < Map.SNACK_NUMBER && map[m_y - 1][m_x] >= 0;
				break;
			case DOWN:
				c = map[m_y + 1][m_x] < Map.SNACK_NUMBER && map[m_y + 1][m_x] >= 0;
				break;
			case LEFT:
				c = map[m_y][m_x - 1] < Map.SNACK_NUMBER && map[m_y][m_x - 1] >= 0;
				break;
			case RIGHT:
				c = map[m_y][m_x + 1] < Map.SNACK_NUMBER && map[m_y][m_x + 1] >= 0;
				break;
			}
		}

		return c;
	}

	protected void ScreenOverFlow()
	{
		if ((m_y >= Controller.HEIGHT - 1 || m_y <= 0) && !this.isOverFlow)
		{
			setPos(p_x, (m_y * -1 + (Controller.HEIGHT - 1)) * Controller.SPRITE_SIZE);
			this.isOverFlow = true;
		} else if ((m_x >= Controller.WIDTH - 1 || m_x <= 0) && !this.isOverFlow)
		{
			setPos((m_x * -1 + (Controller.WIDTH - 1)) * Controller.SPRITE_SIZE, p_y);
			this.isOverFlow = true;
		} else
		{
			isOverFlow = false;
		}
	}

	/**
	 * Sets the movement speed values according to the given direction.
	 *
	 * @param d the direction in which the entity should move
	 */
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

	private void setPos(int p_x, int p_y)
	{
		this.p_x = p_x;
		this.p_y = p_y;
	}

	@Override
	public String toString()
	{
		return "Entity [p_x=" + p_x + ", p_y=" + p_y + ", m_x=" + m_x + ", m_y=" + m_y + ", speed_x=" + speed_x
				+ ", speed_y=" + speed_y + ", reqDirection=" + reqDirection + ", entityDirection=" + entityDirection
				+ "]";
	}
}
