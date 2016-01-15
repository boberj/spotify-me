import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.microedition.lcdui.Image;

import communication.CommandPacket;
import communication.ImagePacket;
import communication.Packet;
import communication.TitlePacket;


public class SpotifyService implements TransportListener {
	
	private Transport transport;
	private SpotifyEventListener listener;
	
	private static final byte[] NEXT = "next".getBytes();
	private static final byte[] PREVIOUS = "previous".getBytes();
	private static final byte[] VOLUME_UP = "volumeup".getBytes();
	private static final byte[] VOLUME_DOWN = "volumedown".getBytes();
	private static final byte[] PLAY_PAUSE = "playpause".getBytes();
	

	public SpotifyService(SpotifyEventListener listener) {
		this.listener = listener;
	}
	
	public void connect() throws IOException {
		transport = Transport.connect("SpotifyMe", this);
	}
	
	public void disconnect() {
		transport.close();
		transport = null;
	}
	
	public void playPause() {
		if (transport != null) {
			transport.send(new CommandPacket(PLAY_PAUSE));
		}
	}
	
	public void next() {
		if (transport != null) {
			transport.send(new CommandPacket(NEXT));
		}
	}
	
	public void previous() {
		if (transport != null) {
			transport.send(new CommandPacket(PREVIOUS));
		}
	}
	
	public void volumeUp() {
		if (transport != null) {
			transport.send(new CommandPacket(VOLUME_UP));
		}
	}
	
	public void volumeDown() {
		if (transport != null) {
			transport.send(new CommandPacket(VOLUME_DOWN));
		}
	}

	public void packetReceived(Packet packet) {
		if (packet instanceof TitlePacket) {
			listener.titleReceived(((TitlePacket) packet).getTitle());
		} else if (packet instanceof ImagePacket) {
			listener.imageReceived(((ImagePacket) packet).getImage());
		}
	}
	
}
