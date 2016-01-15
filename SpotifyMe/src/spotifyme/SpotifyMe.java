package spotifyme;
import java.awt.image.BufferedImage;

import spotifyme.communication.connection.BluetoothConnectionHandler;
import spotifyme.communication.connection.TcpIpConnectionHandler;
import spotifyme.communication.protocol.Command;
import spotifyme.communication.protocol.ImagePacket;
import spotifyme.communication.protocol.TitlePacket;
import spotifyme.communication.server.Server;
import spotifyme.communication.server.ServerListener;

public class SpotifyMe implements ServerListener, SpotifyListener {

	private Server server;
	private SpotifyService spotifyService;

	public static void main(String[] args) throws InterruptedException {
		new SpotifyMe().start();
	}

	public SpotifyMe() {
		try {
			spotifyService = new SpotifyService(this);
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	private void start() {
		server = new Server(this);
		server.addConnectionHandler(new BluetoothConnectionHandler("SpotifyMe"));
		server.addConnectionHandler(new TcpIpConnectionHandler(7768));
		server.start();
		spotifyService.start();
	}
	
	@Override
	public void commandReceived(Command command) {
		if (command == Command.PLAY_PAUSE) {
			spotifyService.playPause();
		} else if (command == Command.NEXT) {
			spotifyService.next();
		} else if (command == Command.PREVIOUS) {
			spotifyService.previous();
		} else if (command == Command.VOLUME_UP) {
			spotifyService.volumeUp();
		} else if (command == Command.VOLUME_DOWN) {
			spotifyService.volumeDown();
		}
	}

	@Override
	public void stateChanged(String newTitle) {
		if (server != null) {
			System.out.println(newTitle);
			server.send(new TitlePacket(newTitle));
		}
	}

	@Override
	public void coverArtChanged(BufferedImage coverArt) {
		if (server != null) {
			server.send(new ImagePacket(coverArt));
		}
		
	}

}