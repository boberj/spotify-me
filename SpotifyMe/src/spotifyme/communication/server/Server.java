package spotifyme.communication.server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import spotifyme.communication.client.Client;
import spotifyme.communication.client.ClientListener;
import spotifyme.communication.connection.ConnectionHandler;
import spotifyme.communication.connection.ConnectionListener;
import spotifyme.communication.protocol.CapabilityPacket;
import spotifyme.communication.protocol.CommandPacket;
import spotifyme.communication.protocol.Packet;


public class Server implements ClientListener, ConnectionListener {
	
	private List<Client> clients = Collections.synchronizedList(new LinkedList<Client>());
	private List<ConnectionHandler> connectionHandlers = new LinkedList<ConnectionHandler>();
	private BlockingQueue<Packet> outQueue = new LinkedBlockingQueue<Packet>();
	private BlockingQueue<Packet> inQueue = new LinkedBlockingQueue<Packet>();

	
	public Server(final ServerListener listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Packet packet = inQueue.take();
						if (packet instanceof CommandPacket) {
							listener.commandReceived(((CommandPacket) packet).getCommand());
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Packet packet = outQueue.take();
						synchronized (clients) {
							for (Client client : clients) {
								try {
									client.send(packet);
								} catch (IOException e) {
									client.disconnect();
								}
							}
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}
				}
			}
		}).start();
	}
	
	public void start() {
		for (ConnectionHandler connectionHandler : connectionHandlers) {
			connectionHandler.start(this);
		}
		
	}
	
	public void addConnectionHandler(ConnectionHandler connectionHandler) {
		connectionHandlers.add(connectionHandler);
	}
	
	public void send(Packet packet) {
		outQueue.add(packet);
	}
	
	private void addClient(Client client) {
		clients.add(client);
	}

	@Override
	public void packetReceived(Packet packet, Client client) {
		if (packet instanceof CommandPacket) {
			inQueue.add(packet);
		} else if (packet instanceof CapabilityPacket) {
			CapabilityPacket capability = (CapabilityPacket) packet;
			client.setImageAreaSize(capability.getImageAreaWidth(), capability.getImageAreaHeight());
		} else {
			System.out.println("Unknown packet received");
		}
	}

	@Override
	public void disconnected(Client client) {
		System.out.println("Client disconnected");
		clients.remove(client);
	}

	@Override
	public void clientConnected(DataInputStream in, DataOutputStream out) {
		addClient(new Client(in, out, this));
	}

}
