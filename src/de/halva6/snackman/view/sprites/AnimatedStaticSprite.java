package de.halva6.snackman.view.sprites;

import de.halva6.snackman.controller.Controller;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class AnimatedStaticSprite extends Sprite
{
	private final int framesNumber;

	protected final Image[] frames;
	protected final double size;

	protected int currentIndex = 0;
	protected double elapsedTime = 0;

	public AnimatedStaticSprite(String imagePaths, int m_x, int m_y, int framesNumber)
	{
		super(imagePaths + "-" + m_x + "-" + m_y);
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

	public void renderSprite(GraphicsContext gc, double deltaTime, double animationTime)
	{
		elapsedTime += deltaTime;
		currentIndex = (int) (elapsedTime / animationTime) % frames.length;
		gc.drawImage(frames[currentIndex], this.p_x, this.p_y);
	}

	public int getCurrentIndex()
	{
		return this.currentIndex;
	}

	@Override
	public double getSize()
	{
		return this.size;
	}
}
