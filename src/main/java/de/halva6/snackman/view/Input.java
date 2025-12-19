package de.halva6.snackman.view;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class Input
{
	private boolean up, down, left, right, escape = false;
	private EventHandler<KeyEvent> event;

	public Input()
	{
		this.event = keyEvent ->
		{
			boolean isPressed = keyEvent.getEventType() == KeyEvent.KEY_PRESSED;

			switch (keyEvent.getCode())
			{
			case W, UP:
				up = isPressed;
				break;
			case S, DOWN:
				down = isPressed;
				break;
			case A, LEFT:
				left = isPressed;
				break;
			case D, RIGHT:
				right = isPressed;
				break;
			case ESCAPE:
				escape = isPressed;
				break;
			default:
				break;
			}
		};
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

	public boolean isEscape()
	{
		return escape;
	}

	public EventHandler<KeyEvent> getEvent()
	{
		return event;
	}
}
