package de.halva6.snackman.view;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Input
{
	private boolean up, down, left, right = false;
	private EventHandler<KeyEvent> event;

	public Input()
	{
		EventHandler<KeyEvent> event = new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent keyEvent)
			{
				boolean keyCode = false;
				if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
				{
					keyCode = true;
				}

				switch (keyEvent.getCode())
				{
				case KeyCode.W:
					up = keyCode;
					break;
				case KeyCode.S:
					down = keyCode;
					break;
				case KeyCode.A:
					left = keyCode;
					break;
				case KeyCode.D:
					right = keyCode;
					break;
				default:
					break;
				}

			}
		};

		this.event = event;
	}

	public boolean isUp()
	{
		return up;
	}

	public boolean isDown()
	{
		return down;
	}

	public boolean isLeft()
	{
		return left;
	}

	public boolean isRight()
	{
		return right;
	}

	public EventHandler<KeyEvent> getEvent()
	{
		return event;
	}
}
