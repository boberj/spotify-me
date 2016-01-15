package spotifyme.communication.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CapabilityPacket extends Packet {
	
	private int imageAreaWidth;
	private int imageAreaHeight;
	
	protected static CapabilityPacket deserialize(DataInputStream in) throws IOException {
		return new CapabilityPacket(in.readShort(), in.readShort());
	}
	
	public CapabilityPacket(int imageAreaWidth, int imageAreaHeight) {
		super(Type.CAPABILITY);
		
		this.imageAreaWidth = imageAreaWidth;
		this.imageAreaHeight = imageAreaHeight;
	}
	
	public int getImageAreaWidth() {
		return imageAreaWidth;
	}
	
	public int getImageAreaHeight() {
		return imageAreaHeight;
	}

	@Override
	protected void serialize(DataOutputStream out) throws IOException {
		out.writeShort(imageAreaWidth);
		out.writeShort(imageAreaHeight);
	}
	
}
