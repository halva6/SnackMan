package de.halva6.snackman.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public interface Sprite <T extends Sprite<T>>
{
	public String getId();
	
	public void renderSprite(GraphicsContext gc, double deltaTime);
	
	public double getSize();
	
	public Rectangle2D getRect();
	public boolean collideSprite(T sprite);
}
