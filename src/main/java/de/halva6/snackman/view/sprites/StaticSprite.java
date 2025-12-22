package de.halva6.snackman.view.sprites;

import java.io.FileNotFoundException;

import de.halva6.snackman.controller.Controller;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents a static sprite in the game with a fixed image and position.
 * <p>
 * Static sprites do not move and are rendered at their specified location.
 * </p>
 */
public class StaticSprite extends Sprite
{
	protected final Image image;
	protected final double size;

	/**
	 * Creates a new static sprite with an auto-generated ID based on path and grid
	 * coordinates.
	 *
	 * @param path the path to the image resource
	 * @param m_x  the x-position on the map grid
	 * @param m_y  the y-position on the map grid
	 * @throws FileNotFoundException if the image file cannot be found
	 */
	public StaticSprite(String path, int m_x, int m_y) throws FileNotFoundException
	{
		super(path + "-" + m_x + "-" + m_y);

		this.image = new Image(getClass().getResourceAsStream(path));
		this.size = image.getHeight();

		this.p_x = m_x * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - getSize()) / 2;
		this.p_y = m_y * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - getSize()) / 2;
	}

	/**
	 * Creates a new static sprite with a custom ID.
	 *
	 * @param path the path to the image resource
	 * @param id   the custom ID for the sprite
	 * @param m_x  the x-position on the map grid
	 * @param m_y  the y-position on the map grid
	 * @throws FileNotFoundException if the image file cannot be found
	 */
	public StaticSprite(String path, String id, int m_x, int m_y) throws FileNotFoundException
	{
		super(id);
		this.image = new Image(getClass().getResourceAsStream(path));
		this.size = image.getHeight();

		this.p_x = m_x * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - getSize()) / 2;
		this.p_y = m_y * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - getSize()) / 2;
	}

	/**
	 * Renders the sprite on the given graphics context.
	 *
	 * @param gc the GraphicsContext used to draw the sprite
	 */
	public void renderSprite(GraphicsContext gc)
	{
		gc.drawImage(image, p_x, p_y);
	}

	/**
	 * Returns the size of the sprite.
	 *
	 * @return the size in pixels
	 */
	@Override
	public double getSize()
	{
		return this.size;
	}
}
