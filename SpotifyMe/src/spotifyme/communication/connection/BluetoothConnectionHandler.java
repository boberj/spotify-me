package spotifyme.communication.connection;

import java.io.IOException;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import net.java.dev.marge.util.UUIDGenerator;

public class BluetoothConnectionHandler implements ConnectionHandler {
	
	String url;
	
	public BluetoothConnectionHandler(String name) {
		url = "btspp://localhost:" + UUIDGenerator.generate(name) + ";name=" + name;
	}

	@Override
	public void start(final ConnectionListener listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
					StreamConnectionNotifier notifier = (StreamConnectionNotifier) Connector.open(url);
					System.out.println("Bluetooth handler ready");
					while (true) {
						StreamConnection connection = notifier.acceptAndOpen();
						RemoteDevice remoteDevice = RemoteDevice.getRemoteDevice(connection);
						System.out.println("Got connection from " + remoteDevice.getBluetoothAddress());
						try {
							listener.clientConnected(connection.openDataInputStream(), connection.openDataOutputStream());
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
