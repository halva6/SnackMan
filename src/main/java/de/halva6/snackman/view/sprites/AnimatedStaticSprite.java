package de.halva6.snackman.view.sprites;

import de.halva6.snackman.controller.Controller;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents a static sprite with multiple animation frames.
 * <p>
 * The sprite cycles through its frames to create an animation effect. It is
 * positioned on a map grid, and all frames are assumed to be square.
 * </p>
 */
public class AnimatedStaticSprite extends Sprite
{
	private final int framesNumber;

	protected final Image[] frames;
	protected final double size;

	protected int currentIndex = 0;
	protected double elapsedTime = 0;

	/**
	 * Creates a new animated static sprite at the given map position.
	 *
	 * @param imagePaths   the base path of the image files (each frame is suffixed
	 *                     with an index)
	 * @param m_x          the x-position on the map grid
	 * @param m_y          the y-position on the map grid
	 * @param framesNumber the number of frames in the animation
	 */
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

	/**
	 * Loads all animation frames from the given base path.
	 *
	 * @param imagePaths the base path of the image files
	 * @return an array of loaded images
	 */
	private Image[] getFrames(String imagePaths)
	{
		Image[] frames = new Image[framesNumber];
		for (int i = 0; i < framesNumber; i++)
		{
			frames[i] = new Image(getClass().getResourceAsStream(imagePaths + i + ".png"));
		}
		return frames;
	}

	/**
	 * Renders the current frame of the animated sprite.
	 * <p>
	 * The frame is selected based on the elapsed time and the specified animation
	 * interval.
	 * </p>
	 *
	 * @param gc            the {@link GraphicsContext} used to draw the sprite
	 * @param deltaTime     the time elapsed since the last frame (in seconds)
	 * @param animationTime the total time each frame should be displayed
	 */
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

	/**
	 * Returns the size of the sprite.
	 *
	 * @return the size in pixels
	 */
	@Override
	public double getSize()
	{
		return this.size;
	}
}
