package de.halva6.snackman.model;

import de.halva6.snackman.controller.Controller;

public class EntityPlayer extends Entity
{
	
	public EntityPlayer(int m_x, int m_y, Direction movingDirection, int[][] map)
	{
		super(m_x, m_y, map);
		this.entityDirection = movingDirection;
		this.reqDirection = movingDirection;
	}

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

			//it checks if there is a wall in front of the player (in the direction of movement) 
			//if so, the speed is later set to 0
			frontWall = collision(entityDirection);

			//it checks whether there is an obstacle in the desired direction; 
			//if so, the desired direction does not become the entity direction
			wall = collision(reqDirection);

			if (!wall)
			{
				entityDirection = reqDirection;
			}
		}

		if (!frontWall)
		{
			//sets the speed values ​​depending on the direction the entity should move
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
