package de.halva6.snackman.model;

public class Entity
{
	private double posX, posY;
	private final double speed = 100;
	
	public Entity(double startX, double startY) 
	{
		this.posX = startX;
		this.posY = startY;
	}
	
	public double[] up(double deltaTime) 
	{
		posY -= speed * deltaTime;
		return new double[]{posX,posY};
	}
	
	public double[] down(double deltaTime) 
	{
		posY += speed * deltaTime;
		return new double[]{posX,posY};
	}
	
	public double[] left(double deltaTime) 
	{
		posX -= speed * deltaTime;
		return new double[]{posX,posY};
	}
	
	public double[] right(double deltaTime) 
	{
		posX += speed * deltaTime;
		return new double[]{posX,posY};
	}
	
	public double[] getInitPos()
	{
		return new double[]{posX,posY};
	}
}
