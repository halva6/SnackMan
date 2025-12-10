package de.halva6.snackman.view;

import de.halva6.snackman.controller.Controller;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Score
{
	private final String font = "DejaVu Sans Mono";
	private final int fontHeight = 20;
	
	private final int text_x;
	private final int text_y;
	
	private final int x_offset = 5;


	public Score(GraphicsContext gc)
	{
		gc.setFont(Font.font(font, FontWeight.BOLD, fontHeight));
		gc.setLineWidth(fontHeight);
		gc.setFill(Color.web("#0a728d"));
		
		this.text_x = x_offset;
		this.text_y = Controller.WIDTH * Controller.SPRITE_SIZE + Controller.SCORE_HEIGHT - (Controller.SCORE_HEIGHT/2) + (fontHeight/2);
	}

	public void renderText(GraphicsContext gc, String text)
	{
		gc.fillText(text, this.text_x,this.text_y);
	}

}
