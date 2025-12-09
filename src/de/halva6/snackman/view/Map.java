package de.halva6.snackman.view;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Map
{
	private String[] tilePaths;

	public Map(String... tilePaths)
	{
		this.tilePaths = tilePaths;
	}

	public ArrayList<Sprite> renderMap(int[][] map) throws FileNotFoundException
	{
		ArrayList<Sprite> tileMapSprites = new ArrayList<Sprite>();
		for (int y = 0; y < map.length; y++)
		{
			for (int x = 0; x < map[y].length; x++)
			{

				int tileId = map[y][x];
				Sprite tile = new Sprite(tilePaths[tileId], x, y);

				tileMapSprites.add(tile);
			}
		}
		return tileMapSprites;
	}
}
