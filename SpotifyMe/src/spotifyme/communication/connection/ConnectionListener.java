package spotifyme.communication.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface ConnectionListener {
	
	public void clientConnected(DataInputStream in, DataOutputStream out);

}
