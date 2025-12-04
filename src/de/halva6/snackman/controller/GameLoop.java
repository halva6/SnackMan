package de.halva6.snackman.controller;

import java.io.FileNotFoundException;

import de.halva6.snackman.model.GenerateMap;
import de.halva6.snackman.view.Input;
import de.halva6.snackman.view.Map;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameLoop
{
	private long lastUpdate = 0;
	private Input input;
	private GraphicsContext gc;

	public GameLoop(Input input, Canvas canvas)
	{
		this.input = input;
		this.gc = canvas.getGraphicsContext2D();

		start();
		
		AnimationTimer timer = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				if (lastUpdate == 0)
				{
					lastUpdate = now;
				}
				double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
				updateGame(deltaTime);
				lastUpdate = now;
			}
		};
		timer.start();
	}
	
	private void start() 
	{
		GenerateMap gm = new GenerateMap(Controller.WIDTH, Controller.HEIGHT);
		try
		{
			Map map = new Map("res/border.png", "res/dot.png");
			map.renderMap(gc, gm.getMap());
		} catch (FileNotFoundException e)
		{
			System.err.println("[Error] rendering the tilemap: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void updateGame(double deltaTime)
	{
		
	}
}
