package de.halva6.snackman.view.sprites;

import de.halva6.snackman.controller.Controller;
import de.halva6.snackman.model.Direction;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents an frame-based animated sprite that can move.
 * 
 * <p>
 * Inherits from {@link AnimatedStaticSprite} and adds movement and rotation
 * functionality.
 * </p>
 */
public class AnimatedMovingSprite extends AnimatedStaticSprite
{
	private Direction direction;
	private boolean isMoving;

	/**
	 * Creates a movable animated sprite at a specific position on the matrix
	 * 
	 * @param imagePaths   path to the directory containing the images that should
	 *                     appear in the animation
	 * @param m_x          start position on the x axis of the matrix
	 * @param m_y          start position on the y axis of the matrix
	 * @param framesNumber number of frames that the animation has or should have
	 */
	public AnimatedMovingSprite(String imagePaths, int m_x, int m_y, int framesNumber)
	{
		super(imagePaths, m_x, m_y, framesNumber);
		this.p_x = m_x * Controller.SPRITE_SIZE;
		this.p_y = m_y * Controller.SPRITE_SIZE;
	}

	/**
	 * Moves the sprite to a new pixel position and updates its direction.
	 *
	 * @param x         the new x-coordinate in pixels
	 * @param y         the new y-coordinate in pixels
	 * @param direction the new direction of the sprite
	 * @param isMoving  indicates whether the sprite is still moving
	 */
	public void moveSprite(int x, int y, Direction direction, boolean isMoving)
	{
		this.p_x = x;
		this.p_y = y;
		this.direction = direction;
		this.isMoving = isMoving;
	}

	/**
	 * Renders the sprite with the current frame of the animation.
	 * <p>
	 * Depending on whether the sprite is currently moving, the current frame will
	 * be changed after a certain time or not.
	 * </p>
	 */
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
