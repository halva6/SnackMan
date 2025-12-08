package de.halva6.snackman.model;

public enum Direction
{
	UP(270), DOWN(90), LEFT(180), RIGHT(0);
	
	private final double angle;
	
	private Direction(double angle) 
	{
		this.angle = angle;
	}
	
	public double getAngle() 
	{
		return angle;
	}
}
