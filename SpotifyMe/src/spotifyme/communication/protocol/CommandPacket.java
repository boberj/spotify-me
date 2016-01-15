package spotifyme.communication.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CommandPacket extends Packet {

	public static final CommandPacket PLAY_PAUSE = new CommandPacket(Command.PLAY_PAUSE);
	public static final CommandPacket NEXT = new CommandPacket(Command.NEXT);
	public static final CommandPacket PREVIOUS = new CommandPacket(Command.PREVIOUS);
	public static final CommandPacket VOLUME_UP = new CommandPacket(Command.VOLUME_UP);
	public static final CommandPacket VOLUME_DOWN = new CommandPacket(Command.VOLUME_DOWN);
	
	Command command;
	
	private CommandPacket(Command command) {
		super(Type.COMMAND);
		
		this.command = command;
	}
	
	public Command getCommand() {
		return command;
	}
	
	protected static CommandPacket deserialize(DataInputStream in) throws IOException {
		return fromValue(Command.fromValue(in.readShort()));
	}
	
	private static CommandPacket fromValue(Command command) {
		switch (command) {
		case PLAY_PAUSE: return PLAY_PAUSE;
		case NEXT: return NEXT;
		case PREVIOUS: return PREVIOUS;
		case VOLUME_UP: return VOLUME_UP;
		case VOLUME_DOWN: return VOLUME_DOWN;
		default: assert false; return null;
		}
	}

	@Override
	protected void serialize(DataOutputStream out) throws IOException {
		out.writeShort(command.getValue());
	}
	
}
