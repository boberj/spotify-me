import java.util.TimerTask;

import javax.microedition.lcdui.Canvas;


public class Ticker extends TimerTask {
	
	private Canvas canvas;
	private String artist = "";
	private String title = "";
	private short state = 0;
	
	public Ticker(Canvas canvas) {
		this.canvas = canvas;
	}
	
	public void setText(String text) {
		int splitPosition = text.indexOf(" – ");
		if (splitPosition != -1) {
				artist = text.substring(0, splitPosition);
				title = text.substring(splitPosition + 3, text.length());
		} else {
			artist = title = text;
		}
	}
	
	public String getText() {
		if (state == 0) {
			return artist;
		} else {
			return title;
		}
	}

	public void run() {
		state = (short) ((state + 1) % 2);
		canvas.repaint();
	}
	
}
