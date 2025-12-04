package de.halva6.snackman.controller;

import de.halva6.snackman.view.Input;
import javafx.animation.AnimationTimer;

public class GameLoop
{
	private long lastUpdate = 0;
	private Input input;

	public GameLoop(Input input)
	{
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
		
		this.input = input;
	}

	private void updateGame(double deltaTime)
	{
		System.out.println(this.input.isDown());
	}
}
