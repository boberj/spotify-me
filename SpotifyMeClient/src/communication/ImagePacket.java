package communication;

import javax.microedition.lcdui.Image;

public class ImagePacket extends Packet {

	public ImagePacket(byte[] data) {
		super(Type.IMAGE, data);
	}
	
	public Image getImage() {
		System.gc();
		return Image.createImage(data, 0, data.length);
	}

}
