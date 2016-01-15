package spotifyme.communication.transport;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class TcpTransport extends Transport {
	
	private Socket socket;
	
	public static TcpTransport connect(String host, int port, TransportListener listener) throws IOException {
		Socket socket = new Socket(host, port);
		
		return new TcpTransport(socket, listener);
	}
	
	private TcpTransport(Socket socket, final TransportListener listener) throws IOException {
		super(new DataInputStream(socket.getInputStream()), new DataOutputStream(socket.getOutputStream()), listener);
		this.socket = socket;
	}
	

	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

}
