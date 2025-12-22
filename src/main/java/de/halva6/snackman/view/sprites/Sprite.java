package de.halva6.snackman.view.sprites;

/**
 * Abstract base class for all drawable and collidable objects in the game.
 * <p>
 * A sprite has a unique ID, a pixel-based position, and a size. It provides
 * basic collision detection with other sprites.
 * </p>
 */
public abstract class Sprite
{
	protected final String id;
	protected double p_x, p_y;

	/**
	 * Creates a new sprite with the given unique ID.
	 *
	 * @param id the unique identifier of the sprite
	 */
	public Sprite(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return this.id;
	}

	/**
	 * Returns the sprite's position and size as a rectangle.
	 *
	 * @return an array containing [x-position, y-position, size]
	 */
	public double[] getRect()
	{
		return new double[] { p_x, p_y, getSize() };
	}

	/**
	 * Checks whether this sprite collides with another sprite.
	 * <p>
	 * Collision is determined by overlapping rectangles of both sprites.
	 * </p>
	 *
	 * @param sprite the other sprite to check collision with
	 * @return {@code true} if the sprites collide, {@code false} otherwise
	 */
	public boolean collideSprite(Sprite sprite)
	{
		double[] rect = sprite.getRect();
		boolean collisionX = this.p_x + getSize() >= rect[0] && rect[0] + rect[2] >= this.p_x;
		boolean collisionY = this.p_y + getSize() >= rect[1] && rect[1] + rect[2] >= this.p_y;

		return collisionX && collisionY;
	}

	/**
	 * Returns the size of the sprite.
	 * <p>
	 * Must be implemented by subclasses to define the sprite's dimensions.
	 * </p>
	 *
	 * @return the size of the sprite in pixels
	 */
	public abstract double getSize();
}
