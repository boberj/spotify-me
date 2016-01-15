package spotifyme.communication.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import spotifyme.communication.protocol.ImagePacket;
import spotifyme.communication.protocol.Packet;
import spotifyme.communication.transport.Transport;
import spotifyme.communication.transport.TransportListener;


public class Client implements TransportListener {
	
	private DataInputStream in;
	private DataOutputStream out;
	
	private ClientListener clientListener;
	
	private Transport transport;

	private int imageAreaWidth;
	private int imageAreaHeight;
	
	public Client(final DataInputStream in, final DataOutputStream out, final ClientListener clientListener) {
		this.in = in;
		this.out = out;
		this.clientListener = clientListener;
		this.transport = new Transport(in, out, this);
	}
	
	public void disconnect() {
		try {
			in.close();
		} catch (IOException e) {
			// Ignore
		}
		try {
			out.close();
		} catch (IOException e) {
			// Ignore
		}
		clientListener.disconnected(this);
	}
	
	public void setImageAreaSize(int width, int height) {
		imageAreaWidth = width;
		imageAreaHeight = height;
	}
	
	public void send(Packet packet) throws IOException {
		if (packet instanceof ImagePacket) {
			send((ImagePacket) packet);
		} else {
			transport.send(packet);
		}
	}
	
	public void send(ImagePacket packet) throws IOException {
		if (imageAreaWidth > 0 && imageAreaHeight > 0) {
			transport.send(packet.resize(imageAreaWidth, imageAreaHeight));
			System.out.println("Resizing to " + imageAreaWidth + "x" + imageAreaHeight);
		}
	}


	@Override
	public void packetReceived(Packet packet) {
		clientListener.packetReceived(packet, this);
		
	}

	@Override
	public void transportError(IOException e) {
		disconnect();
	}
	

}
