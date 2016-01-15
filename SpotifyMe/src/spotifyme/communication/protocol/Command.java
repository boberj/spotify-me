package spotifyme.communication.protocol;

public enum Command {

	PLAY_PAUSE(1),
	NEXT(2),
	PREVIOUS(3),
	VOLUME_UP(4),
	VOLUME_DOWN(5);
	
	private short value;
	
	Command(int value) {
		this.value = (short) value;
	}
	
	public short getValue() {
		return value;
	}
	
	public static Command fromValue(short value) {
		switch (value) {
		case 1: return PLAY_PAUSE;
		case 2: return NEXT;
		case 3: return PREVIOUS;
		case 4: return VOLUME_UP;
		case 5: return VOLUME_DOWN;
		default: assert false;
		}
		
		return null;
	}
	
}
