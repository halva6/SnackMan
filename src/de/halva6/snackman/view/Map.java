package de.halva6.snackman.view;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import de.halva6.snackman.controller.Controller;
import de.halva6.snackman.view.sprites.StaticSprite;

public class Map
{
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
		tilePaths = new String[] { "00.png", "01.png", "02.png", "03.png", "04.png", "05.png", "06.png", "07.png",
				"08.png", "09.png", "10.png", "11.png", "12.png", "13.png", "14.png", "15.png", "16.png", "17.png",
				"18.png" };
	}

	public ArrayList<StaticSprite> initMap(int[][] map) throws FileNotFoundException
	{
		ArrayList<StaticSprite> tileMapSprites = new ArrayList<>(map.length * map[0].length);
		for (int y = 0; y < map.length; y++)
		{
			for (int x = 0; x < map[y].length; x++)
			{
				int tileId = map[y][x];

				StaticSprite tile = null;
				if (tileId >= 15)
				{
					String id = tileId + "-food-" + x + y;
					tile = new StaticSprite(Controller.TILEMAP_PATH + tilePaths[tileId], id, x, y);
					tileMapSprites.add(tile);

					dotCount++;
				} else if (tileId >= 0)
				{
					tile = new StaticSprite(Controller.TILEMAP_PATH + tilePaths[tileId], x, y);
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
