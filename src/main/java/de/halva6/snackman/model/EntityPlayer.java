package de.halva6.snackman.model;

import de.halva6.snackman.controller.Controller;

/**
 * Represents the player-controlled entity (e.g., Pac-Man) in the game.
 * <p>
 * The player moves according to input directions. Movement changes are applied
 * immediately if the requested direction is opposite to the current direction,
 * otherwise the requested direction is only applied when reaching a grid
 * intersection.
 * </p>
 */
public class EntityPlayer extends Entity
{
	/**
	 * Creates a new player entity at the specified map coordinates.
	 *
	 * Sets the initial movement and requested direction.
	 *
	 * @param m_x             the initial x-position on the map grid
	 * @param m_y             the initial y-position on the map grid
	 * @param movingDirection the initial movement direction
	 * @param map             the map data used for collision detection
	 */
	public EntityPlayer(int m_x, int m_y, Direction movingDirection, int[][] map)
	{
		super(m_x, m_y, map);
		this.entityDirection = movingDirection;
		this.reqDirection = movingDirection;
	}

	/**
	 * Updates the player's position and movement each frame.
	 * <p>
	 * Handles direction changes, collision detection, and speed updates. The player
	 * can immediately reverse direction if the requested direction is opposite to
	 * the current one. Movement is constrained by walls on the map.
	 * </p>
	 */
	@Override
	public void move()
	{
		boolean wall = false;
		boolean frontWall = false;

		// change instant directions, but only if it is the opposite direction
		if (reqDirection.getAngle() == entityDirection.getAngle() + 180
				|| reqDirection.getAngle() == entityDirection.getAngle() - 180)
		{
			this.entityDirection = reqDirection;
		}

		if (this.p_y % Controller.SPRITE_SIZE == 0 && this.p_x % Controller.SPRITE_SIZE == 0)
		{
			m_x = (int) this.p_x / Controller.SPRITE_SIZE;
			m_y = (int) this.p_y / Controller.SPRITE_SIZE;

			// it checks if there is a wall in front of the player (in the direction of
			// movement)
			// if so, the speed is later set to 0
			ScreenOverFlow();
			
			try
			{
				frontWall = wallCollision(entityDirection);

				// it checks whether there is an obstacle in the desired direction;
				// if so, the desired direction does not become the entity direction
				wall = wallCollision(reqDirection);
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				System.out.println(toString());
				e.printStackTrace();
				System.exit(1);
			}

			if (!wall)
			{
				entityDirection = reqDirection;
			}
		}

		if (!frontWall)
		{
			// sets the speed values ​​depending on the direction the entity should move
			setSpeedInDirection(entityDirection);
		} else
		{
			speed_x = 0;
			speed_y = 0;
		}

		this.p_x += speed_x;
		this.p_y += speed_y;
	}
}
