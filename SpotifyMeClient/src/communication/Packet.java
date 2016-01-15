package communication;

public class Packet {
	
	public static class Type {
		public static final Type COMMAND = new Type(1);
		public static final Type TITLE = new Type(2);
		public static final Type IMAGE = new Type(3);
		public static final Type CAPABILITY = new Type(4);
		
		private short value;
		
		private Type(int value) {
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
			default: throw new Error();
			}
		}
		
	}
	
	protected byte[] data;
	private Type type;
	
	public Packet(Type type, byte[] data) {
		this.type = type;
		this.data = data;
	}
	
	public Type getType() {
		return type;
	}
	
	public int getSize() {
		return data.length;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public static Packet fromType(Type type, byte[] data) {
		if (type == Type.COMMAND) {
			return new CommandPacket(data);
		} else if (type == Type.TITLE) {
			return new TitlePacket(data);
		} else if (type == Type.IMAGE) {
			return new ImagePacket(data);
		} else if (type == Type.CAPABILITY) {
			return new CapabilityPacket(data);
		} else {
			throw new Error();
		}
	}

}
