package de.halva6.snackman.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import de.halva6.snackman.controller.Controller;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite
{
	private Image image;
	private String id;
	private double x, y;

	public Sprite(String path) throws FileNotFoundException
	{
		this.image = new Image(new FileInputStream(path));
		this.id = path + "-" + System.currentTimeMillis();
	}

	public void renderSprite(GraphicsContext gc)
	{
		gc.drawImage(image, this.x, this.y);
	}
	
	public void moveSprite(GraphicsContext gc, double[] pos)
	{
		this.x = pos[0];
		this.y = pos[1];
		gc.drawImage(image, this.x, this.y);
	}


	public boolean collideSprite(Sprite otherSprite)
	{
		return this.x < otherSprite.getX() + Controller.SPRITE_SIZE
				&& this.x + Controller.SPRITE_SIZE > otherSprite.getX()
				&& this.y < otherSprite.getY() + Controller.SPRITE_SIZE
				&& this.y + Controller.SPRITE_SIZE > otherSprite.getY();
	}

	public Rectangle2D getBounds()
	{

		return new Rectangle2D(x, y, image.getWidth(), image.getHeight());
	}

	public boolean collideShape(Sprite otherSprite)
	{
		return this.getBounds().intersects(otherSprite.getBounds());
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getSize()
	{
		return this.image.getHeight();
	}

	public String getId()
	{
		return id;
	}
	
	public void setPos(double[] pos) 
	{
		this.x = pos[0];
		this.y = pos[1];		
	}

}
