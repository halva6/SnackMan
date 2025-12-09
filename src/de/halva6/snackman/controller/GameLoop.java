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
import de.halva6.snackman.view.Score;
import de.halva6.snackman.view.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
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

	private int scoreCount = 0;
	private Score score;
	private int dotCount = 0;

	private boolean gameOver, win = false;

	public GameLoop(Input input, Canvas canvas)
	{
		this.input = input;
		this.gc = canvas.getGraphicsContext2D();
		this.score = new Score(gc);

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

				if (gameOver)
				{
					stop();
					Platform.runLater(() -> {
						try
						{
							gameOver();
						} catch (Exception e)
						{
							e.printStackTrace();
						}
					});
				}
			}
		};
		timer.start();
	}

	private void gameOver()
	{
		String status;
		String points = "" + scoreCount;
		if (win)
		{
			status = "You won the game";
		} else
		{
			status = "You lost the game";
		}
		SceneController.gameOverScreenScene(gc.getCanvas(), SceneController.gameOverFXMLPath, status, points);
	}

	// executes all before the first frame will be rendered
	private void awake()
	{
		GenerateMap gm = new GenerateMap(Controller.WIDTH, Controller.HEIGHT);
		try
		{
			Map map = new Map();
			this.tileMapSprites = map.initMap(gm.getMap());
			this.dotCount = map.getDotCount();
			this.player = new MovingSprite("/img/pacman.png", 1, 1);
			this.playerE = new EntityPlayer(1, 1, Direction.DOWN, gm.getMap());

			for (int i = 0; i < this.enemyNumber; i++)
			{
				MovingSprite e = new MovingSprite("/img/ghost.png", 9, 13);
				EntityEnemy ee = new EntityEnemy(9, 13, gm.getMap());

				this.enemys.add(e);
				this.enemeyE.add(ee);
			}

		} catch (FileNotFoundException e)
		{
			System.err.println("[Error] rendering the tilemap: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	// It updates at regular intervals.
	// It is independent of the frame rate because the frame rate always varies
	// depending on how many resources the process currently requires.
	private void fixedUpdate()
	{
		playerE.move();

		for (EntityEnemy ee : this.enemeyE)
		{
			ee.move();
		}

	}

	// updates every frame
	private void updateGame(double deltaTime)
	{
		// assigning keyboard input to the corresponding directions
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

		// move the player sprite
		player.moveSprite(gc, playerE.getPosX(), playerE.getPosY(), playerE.getEntitiyDirection().getAngle());

		// move the enemies

		for (int i = 0; i < this.enemyNumber; i++)
		{
			MovingSprite e = this.enemys.get(i);
			EntityEnemy ee = this.enemeyE.get(i);

			e.moveSprite(gc, ee.getPosX(), ee.getPosY(), ee.getEntitiyDirection().getAngle());
		}

		manageCollision();
		render();
	}

	private void render()
	{
		// resets the canvas (if not, all moving sprites would be duplicated)
		gc.clearRect(0, 0, Controller.WIDTH * Controller.SPRITE_SIZE,
				Controller.HEIGHT * Controller.SPRITE_SIZE + Controller.SCORE_HEIGHT);

		// render all tiles of the tile map
		for (Sprite tileSprite : tileMapSprites)
		{
			tileSprite.renderSprite(gc);
		}

		// render the player
		this.player.renderSprite(gc);

		// render all enemies

		for (MovingSprite e : this.enemys)
		{
			e.renderSprite(gc);
		}

		// render the score text
		score.renderText(gc, "Score: " + this.scoreCount);
	}

	// collision detection with the enemies and the dots
	private void manageCollision()
	{
		if (dotCount == 0)
		{
			win = true;
			gameOver = true;
		}

		// checks all dots
		for (int i = 0; i < tileMapSprites.size(); i++)
		{
			Sprite tile = tileMapSprites.get(i);
			// if the id of the tile is dot -> checks only dots because it is better for the
			// performance
			
			if (tile.getId().contains("food"))
			{
				if (tile.collideSprite(player))
				{
					
					String idSplit[] = tile.getId().split("-");
															
					// increase the score
					scoreCount += Integer.valueOf(idSplit[0]) * 5;
					
					// decrease the dotCount -> this represents the number of all available dots ->
					// if there are no dots anymore, the game is over an the player won
					dotCount--;

					// removes the dot from the tile map -> will not be rendered anymore
					tileMapSprites.remove(i);
				}
			}
		}

		// checks all enemies
		for (int i = 0; i < enemys.size(); i++)
		{
			if (enemys.get(i).collideSprite(player))
			{
				gameOver = true;
			}

		}
	}
}
