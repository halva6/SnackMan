package de.halva6.snackman.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import de.halva6.snackman.model.Direction;
import de.halva6.snackman.model.EntityEnemy;
import de.halva6.snackman.model.EntityPlayer;
import de.halva6.snackman.model.GenerateMap;
import de.halva6.snackman.view.Input;
import de.halva6.snackman.view.Map;
import de.halva6.snackman.view.MovingSprite;
import de.halva6.snackman.view.Sprite;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameLoop
{
	private long lastUpdate = 0;
	private Input input;

	private AnimationTimer timer;
	private double accumulator = 0;
	private final double fixedDeltaTime = 0.005; // like the fps

	private GraphicsContext gc;
	private ArrayList<Sprite> tileMapSprites;

	private MovingSprite player;
	private EntityPlayer playerE;

	private ArrayList<MovingSprite> enemys = new ArrayList<MovingSprite>();
	private ArrayList<EntityEnemy> enemeyE = new ArrayList<EntityEnemy>();
	private final int enemyNumber = 3;

	public GameLoop(Input input, Canvas canvas)
	{
		this.input = input;
		this.gc = canvas.getGraphicsContext2D();

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

				accumulator += deltaTime;

				while (accumulator >= fixedDeltaTime)
				{
					fixedUpdate();
					accumulator -= fixedDeltaTime;
				}

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
			this.player = new MovingSprite("res/pacman.png", 1, 1);
			this.playerE = new EntityPlayer(1, 1, Direction.DOWN, gm.getMap());

			for (int i = 0; i < this.enemyNumber; i++)
			{
				MovingSprite e = new MovingSprite("res/ghost.png", 14, 14);
				EntityEnemy ee = new EntityEnemy(14, 14, gm.getMap());

				this.enemys.add(e);
				this.enemeyE.add(ee);
			}

		} catch (FileNotFoundException e)
		{
			System.err.println("[Error] rendering the tilemap: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void fixedUpdate()
	{
		playerE.move();

		if (enemyNumber > 0)
		{
			for (EntityEnemy ee : this.enemeyE)
			{
				ee.move();
			}
		}
	}

	private void updateGame(double deltaTime)
	{
		if (input.isUp())
		{
			playerE.setReqDirection(Direction.UP);
		} else if (input.isDown())
		{
			playerE.setReqDirection(Direction.DOWN);
		} else if (input.isLeft())
		{
			playerE.setReqDirection(Direction.LEFT);
		} else if (input.isRight())
		{
			playerE.setReqDirection(Direction.RIGHT);
		}

		player.moveSprite(gc, playerE.getPosX(), playerE.getPosY(), playerE.getEntitiyDirection().getAngle());

		if (enemyNumber > 0)
		{
			for (int i = 0; i < this.enemyNumber; i++)
			{
				MovingSprite e = this.enemys.get(i);
				EntityEnemy ee = this.enemeyE.get(i);

				e.moveSprite(gc, ee.getPosX(), ee.getPosY(), ee.getEntitiyDirection().getAngle());
			}
		}

		render();
	}

	private void render()
	{
		gc.clearRect(0, 0, Controller.WIDTH * Controller.SPRITE_SIZE, Controller.HEIGHT * Controller.SPRITE_SIZE);
		for (Sprite tileSprite : tileMapSprites)
		{
			tileSprite.renderSprite(gc);
		}
		this.player.renderSprite(gc);

		for (MovingSprite e : this.enemys)
		{
			e.renderSprite(gc);
		}

	}

}
