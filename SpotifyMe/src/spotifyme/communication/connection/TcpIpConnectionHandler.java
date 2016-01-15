package spotifyme.communication.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpIpConnectionHandler implements ConnectionHandler {
	
	private int port;
	
	public TcpIpConnectionHandler(int port) {
		this.port = port;
	}

	@Override
	public void start(final ConnectionListener listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ServerSocket serverSocket = new ServerSocket(port);
					System.out.println("TCP/IP handler ready on port " + port);
					while(true) {
						Socket socket = serverSocket.accept();
						System.out.println("Got connection from " + socket.getInetAddress());
						try {
							listener.clientConnected(new DataInputStream(socket.getInputStream()), new DataOutputStream(socket.getOutputStream()));
						} catch (IOException e) {
							System.out.println("Error occurred when trying to open data connection");
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

}
