package de.halva6.snackman.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import de.halva6.snackman.controller.Controller;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Map
{
	private Image[] tiles;
	
	public Map(String ...graphicPaths) throws FileNotFoundException 
	{
		tiles = new Image[graphicPaths.length];
		
		for(int i = 0; i < graphicPaths.length; i++) 
		{
			tiles[i] = new Image(new FileInputStream(graphicPaths[i]));
		}
	}
	
	public void renderMap(GraphicsContext gc, int[][] map) 
	{
	    for (int y = 0; y < map.length; y++) {
	        for (int x = 0; x < map[y].length; x++) {

	            int tileId = map[y][x];
	            Image tile = tiles[tileId];
	            
	            double diffSize = (Controller.SPRITE_SIZE - tile.getHeight()) / 2;

	            gc.drawImage(tile, x * Controller.SPRITE_SIZE + diffSize, y * Controller.SPRITE_SIZE + diffSize);
	        }
	    }		
	}
}
