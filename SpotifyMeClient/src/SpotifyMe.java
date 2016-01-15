import java.io.IOException;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import Model.Status;

public class SpotifyMe extends MIDlet implements KeyListener, SpotifyEventListener {

	private View view;
	private Model model;
	private SpotifyService spotifyService = new SpotifyService(this);
	
	/**
	 * Creates several screens and navigates between them.
	 */
	public SpotifyMe() {
		model = new Model();
		view = new View(model);
		view.setKeyListener(this);
	}
	
	
	/* (non-Javadoc)
	 * @see javax.microedition.midlet.MIDlet#startApp()
	 */
	protected void startApp() throws MIDletStateChangeException {
		Display.getDisplay(this).setCurrent(view);
	}
	
	private void exit() {
		Display.getDisplay(this).setCurrent(null);
		destroyApp(true);
		notifyDestroyed();
	}
	
	/* (non-Javadoc)
	 * @see javax.microedition.midlet.MIDlet#destroyApp(boolean)
	 */
	protected void destroyApp(boolean unconditional) {}

	/* (non-Javadoc)
	 * @see javax.microedition.midlet.MIDlet#pauseApp()
	 */
	protected void pauseApp() {}

	public void keyUpPressed() {
		spotifyService.volumeUp();
	}

	public void keyDownPressed() {
		spotifyService.volumeDown();
	}

	public void keyLeftPressed() {
		spotifyService.previous();
	}

	public void keyRightPressed() {
		spotifyService.next();
	}

	public void keyFirePressed() {
		if (model.getStatus() == Status.DISCONNECTED) {
			new Thread(new Runnable() {
				public void run() {
					try {
						model.setStatus(Status.CONNECTING);
						spotifyService.connect();
						model.setStatus(Status.CONNECTED);
					} catch (IOException e) {
						model.setStatus(Status.DISCONNECTED);
						Display.getDisplay(SpotifyMe.this).setCurrent(new Alert("Oops", e.getMessage(), null, null));
					}					
				}
			}).start();
		} else if (model.getStatus() == Status.CONNECTED) {
			spotifyService.playPause();
		}
	}

	public void titleReceived(String title) {
		model.setTitle(title);
	}


	public void imageReceived(Image image) {
		model.setCoverArt(image);
	}
	
}
