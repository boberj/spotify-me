import java.io.IOException;
import java.util.Timer;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import Model.Status;

public class View extends Canvas implements ChangeEventListener {

	private Model model;
	private KeyListener keyListener;
	private Image controls;
	private Ticker ticker;
	private Font mainFont;
	private Font statusFont; 
	private int mainFontHeight;

	public View(Model model) {
		super();
		this.model = model;
		this.model.setChangeEventListener(this);
		try { controls = Image.createImage("/controls.png"); } catch (IOException e) { }
		this.mainFont = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
		this.statusFont = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_LARGE);
		this.mainFontHeight = mainFont.getBaselinePosition();
		ticker = new Ticker(this);
		setFullScreenMode(true);
		new Timer().schedule(ticker, 100, 3000);
	}

	public void setKeyListener(KeyListener listener) {
		this.keyListener = listener;
	}

	protected void keyPressed(int keyCode) {
		if (keyListener != null) {
			switch (getGameAction(keyCode)) {
			case Canvas.UP:
				keyListener.keyUpPressed();
				break;

			case Canvas.DOWN:
				keyListener.keyDownPressed();
				break;

			case Canvas.LEFT:
				keyListener.keyLeftPressed();
				break;

			case Canvas.RIGHT:
				keyListener.keyRightPressed();
				break;
				
			case Canvas.FIRE:
				keyListener.keyFirePressed();
				break;
			}
		}
	}

	protected void paint(Graphics g) {
		int height = getHeight();
		int width = getWidth();
		
		Status status = model.getStatus();
		if (status == Status.CONNECTING) {
			drawConnecting(g, width, height);
		} else if (status == Status.CONNECTED) {
			drawConnected(g, width, height);
		} else if (status == Status.DISCONNECTED) {
			drawDisconnected(g, width, height);
		}
	}
	
	private void drawConnecting(Graphics g, int width, int height) {
		g.setFont(statusFont);
		g.setColor(0x00000000);
		g.fillRect(0, 0, width, height);
		g.setColor(0x00FFFFFF);
		
		String str = "Connecting...";
		g.drawString(str, width / 2 , height / 2, Graphics.HCENTER | Graphics.BASELINE);
	}
	
	private void drawConnected(Graphics g, int width, int height) {
		g.setFont(mainFont);
		g.setColor(0x00000000);
		g.fillRect(0, 240, width, 42);
		g.setColor(0x00FFFFFF);
		
		g.drawImage(model.coverArt, width / 2, 0, Graphics.HCENTER | Graphics.TOP);
		g.drawString(ticker.getText(), width / 2, 240 + (38 - mainFontHeight)/ 2, Graphics.HCENTER | Graphics.TOP);
		g.drawImage(controls, width / 2, height, Graphics.HCENTER | Graphics.BOTTOM);
	}
	
	private void drawDisconnected(Graphics g, int width, int height) {
		g.setFont(statusFont);
		g.setColor(0x00000000);
		g.fillRect(0, 0, width, height);
		g.setColor(0x00FFFFFF);
		
		g.drawString("Disconnected", width / 2, height /2, Graphics.HCENTER | Graphics.BASELINE);
		g.drawString("Connect", width / 2, height, Graphics.HCENTER | Graphics.BOTTOM);
	}

	public void onChange() {
		ticker.setText(model.getTitle());
		repaint();
	}
	
}
