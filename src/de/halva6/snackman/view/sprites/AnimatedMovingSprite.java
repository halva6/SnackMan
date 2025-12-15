package de.halva6.snackman.view.sprites;

import de.halva6.snackman.controller.Controller;
import de.halva6.snackman.model.Direction;
import javafx.scene.canvas.GraphicsContext;

public class AnimatedMovingSprite extends AnimatedStaticSprite
{
	private Direction direction;
	private boolean isMoving;

	public AnimatedMovingSprite(String imagePaths, int m_x, int m_y, int framesNumber)
	{
		super(imagePaths, m_x, m_y, framesNumber);
		this.p_x = m_x * Controller.SPRITE_SIZE;
		this.p_y = m_y * Controller.SPRITE_SIZE;
	}

	public void moveSprite(int x, int y, Direction direction, boolean isMoving)
	{
		this.p_x = x;
		this.p_y = y;
		this.direction = direction;
		this.isMoving = isMoving;

	}

	@Override
	public void renderSprite(GraphicsContext gc, double deltaTime, double animationTime)
	{
		double cx = p_x + size / 2;
		double cy = p_y + size / 2;

		gc.save();

		gc.translate(cx, cy);
		gc.rotate(direction.getAngle());
		gc.translate(-cx, -cy);

		if (isMoving)
		{
			super.renderSprite(gc, deltaTime, animationTime);
		} else
		{
			gc.drawImage(frames[getCurrentIndex()], p_x, p_y);
		}

		gc.restore();
	}

	@Override
	public String toString()
	{
		return "AnimatedMovingSprite [direction=" + direction + ", isMoving=" + isMoving + ", p_x=" + p_x + ", p_y="
				+ p_y + ", getId()=" + getId() + "]";
	}
}
