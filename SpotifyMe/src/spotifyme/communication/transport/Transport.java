package spotifyme.communication.transport;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import spotifyme.communication.protocol.Packet;

public class Transport {
	
	final DataInputStream in;
	final DataOutputStream out;
	
	public Transport(final DataInputStream in, DataOutputStream out, final TransportListener listener) {
		this.in = in;
		this.out = out;
		
		new Thread(new Runnable() {
			public void run() {
				while(true) {
					try {
						int size = in.readInt();
						byte[] data = new byte[size];
						in.readFully(data);
						listener.packetReceived(Packet.deserialize(data));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						listener.transportError(e);
						return; 
					}
				}
			}
		}).start();
	}
	
	public void send(Packet packet) throws IOException {
		byte[] data = packet.serialize();
		
		System.out.print("Sending " + data.length + " bytes...");
		
		out.writeInt(data.length);
		out.write(data);
		out.flush();
		
		System.out.println("done");
	}

}
