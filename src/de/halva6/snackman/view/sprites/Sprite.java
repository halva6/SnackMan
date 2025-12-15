package de.halva6.snackman.view.sprites;

import javafx.geometry.Rectangle2D;

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

	public Rectangle2D getRect()
	{
		return new Rectangle2D(p_x, p_y, getSize(), getSize());
	}

	public boolean collideSprite(Sprite sprite)
	{
		return this.getRect().intersects(sprite.getRect());
	}

	public abstract double getSize();
}
