package de.halva6.snackman.view.sprites;

import java.io.FileNotFoundException;

import de.halva6.snackman.controller.Controller;
import de.halva6.snackman.model.Direction;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents a sprite that can move and rotate based on its direction. Inherits
 * from {@link StaticSprite} and adds movement and rotation functionality.
 */
public class MovingSprite extends StaticSprite
{
	private Direction direction;

	/**
	 * Creates a new moving sprite at the specified map grid position.
	 *
	 * @param path the path to the image resource
	 * @param m_x  the x-position on the map grid
	 * @param m_y  the y-position on the map grid
	 * @throws FileNotFoundException if the image file cannot be found
	 */
	public MovingSprite(String path, int m_x, int m_y) throws FileNotFoundException
	{
		super(path, m_x, m_y);
		this.p_x = m_x * Controller.SPRITE_SIZE;
		this.p_y = m_y * Controller.SPRITE_SIZE;
	}

	/**
	 * Moves the sprite to a new pixel position and updates its direction.
	 *
	 * @param x         the new x-coordinate in pixels
	 * @param y         the new y-coordinate in pixels
	 * @param direction the new direction of the sprite
	 */
	public void moveSprite(int x, int y, Direction direction)
	{
		this.p_x = x;
		this.p_y = y;
		this.direction = direction;
	}

	/**
	 * Renders the sprite on the given graphics context.
	 * <p>
	 * The sprite is rotated according to its current direction.
	 * </p>
	 *
	 * @param gc the {@link GraphicsContext} used to draw the sprite
	 */
	@Override
	public void renderSprite(GraphicsContext gc)
	{
		double cx = p_x + image.getWidth() / 2;
		double cy = p_y + image.getHeight() / 2;

		gc.save();

		gc.translate(cx, cy);
		gc.rotate(direction.getAngle());
		gc.translate(-cx, -cy);

		super.renderSprite(gc);

		gc.restore();
	}

}
