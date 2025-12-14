package de.halva6.snackman.view;

import de.halva6.snackman.controller.Controller;
import de.halva6.snackman.model.Direction;

public class AnimatedMovingSprite extends AnimatedStaticSprite
{
	
	private double x, y;
	private Direction direction;

	public AnimatedMovingSprite(String imagePaths, int m_x, int m_y, int framesNumber)
	{
		super(imagePaths, m_x, m_y, framesNumber);
		this.x = m_x * Controller.SPRITE_SIZE;
		this.y = m_y * Controller.SPRITE_SIZE;
	}

}
