package de.halva6.snackman.view;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Map
{
	private String[] tilePaths;
	private int dotCount = 0;

	public Map(String... tilePaths)
	{
		this.tilePaths = tilePaths;
	}

	public ArrayList<Sprite> initMap(int[][] map) throws FileNotFoundException
	{
		ArrayList<Sprite> tileMapSprites = new ArrayList<Sprite>();
		for (int y = 0; y < map.length; y++)
		{
			for (int x = 0; x < map[y].length; x++)
			{
				int tileId = map[y][x];
				
				if(tileId == 1) 
				{
					dotCount++;
				}
				
				Sprite tile = new Sprite(tilePaths[tileId], x, y);
				
				tileMapSprites.add(tile);
			}
		}
		return tileMapSprites;
	}
	
	public int getDotCount() 
	{
		return this.dotCount;
	}
	
}
