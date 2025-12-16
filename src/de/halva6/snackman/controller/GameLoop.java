package de.halva6.snackman.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.halva6.snackman.model.Direction;
import de.halva6.snackman.model.EntityEnemy;
import de.halva6.snackman.model.EntityPlayer;
import de.halva6.snackman.model.GenerateMap;
import de.halva6.snackman.view.Input;
import de.halva6.snackman.view.Map;
import de.halva6.snackman.view.PauseView;
import de.halva6.snackman.view.GameText;
import de.halva6.snackman.view.sprites.AnimatedMovingSprite;
import de.halva6.snackman.view.sprites.StaticSprite;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameLoop
{
	private static final Logger logger = Logger.getLogger(GameLoop.class.getName());
	private static final int DEFAULT_ENEMY_NUMBER = 3;
	private static final double FIXED_DELTA_TIME = 0.005; // like the fps

	private long lastUpdate = 0;
	private final Input input;

	private AnimationTimer timer;
	private double accumulator = 0;

	private GraphicsContext gc;
	private ArrayList<StaticSprite> tileMapSprites;

	private AnimatedMovingSprite player;
	private EntityPlayer playerE;

	private ArrayList<AnimatedMovingSprite> enemys = new ArrayList<>(DEFAULT_ENEMY_NUMBER);
	private ArrayList<EntityEnemy> enemeyE = new ArrayList<>(DEFAULT_ENEMY_NUMBER);

	private int scoreCount = 0;
	private GameText scoretText;
	private int dotCount = 0;
	private int enemy_number = DEFAULT_ENEMY_NUMBER;
	private boolean gameOver, win, pause, escapeBlock = false;

	private GameText timeText;
	private double playTime = 0;

	private PauseView pauseView = new PauseView();

	private final AudioController ac;

	public GameLoop(Group root, Input input, Canvas canvas)
	{
		this.input = input;
		this.gc = canvas.getGraphicsContext2D();
		this.scoretText = new GameText(gc, 5);
		this.timeText = new GameText(gc, 200);
		this.ac = new AudioController();

		root.getChildren().add(pauseView.getPauseView());

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

				if (!pause)
				{
					accumulator += deltaTime;

					while (accumulator >= FIXED_DELTA_TIME)
					{
						fixedUpdate();
						accumulator -= FIXED_DELTA_TIME;
					}
				}

				updateGame(deltaTime);
				lastUpdate = now;

				exitGame();
			}
		};

		timer.start();
	}

	private void exitGame()
	{
		if (gameOver)
		{
			timer.stop();
			Platform.runLater(() -> {
				try
				{
					gameOver();
				} catch (Exception e)
				{
					logger.log(Level.SEVERE, "Exception in gameOver()", e);
				}
			});
		}
		if (pauseView.getExitGame())
		{
			timer.stop();
		}
	}

	private void gameOver()
	{
		String status;
		String points = "Score: " + scoreCount;
		String time = String.format("Time: %.1f", playTime);
		if (win)
		{
			status = "You won the game";
			ac.playWinSound();
		} else
		{
			status = "You lost the game";
			ac.playHuntSound();
		}
		SceneController.gameOverScreenScene(gc.getCanvas(), SceneController.GAME_OVER_FXML_PATH, status, points, time);
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
			this.player = new AnimatedMovingSprite(Controller.PACMAN_PATH, 1, 1, 6);
			this.playerE = new EntityPlayer(1, 1, Direction.DOWN, gm.getMap());

			for (int i = 0; i < this.enemy_number; i++)
			{
				AnimatedMovingSprite e = new AnimatedMovingSprite(Controller.GHOST_PATH, 12, 11, 6);
				EntityEnemy ee = new EntityEnemy(12, 11, gm.getMap());

				this.enemys.add(e);
				this.enemeyE.add(ee);
			}

		} catch (FileNotFoundException e)
		{
			logger.log(Level.SEVERE, String.format("[Error] rendering the tilemap: %s", e.getMessage()), e);
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
		if (!pause)
		{
			// If there is no pause, and the escape key is pressed and then released, the
			// program will be paused.
			setPause();

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
			player.moveSprite(playerE.getPosX(), playerE.getPosY(), playerE.getEntitiyDirection(), playerE.isMoving());

			// move the enemies
			for (int i = 0; i < this.enemy_number; i++)
			{
				AnimatedMovingSprite e = this.enemys.get(i);
				EntityEnemy ee = this.enemeyE.get(i);

				e.moveSprite(ee.getPosX(), ee.getPosY(), ee.getEntitiyDirection(), ee.isMoving());
			}

			manageCollision();
			render(deltaTime);

			playTime += deltaTime;
		} else
		{
			// However, if there is a pause and the escape key is pressed and then released,
			// the pause will end.
			setPause();
		}
	}

	private void render(double deltaTime)
	{
		// resets the canvas (if not, all moving sprites would be duplicated)
		gc.clearRect(0, 0, Controller.WIDTH * Controller.SPRITE_SIZE,
				Controller.HEIGHT * Controller.SPRITE_SIZE + Controller.SCORE_HEIGHT);

		// render all tiles of the tile map
		for (StaticSprite tileSprite : tileMapSprites)
		{
			tileSprite.renderSprite(gc);
		}

		// render the player
		this.player.renderSprite(gc, deltaTime, Controller.PACMAN_ANIMATION_DUARTION);

		// render all enemies
		for (AnimatedMovingSprite e : this.enemys)
		{
			e.renderSprite(gc, deltaTime, Controller.GHOST_ANIMATION_DUARTION);
		}

		// render the score text
		scoretText.renderText(gc, "Score: " + this.scoreCount);
		timeText.renderText(gc, String.format("Time: %.1f", playTime));
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
			StaticSprite tile = tileMapSprites.get(i);
			// if the id of the tile is dot -> checks only dots because it is better for the
			// performance

			if (tile.getId().contains("food"))
			{
				if (tile.collideSprite(player))
				{
					String[] idSplit = tile.getId().split("-");

					// increase the score
					scoreCount += Integer.valueOf(idSplit[0]) * 5;

					// decrease the dotCount -> this represents the number of all available dots ->
					// if there are no dots anymore, the game is over an the player won
					dotCount--;

					// removes the dot from the tile map -> will not be rendered anymore
					tileMapSprites.remove(i);

					ac.playEatSound();
				}
			}
		}

		// checks all enemies
		for (AnimatedMovingSprite enemy : enemys)
		{
			if (enemy.collideSprite(player))
			{
				gameOver = true;
			}
		}
	}

	private void setPause()
	{
		// when the button is pressed to exit the pause screen
		if (pauseView.getExitPause())
		{
			escapeBlock = false;
			pause = !pause;
			pauseView.setVisible(pause);
			pauseView.setExitPause(false);
		}

		// When the Escape key is pressed and then released, the pause status is
		// reversed.
		if (input.isEscape() && !escapeBlock)
		{
			escapeBlock = true;
		}

		if (!input.isEscape() && escapeBlock)
		{
			escapeBlock = false;
			pause = !pause;
			pauseView.setVisible(pause);
		}
	}
}