package communication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CapabilityPacket extends Packet {
	
	private short imageAreaWidth;
	private short imageAreaHeight;

	public CapabilityPacket(byte[] data) {
		super(Type.CAPABILITY, data);
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
		try {
			imageAreaWidth = in.readShort();
			imageAreaHeight = in.readShort();
		} catch (IOException e) {
		}
	}
	
	public CapabilityPacket(short imageAreaWidth, short imageAreaHeight) {
		super(Type.CAPABILITY, null);
		this.imageAreaWidth = imageAreaWidth;
		this.imageAreaHeight = imageAreaHeight;
		
		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
		DataOutputStream dataBuffer = new DataOutputStream(byteBuffer);
		try {
			dataBuffer.writeShort(imageAreaWidth);
			dataBuffer.writeShort(imageAreaHeight);
		} catch (IOException e) {
		}
		data = byteBuffer.toByteArray();
	}
	
	public int getImageAreaWidth() {
		return imageAreaWidth;
	}
	
	public int getImageAreaHeight() {
		return imageAreaHeight;
	}

}
