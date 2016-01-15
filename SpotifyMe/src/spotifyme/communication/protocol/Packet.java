package spotifyme.communication.protocol;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Packet {
	
	protected enum Type {
		COMMAND(1),
		TITLE(2),
		IMAGE(3),
		CAPABILITY(4);
		
		private short value;
		
		Type(int value) {
			this.value = (short) value;
		}
		
		public short getValue() {
			return value;
		}
		
		public static Type fromValue(short value) {
			switch (value) {
			case 1: return COMMAND;
			case 2: return TITLE;
			case 3: return IMAGE;
			case 4: return CAPABILITY;
			default: assert false;
			}
			
			return null;
		}
		
	}
	
	private Type type;
	
	public Packet(Type type) {
		this.type = type;
	}
	
	public static Packet deserialize(byte[] data) throws IOException {
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
		Packet.Type type = Packet.Type.fromValue(in.readShort());
		
		System.out.println("Received " + type + " packet (" + data.length + " bytes)");
		
		switch (type) {
		case COMMAND: return CommandPacket.deserialize(in);
		case TITLE: return TitlePacket.deserialize(in);
		case IMAGE: return ImagePacket.deseralize(in);
		case CAPABILITY: return CapabilityPacket.deserialize(in);
		default: assert false; return null;
		}
	}
	
	protected abstract void serialize(DataOutputStream out) throws IOException;
	
	public byte[] serialize() throws IOException {
		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
		DataOutputStream dataBuffer = new DataOutputStream(byteBuffer);
		
		dataBuffer.writeShort(type.getValue());
		serialize(dataBuffer);
		
		return byteBuffer.toByteArray();
	}

}
