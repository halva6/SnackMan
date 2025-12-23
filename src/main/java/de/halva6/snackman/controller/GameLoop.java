package de.halva6.snackman.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.halva6.snackman.model.Direction;
import de.halva6.snackman.model.DangerousEntityEnemy;
import de.halva6.snackman.model.EntityEnemy;
import de.halva6.snackman.model.EntityPlayer;
import de.halva6.snackman.model.level.GenerateMap;
import de.halva6.snackman.model.level.LevelData;
import de.halva6.snackman.model.level.LevelLoader;
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

/**
 * Manages the entire gameplay. This includes rendering, collision detection,
 * checking if the game has ended, starting the game, user input, and moving
 * players and opponents, all within the main loop.
 */
public class GameLoop
{
	private static final Logger logger = Logger.getLogger(GameLoop.class.getName());
	private static final double FIXED_DELTA_TIME = 0.005; // like the fps

	private long lastUpdate;
	private final Input input;

	private final AnimationTimer timer;
	private double accumulator = 0;

	private GraphicsContext gc;

	private LevelData levelData;

	private ArrayList<StaticSprite> tileMapSprites;

	private AnimatedMovingSprite player;
	private EntityPlayer playerE;

	private final int enemyNumber;
	private ArrayList<AnimatedMovingSprite> enemys = new ArrayList<>();
	private ArrayList<EntityEnemy> enemeyE = new ArrayList<>();

	private int scoreCount = 0;
	private GameText scoretText;
	private int dotCount = 0;
	private boolean gameOver, win, pause, escapeBlock = false;

	private GameText timeText;
	private double playTime = 0;

	private PauseView pauseView = new PauseView();

	private final AudioController ac;

	/**
	 * Creates the main loop of the game.
	 * 
	 * <p>
	 * The main loop is based on an AnimationTimer, which is actually intended for
	 * creating animations. However, due to its similarity to game loops, the
	 * {@link javafx.animation.AnimationTimer} can also be used for this purpose, as
	 * JavaFX handles the thread management and the actual rendering. A Canvas is
	 * well-suited for displaying custom graphics; it's designed to group many
	 * different graphics into a single node, which significantly improves
	 * performance.
	 * </p>
	 * 
	 * @param root      Layout in which the canvas is located, intermediate layer
	 *                  between the stage and the canvas
	 * @param input     The input must be "connected" to the stage before this class
	 *                  is created, so that the physical inputs can be queried at
	 *                  the end of the game loop.
	 * @param canvas    the drawing surface on which everything is displayed
	 * @param levelData includes the parameters for the respective different levels,
	 *                  e.g. the number of opponents
	 */
	public GameLoop(Group root, Input input, Canvas canvas, LevelData levelData)
	{
		this.input = input;
		this.gc = canvas.getGraphicsContext2D();
		this.scoretText = new GameText(gc, 5);
		this.timeText = new GameText(gc, 200);
		this.ac = new AudioController();

		this.levelData = levelData;
		this.enemyNumber = levelData.enemyStartX().length;

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
					return; // skip first frame to get proper delta time
				}

