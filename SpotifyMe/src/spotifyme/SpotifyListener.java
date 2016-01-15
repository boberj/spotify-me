package spotifyme;

import java.awt.image.BufferedImage;

public interface SpotifyListener {

	public void stateChanged(String newTitle);

	public void coverArtChanged(BufferedImage coverArt);
	
}
