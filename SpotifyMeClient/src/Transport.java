import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.bluetooth.LocalDevice;
import javax.bluetooth.ServiceRecord;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import net.java.dev.marge.util.UUIDGenerator;

import communication.Packet;


public class Transport {
	
	private TransportListener listener;
	private DataInputStream in;
	private DataOutputStream out;
	private StreamConnection connection;
	
	public static Transport connect(String name, TransportListener listener) throws IOException {
		String url = LocalDevice.getLocalDevice().getDiscoveryAgent().selectService(UUIDGenerator.generate(name), ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
		StreamConnection connection = (StreamConnection) Connector.open(url);
		
		return new Transport(connection, listener);
	}
	
	private Transport(StreamConnection connection, final TransportListener listener) throws IOException {
		this.connection = connection;
		this.listener = listener;
		this.in = connection.openDataInputStream();
		this.out = connection.openDataOutputStream();
		
		new Thread(new Runnable() {
			public void run() {
				while(true) {
					try {
						Packet.Type type = Packet.Type.fromValue(in.readShort());
						int size = in.readInt();
						byte[] data = new byte[size];
						in.readFully(data);
						listener.packetReceived(Packet.fromType(type, data));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						return; 
					}
				}
			}
		}).start();
	}
	
	public void send(Packet packet) {
		try {
			out.writeShort(packet.getType().getValue());
			out.writeInt(packet.getSize());
			out.write(packet.getData());
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	public void close() {
		try {
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
	}

}
