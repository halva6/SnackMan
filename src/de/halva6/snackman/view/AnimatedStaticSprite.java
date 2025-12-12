package de.halva6.snackman.view;

import de.halva6.snackman.controller.Controller;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class AnimatedStaticSprite implements Sprite<AnimatedStaticSprite>
{
	private static final double ANIMATION_TIME = 0.100;

	private final String id;
	private final int framesNumber;

	protected final Image[] frames;
	protected final double size;

	protected double p_x, p_y;

	public AnimatedStaticSprite(String imagePaths, int m_x, int m_y, int framesNumber)
	{
		this.id = imagePaths + "-" + m_x + "-" + m_y;
		this.framesNumber = framesNumber;

		this.frames = getFrames(imagePaths);
		// It is assumed that all graphics are square (throughout the entire project),
		// therefore it doesn't matter whether the height or the width is used here.
		this.size = frames[0].getHeight();

		this.p_x = m_x * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - size) / 2;
		this.p_y = m_y * Controller.SPRITE_SIZE + (Controller.SPRITE_SIZE - size) / 2;

	}

	private Image[] getFrames(String imagePaths)
	{
		Image[] frames = new Image[framesNumber];
		for (int i = 0; i < framesNumber; i++)
		{
			frames[i] = new Image(getClass().getResourceAsStream(imagePaths + i + ".png"));
		}
		return frames;
	}

	@Override
	public String getId()
	{
		return this.id;
	}

	@Override
	public void renderSprite(GraphicsContext gc, double deltaTime)
	{
		int index = (int) ((deltaTime % (frames.length * ANIMATION_TIME)) / ANIMATION_TIME);
		gc.drawImage(frames[index], this.p_x, this.p_y);
	}

	@Override
	public double getSize()
	{
		return this.size;
	}

	@Override
	public Rectangle2D getRect()
	{
		return new Rectangle2D(p_x, p_y, size, size);
	}

	@Override
	public boolean collideSprite(AnimatedStaticSprite otherSprite)
	{
		return this.getRect().intersects(otherSprite.getRect());
	}

}
