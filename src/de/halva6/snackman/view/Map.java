package de.halva6.snackman.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class Map
{
	public static final String TILEMAP_PATH = "res/img/tiles/";

	public static final int SNACK_NUMBER = 15;

	public static final int NUMBER_OF_TILES = 19;

	private String[] tilePaths;
	private int dotCount = 0;

	public Map(String... tilePaths)
	{
		this.tilePaths = tilePaths;
	}

	public Map()
	{
		File directory = new File(TILEMAP_PATH);
		File[] files = directory.listFiles();

		if (files != null)
		{
			this.tilePaths = new String[files.length];

			for (int i = 0; i < files.length; i++)
			{
				this.tilePaths[i] = files[i].getName();
			}
		}

		Arrays.sort(this.tilePaths);
	}

	public ArrayList<Sprite> initMap(int[][] map) throws FileNotFoundException
	{
		ArrayList<Sprite> tileMapSprites = new ArrayList<Sprite>();
		for (int y = 0; y < map.length; y++)
		{
			for (int x = 0; x < map[y].length; x++)
			{
				int tileId = map[y][x];

				Sprite tile = null;
				if (tileId >= 15)
				{
					String id = tileId + "-food-" + x + y;
					tile = new Sprite(TILEMAP_PATH + tilePaths[tileId], id, x, y);
					tileMapSprites.add(tile);

					dotCount++;
				} else if (tileId >= 0)
				{
					tile = new Sprite(TILEMAP_PATH + tilePaths[tileId], x, y);
					tileMapSprites.add(tile);
				}

			}
		}
		return tileMapSprites;
	}

	public int getDotCount()
	{
		return this.dotCount;
	}

}
