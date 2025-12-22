package de.halva6.snackman.model;

/**
 * Represents the four possible movement directions in the game.
 * <p>
 * Each direction is associated with an angle in degrees, which can be used for
 * rotating sprites or other directional calculations.
 * </p>
 */
public enum Direction
{
	/** Upward direction (270 degrees). */
	UP(270),
	/** Downward direction (90 degrees). */
	DOWN(90),
	/** Left direction (180 degrees). */
	LEFT(180),
	/** Right direction (0 degrees). */
	RIGHT(0);

	private final double angle;

	/**
	 * Creates a direction with the specified angle.
	 *
	 * @param angle the rotation angle in degrees
	 */
	private Direction(double angle)
	{
		this.angle = angle;
	}

	public double getAngle()
	{
		return angle;
	}
}
