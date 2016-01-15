package spotifyme.communication.server;

import spotifyme.communication.protocol.Command;

public interface ServerListener {
	
	public void commandReceived(Command command);

}
