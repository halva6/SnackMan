package de.halva6.snackman.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import de.halva6.snackman.controller.Controller;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite
{
	protected Image image;
	private String id;
	protected final int m_x, m_y;
	protected final double xPos, yPos;

	public Sprite(String path, int m_x, int m_y) throws FileNotFoundException
	{
		this.image = new Image(new FileInputStream(path));
		this.id = path + "-" + m_x + "-" + m_y;

		this.m_x = m_x;
		this.m_y = m_y;

//      double diffSize = (Controller.SPRITE_SIZE - getSize()) / 2;
		this.xPos = m_x * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - getSize()) / 2;
		this.yPos = m_y * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - getSize()) / 2;
	}
	
	public Sprite(String path,  String id, int m_x, int m_y) throws FileNotFoundException
	{
		this.image = new Image(new FileInputStream(path));
		this.id = id;
		this.m_x = m_x;
		this.m_y = m_y;

//      double diffSize = (Controller.SPRITE_SIZE - getSize()) / 2;
		this.xPos = m_x * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - getSize()) / 2;
		this.yPos = m_y * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - getSize()) / 2;
	}


	public void renderSprite(GraphicsContext gc)
	{

		gc.drawImage(image, xPos, yPos);
	}

	public double getSize()
	{
		return this.image.getHeight();
	}

	public String getId()
	{
		return id;
	}

	public int getM_x()
	{
		return m_x;
	}

	public int getM_y()
	{
		return m_y;
	}

	public Rectangle2D getRect()
	{
		return new Rectangle2D(xPos, yPos, image.getWidth(), image.getHeight());
	}

	public boolean collideSprite(Sprite otherSprite)
	{
		return this.getRect().intersects(otherSprite.getRect());
	}

}
