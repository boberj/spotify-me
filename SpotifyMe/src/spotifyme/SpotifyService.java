package spotifyme;
import java.awt.image.BufferedImage;

import spotifyme.jna.extra.AppCommand;
import spotifyme.jna.extra.WinUserExtra;
import spotifyme.jna.utils.ImageUtils;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.WPARAM;


public class SpotifyService {
	
	private static final int HEIGHT_OF_CONTROLS = 93;
	private static final int BORDER_COLOR = 0xff282828;
	private static final int MAIN_AREA_COLOR = 0xff121314;
	private HWND hWnd;
	private char[] titleBuffer = new char[1000];
	private Thread observer;
	
	public SpotifyService(final SpotifyListener listener) throws Exception {
		hWnd = User32.INSTANCE.FindWindow("SpotifyMainWindow", null);
		
		if (hWnd == null) {
			throw new Exception("Could not find Spotify window");
		}
		
		observer = new Thread(new Runnable() {
			@Override
			public void run() {
				String lastTitle = "";
				String newTitle;
				
				while (true) {
					newTitle = getSongInfo();
					if (!lastTitle.equals(newTitle)) {
						lastTitle = newTitle;
						listener.stateChanged(newTitle);
						listener.coverArtChanged(getCoverArt());
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// Ignore
					}
				}
			}
		});
	}
	
	public void start() {
		observer.start();
	}
	
	public String getSongInfo() {
		int length = User32.INSTANCE.GetWindowText(hWnd, titleBuffer, 1000);
		// Remove "Spotify" or "Spotify - " part
		if (length == 7) {
			return new String();
		} else {
			return new String(titleBuffer, 10, length - 10);
		}
	}
	
	public BufferedImage getCoverArt() {
		BufferedImage image = ImageUtils.getScreenshot(hWnd);
		int width = image.getWidth();
		int height = image.getHeight();
		
		// Find top
		int top = 0;
		int bottom = height - HEIGHT_OF_CONTROLS;
		for (int i = bottom - 1; i >= 0; i--) {
			if (image.getRGB(0, i) == BORDER_COLOR && image.getRGB(1, i) == BORDER_COLOR) {
				top = i + 1;
				break;
			}
		}
		
		//Find side
		int side = 0;
		int borderY = height - HEIGHT_OF_CONTROLS + 1;
		for (int i = 0; i < width; i++) {
			if (image.getRGB(i, borderY) == MAIN_AREA_COLOR) {
				side = i;
				break;
			}
		}
		
		return image.getSubimage(0, top, side, bottom - top);
	}
	
	public void playPause() {
		sendAppCommand(AppCommand.MEDIA_PLAY_PAUSE);
	}
	
	public void next() {
		sendAppCommand(AppCommand.MEDIA_NEXTTRACK);
	}
	
	public void previous() {
		sendAppCommand(AppCommand.MEDIA_PREVIOUSTRACK);
	}
	
	public void volumeUp() {
		sendAppCommand(AppCommand.VOLUME_UP);
	}
	
	public void volumeDown() {
		sendAppCommand(AppCommand.VOLUME_DOWN);
	}
	
	private void sendAppCommand(int key) {
		User32.INSTANCE.PostMessage(hWnd, WinUserExtra.WM_APPCOMMAND, new WPARAM(0), new LPARAM(key << 16));
	}

}
