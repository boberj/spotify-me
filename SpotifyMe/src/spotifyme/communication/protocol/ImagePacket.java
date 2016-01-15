package spotifyme.communication.protocol;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import spotifyme.ImageUtils;

public class ImagePacket extends Packet {
	
	private BufferedImage image;
	
	public ImagePacket(BufferedImage image) {
		super(Type.IMAGE);
		
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public ImagePacket resize(int maxWidth, int maxHeight) {
		return new ImagePacket(ImageUtils.resize(image, maxWidth, maxHeight));
	}

	protected static ImagePacket deseralize(DataInputStream in) throws IOException {
		return new ImagePacket(ImageIO.read(in));
	}

	@Override
	protected void serialize(DataOutputStream out) throws IOException {
		ImageIO.write(image, "png", out);
	}

}
