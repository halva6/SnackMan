package de.halva6.snackman.view.sprites;

import java.io.FileNotFoundException;

import de.halva6.snackman.controller.Controller;
import de.halva6.snackman.model.Direction;
import javafx.scene.canvas.GraphicsContext;

public class MovingSprite extends StaticSprite
{
	private Direction direction;

	public MovingSprite(String path, int m_x, int m_y) throws FileNotFoundException
	{
		super(path, m_x, m_y);
		this.p_x = m_x * Controller.SPRITE_SIZE;
		this.p_y = m_y * Controller.SPRITE_SIZE;
	}

	public void moveSprite(int x, int y, Direction direction)
	{
		this.p_x = x;
		this.p_y = y;
		this.direction = direction;
	}

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
