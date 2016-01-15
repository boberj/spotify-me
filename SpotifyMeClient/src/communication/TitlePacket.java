package communication;

import java.io.UnsupportedEncodingException;

public class TitlePacket extends Packet {

	public TitlePacket(byte[] data) {
		super(Type.TITLE, data);
	}
	
	public String getTitle() {
		try {
			return new String(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

}
