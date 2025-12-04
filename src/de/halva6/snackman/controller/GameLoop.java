package de.halva6.snackman.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import de.halva6.snackman.model.Entity;
import de.halva6.snackman.model.GenerateMap;
import de.halva6.snackman.view.Input;
import de.halva6.snackman.view.Map;
import de.halva6.snackman.view.Sprite;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameLoop
{
	private long lastUpdate = 0;
	private Input input;
	
	private AnimationTimer timer;
	
	private GraphicsContext gc;
	private ArrayList<Sprite> tileMapSprites;
	
	private Sprite player;
	private Entity playerE;

	public GameLoop(Input input, Canvas canvas)
	{
		this.input = input;
		this.gc = canvas.getGraphicsContext2D();
		this.playerE = new Entity(50,50);

		awake();
		
		this.timer = new AnimationTimer()
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
	
	private void awake() 
	{
		GenerateMap gm = new GenerateMap(Controller.WIDTH, Controller.HEIGHT);
		try
		{
			Map map = new Map("res/border.png", "res/dot.png");
			this.tileMapSprites = map.renderMap(gm.getMap());
			this.player = new Sprite("res/pacman.png");
			this.player.setPos(playerE.getInitPos());
		} catch (FileNotFoundException e)
		{
			System.err.println("[Error] rendering the tilemap: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void updateGame(double deltaTime)
	{
		render();
		if(input.isUp()) 
		{
			this.player.moveSprite(gc, playerE.up(deltaTime));
		}
		if(input.isDown()) 
		{
			this.player.moveSprite(gc, playerE.down(deltaTime));
		}
		if(input.isLeft()) 
		{
			this.player.moveSprite(gc, playerE.left(deltaTime));
		}
		if(input.isRight()) 
		{
			this.player.moveSprite(gc, playerE.right(deltaTime));
		}
	}
	
	private void render() 
	{
	    gc.clearRect(0, 0, Controller.WIDTH * Controller.SPRITE_SIZE, Controller.HEIGHT * Controller.SPRITE_SIZE);
	    for(Sprite tileSprite : tileMapSprites) 
	    {
	    	tileSprite.renderSprite(gc);
	    }
	    this.player.renderSprite(gc);

	}
	
}
