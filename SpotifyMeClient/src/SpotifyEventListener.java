import javax.microedition.lcdui.Image;


public interface SpotifyEventListener {
	
	public void titleReceived(String title);
	
	public void imageReceived(Image image);

}
