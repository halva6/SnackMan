package de.halva6.snackman.view.sprites;

import java.io.FileNotFoundException;

import de.halva6.snackman.controller.Controller;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class StaticSprite extends Sprite
{
	protected final Image image;
	protected final double size;

	public StaticSprite(String path, int m_x, int m_y) throws FileNotFoundException
	{
		super(path + "-" + m_x + "-" + m_y);

		this.image = new Image(getClass().getResourceAsStream(path));
		this.size = image.getHeight();

		this.p_x = m_x * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - getSize()) / 2;
		this.p_y = m_y * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - getSize()) / 2;
	}

	public StaticSprite(String path, String id, int m_x, int m_y) throws FileNotFoundException
	{
		super(id);
		this.image = new Image(getClass().getResourceAsStream(path));
		this.size = image.getHeight();

		this.p_x = m_x * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - getSize()) / 2;
		this.p_y = m_y * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - getSize()) / 2;
	}

	public void renderSprite(GraphicsContext gc)
	{
		gc.drawImage(image, p_x, p_y);
	}

	@Override
	public double getSize()
	{
		return this.size;
	}
}
