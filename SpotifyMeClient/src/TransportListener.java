import communication.Packet;


public interface TransportListener {
	
	public void packetReceived(Packet packet);

}
