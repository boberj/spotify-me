package communication;

public class CommandPacket extends Packet {

	public CommandPacket(byte[] data) {
		super(Type.COMMAND, data);
	}
	
}
