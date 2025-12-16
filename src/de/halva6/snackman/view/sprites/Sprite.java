package de.halva6.snackman.view.sprites;

public abstract class Sprite
{
	protected final String id;
	protected double p_x, p_y;

	public Sprite(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return this.id;
	}

	public double[] getRect()
	{
		return new double[] { p_x, p_y, getSize() };
	}

	public boolean collideSprite(Sprite sprite)
	{
		double[] rect = sprite.getRect();
		boolean collisionX = this.p_x + getSize() >= rect[0] && rect[0] + rect[2] >= this.p_x;
		boolean collisionY = this.p_y + getSize() >= rect[1] && rect[1] + rect[2] >= this.p_y;

		return collisionX && collisionY;
	}

	public abstract double getSize();
}
