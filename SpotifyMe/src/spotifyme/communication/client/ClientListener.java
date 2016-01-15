package spotifyme.communication.client;
import spotifyme.communication.protocol.Packet;


public interface ClientListener {
	
	public void packetReceived(Packet packet, Client client);
	
	public void disconnected(Client client);

}
