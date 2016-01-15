package spotifyme.communication.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TitlePacket extends Packet {
	
	private String title;
	
	protected static TitlePacket deserialize(DataInputStream in) throws IOException {
		return new TitlePacket(in.readUTF());
		//new String(in, "UTF-8")
	}

	public TitlePacket(String title) {
		super(Type.TITLE);
		
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	@Override
	protected void serialize(DataOutputStream out) throws IOException {
		out.writeUTF(title);
	}

}
