package de.halva6.snackman.view;

import java.io.FileNotFoundException;

import de.halva6.snackman.controller.Controller;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class StaticSprite implements Sprite<StaticSprite>
{
	private final String id;

	protected final Image image;
	protected double p_x, p_y;

	public StaticSprite(String path, int m_x, int m_y) throws FileNotFoundException
	{
		this.image = new Image(getClass().getResourceAsStream(path));
		this.id = path + "-" + m_x + "-" + m_y;

		this.p_x = m_x * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - getSize()) / 2;
		this.p_y = m_y * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - getSize()) / 2;
	}

	public StaticSprite(String path, String id, int m_x, int m_y) throws FileNotFoundException
	{
		this.image = new Image(getClass().getResourceAsStream(path));
		this.id = id;

		this.p_x = m_x * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - getSize()) / 2;
		this.p_y = m_y * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - getSize()) / 2;
	}

	@Override
	public String getId()
	{
		return this.id;
	}

	@Override
	public void renderSprite(GraphicsContext gc, double deltaTime)
	{
		gc.drawImage(image, p_x, p_y);
	}

	@Override
	public double getSize()
	{
		return this.image.getHeight();
	}

	@Override
	public Rectangle2D getRect()
	{
		return new Rectangle2D(p_x, p_y, image.getWidth(), image.getHeight());
	}

	@Override
	public boolean collideSprite(StaticSprite otherSprite)
	{
		return this.getRect().intersects(otherSprite.getRect());
	}

}
