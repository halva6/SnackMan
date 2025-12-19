package de.halva6.snackman.model;

import de.halva6.snackman.controller.Controller;
import de.halva6.snackman.view.Map;

/**
 * A dangerous enemy that actively hunts the player using intelligent pathfinding.
 * This enemy calculates the best direction to move toward the player at each intersection.
 */
public class DangerousEntityEnemy extends EntityEnemy
{
	private EntityPlayer player;

	public DangerousEntityEnemy(int m_x, int m_y, int[][] map, EntityPlayer player)
	{
		super(m_x, m_y, map);
		this.player = player;
	}

	@Override
	public void move()
	{
		boolean frontWall = false;

		if (this.p_y % Controller.SPRITE_SIZE == 0 && this.p_x % Controller.SPRITE_SIZE == 0)
		{
			m_x = this.p_x / Controller.SPRITE_SIZE;
			m_y = this.p_y / Controller.SPRITE_SIZE;

			// Check if there's a wall in front
			frontWall = wallCollision(entityDirection);

			// At intersections or when blocked, choose the best direction toward player
			if (frontWall || isAtIntersection())
			{
				Direction bestDirection = findBestDirectionToPlayer();
				if (bestDirection != null)
				{
					entityDirection = bestDirection;
					frontWall = false; // We found a valid direction
				}
			}
		}

		// Move in the chosen direction
		if (!frontWall)
		{
			setSpeedInDirection(entityDirection);
		} else
		{
			speed_x = 0;
			speed_y = 0;
		}
		
		this.p_x += speed_x;
		this.p_y += speed_y;
	}

	/**
	 * Checks if the enemy is at an intersection (more than 2 directions available).
	 */
	private boolean isAtIntersection()
	{
		int availableDirections = 0;

		if (map[m_y - 1][m_x] >= Map.SNACK_NUMBER) availableDirections++; // UP
		if (map[m_y + 1][m_x] >= Map.SNACK_NUMBER) availableDirections++; // DOWN
		if (map[m_y][m_x - 1] >= Map.SNACK_NUMBER) availableDirections++; // LEFT
		if (map[m_y][m_x + 1] >= Map.SNACK_NUMBER) availableDirections++; // RIGHT

		return availableDirections > 2;
	}

	/**
	 * Finds the best direction to move toward the player.
	 * Uses Manhattan distance to evaluate each possible direction.
	 * Prevents the enemy from going backward unless it's the only option.
	 */
	private Direction findBestDirectionToPlayer()
	{
		int playerMapX = player.getPosX() / Controller.SPRITE_SIZE;
		int playerMapY = player.getPosY() / Controller.SPRITE_SIZE;

		Direction bestDirection = null;
		double bestDistance = Double.MAX_VALUE;
		Direction oppositeDirection = getOppositeDirection(entityDirection);

		// Evaluate all four directions
		Direction[] directions = Direction.values();
		
		for (Direction dir : directions)
		{
			// Skip walls
			if (wallCollision(dir))
			{
				continue;
			}

			// Calculate where we would be if we move in this direction
			int nextX = m_x;
			int nextY = m_y;

			switch (dir)
			{
			case UP:
				nextY--;
				break;
			case DOWN:
				nextY++;
				break;
			case LEFT:
				nextX--;
				break;
			case RIGHT:
				nextX++;
				break;
			}

			// Calculate Manhattan distance to player
			double distance = Math.abs(nextX - playerMapX) + Math.abs(nextY - playerMapY);

			// Prefer not going backward (add penalty to opposite direction)
			if (dir == oppositeDirection)
			{
				distance += 100; // Large penalty for turning around
			}

			// Choose the direction with the smallest distance to player
			if (distance < bestDistance)
			{
				bestDistance = distance;
				bestDirection = dir;
			}
		}

		return bestDirection;
	}

	/**
	 * Gets the opposite direction of the given direction.
	 */
	private Direction getOppositeDirection(Direction dir)
	{
		if (dir == null) return null;

		switch (dir)
		{
		case UP:
			return Direction.DOWN;
		case DOWN:
			return Direction.UP;
		case LEFT:
			return Direction.RIGHT;
		case RIGHT:
			return Direction.LEFT;
		default:
			return null;
		}
	}
}
