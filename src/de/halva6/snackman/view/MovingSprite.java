package de.halva6.snackman.view;

import java.io.FileNotFoundException;

import de.halva6.snackman.controller.Controller;
import de.halva6.snackman.model.Direction;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class MovingSprite extends StaticSprite
{
	private double x, y;
	private Direction direction;

	public MovingSprite(String path, int m_x, int m_y) throws FileNotFoundException
	{
		super(path, m_x, m_y);
		this.x = m_x * Controller.SPRITE_SIZE;
		this.y = m_y * Controller.SPRITE_SIZE;
	}

	public void moveSprite(GraphicsContext gc, double x, double y, Direction direction, boolean isMoving)
	{
		if(isMoving) 
		{
		}
		
		this.x = x;
		this.y = y;
		this.direction = direction;
		
		renderMovingSprite(gc);
	}

	@Override
	public void renderSprite(GraphicsContext gc, double deltaTime)
	{
		renderMovingSprite(gc);
	}

	@Override
	public Rectangle2D getRect()
	{
		return new Rectangle2D(x, y, image.getWidth(), image.getHeight());
	}
	
	private void renderMovingSprite(GraphicsContext gc) 
	{
		double cx = x + image.getWidth() / 2;
		double cy = y + image.getHeight() / 2;

		gc.save();

		gc.translate(cx, cy);
		gc.rotate(direction.getAngle());
		gc.translate(-cx, -cy);

		gc.drawImage(image, x, y);

		gc.restore();
	}

}
