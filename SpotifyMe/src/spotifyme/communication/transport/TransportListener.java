package spotifyme.communication.transport;
import java.io.IOException;

import spotifyme.communication.protocol.Packet;


public interface TransportListener {
	
	public void packetReceived(Packet packet);
	
	public void transportError(IOException e);

}