				double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
				lastUpdate = now;

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
				exitGame();
			}
		};

		timer.start();
	}

	/**
	 * Stops the game execution depending on the current game state.
	 *
	 * <p>
	 * If the game is over, the game timer is stopped and the {@code gameOver()}
	 * method is executed on the JavaFX Application Thread using
	 * {@link javafx.application.Platform#runLater(Runnable)} to ensure thread-safe
	 * UI updates. Any exceptions occurring during this process are logged.
	 * </p>
	 *
	 * <p>
	 * If the user chooses to exit the game via the pause menu, the game timer is
	 * also stopped.
	 * </p>
	 *
	 * <p>
	 * This method is typically called from the game loop or in response to user
	 * actions such as pausing or exiting the game.
	 * </p>
	 */
	private void exitGame()
	{
		if (gameOver)
		{
			timer.stop();
			//
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

	/**
	 * Starts the Game Over screen and prepares some information so that it can be
	 * displayed after the game ends. Additionally, a new high score or best time is
	 * saved to an external XML file, potentially allowing players to unlock new
	 * levels.
	 */
	private void gameOver()
	{
		String status;
		String points = "Score: " + scoreCount;
		String timeStr = String.format("Time: %.1f", playTime);

		if (win)
		{
			status = "You won the game";
			ac.playWinSound();
		} else
		{
			status = "You lost the game";
			ac.playHuntSound();
		}

		if ((levelData.currentHighScore() <= scoreCount
				|| levelData.currentHighScore() == scoreCount && levelData.currentBestTime() >= playTime)
				&& levelData.levelId() != -1) //-1 are the random levels
		{
			int timeInt = (int) Math.round(playTime);
			LevelLoader.saveExternalLevelStats(this.levelData.levelId(), scoreCount, timeInt,
					levelData.nextLevelAvailable(scoreCount, timeInt));
		}

		SceneController.gameOverScreenScene(gc.getCanvas(), SceneController.GAME_OVER_FXML_PATH, status, points,
				timeStr);
	}

	// executes all before the first frame will be rendered
	/**
	 * Executed before the first frame.
	 * 
	 * <p>
	 * Initialization of the map and the tilemap, as well as the players and
	 * enemies.
	 * </p>
	 */
	private void awake()
	{
		GenerateMap gm;

		if (levelData.levelId() == -1) // levelId -1 means it is a random level
		{
			gm = new GenerateMap(Controller.WIDTH, Controller.HEIGHT);
		} else
		{
			gm = new GenerateMap(Controller.WIDTH, Controller.HEIGHT, levelData.tileFilePath());
		}

		try
		{
			Map map = new Map();
			this.tileMapSprites = map.initMap(gm.getMap());
			this.dotCount = map.getDotCount();

			// start pos from level data --> from the level.xml
			this.playerE = new EntityPlayer(levelData.playerStartX(), levelData.playerStartY(), Direction.DOWN,
					gm.getMap());
			this.player = new AnimatedMovingSprite(Controller.PACMAN_PATH, playerE.getPosX(), playerE.getPosX(), 6);

			for (int i = 0; i < enemyNumber; i++)
			{
				int enemyStartX = levelData.enemyStartX()[i];
				int enemyStartY = levelData.enemyStartY()[i];

				EntityEnemy ee;

				// first enemy is dangerous (hunts the player), others are random
				// intelligence: 0.0 = random, 1.0 = perfect hunter, 0.5 = balanced
				if (i == 0)
				{
					ee = new DangerousEntityEnemy(enemyStartX, enemyStartY, gm.getMap(), playerE, 0.5);
				} else
				{
					ee = new EntityEnemy(enemyStartX, enemyStartY, gm.getMap());
				}

				AnimatedMovingSprite e = new AnimatedMovingSprite(Controller.GHOST_PATH, enemyStartX, enemyStartY, 6);

				this.enemys.add(e);
				this.enemeyE.add(ee);
			}

		} catch (FileNotFoundException e)
		{
			logger.log(Level.SEVERE, String.format("[Error] rendering the tilemap: %s", e.getMessage()), e);
			System.exit(1);
		}
	}

	/**
	 * It updates at regular intervals, regardless of the frame rate.
	 * 
	 * <p>
	 * Since not every device running the program has the same resources, the
	 * movement of the player and the enemies is adjusted accordingly.
	 * </p>
	 */
	private void fixedUpdate()
	{
		playerE.move();

		for (EntityEnemy ee : this.enemeyE)
		{
			ee.move();
		}

	}

	/**
	 * Updates with every frame
	 * 
	 * <p>
	 * At the start of each frame, the keyboard input is requested, and the player
	 * moves accordingly. Enemies are also moved, and collision is checked to
	 * determine if the player has, for example, just collected a point.
	 * Additionally, everything is rendered, and the time the user has spent in the
	 * current game is measured.
	 * </p>
	 * 
	 * @param deltaTime indicates the time difference between each frame
	 */
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
			player.moveSprite(playerE.getPosX(), playerE.getPosY(), playerE.getEntityDirection(), playerE.isMoving());

			// move the enemies
			for (int i = 0; i < enemyNumber; i++)
			{
				AnimatedMovingSprite e = this.enemys.get(i);
				EntityEnemy ee = this.enemeyE.get(i);

				e.moveSprite(ee.getPosX(), ee.getPosY(), ee.getEntityDirection(), ee.isMoving());
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

	/**
	 * Renders everything related to the game onto a canvas.
	 * 
	 * @param deltaTime indicates the time difference between each frame
	 */
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

	/**
	 * Checks if collisions have occurred, either between the player and collectible
	 * items or between the player and the enemy.
	 */
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

	/**
	 * Toggles the pause state of the application.
	 * <p>
	 * The pause mode can be exited either by pressing the pause view's exit button
	 * or by pressing and releasing the Escape key. An internal escape block
	 * prevents repeated toggling while the Escape key is held down.
	 * </p>
	 * 
	 * When the pause state changes, the visibility of the pause view is updated
	 * accordingly.
	 */
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