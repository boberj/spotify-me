package spotifyme;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;


public class ImageUtils {

	public static BufferedImage resize(BufferedImage image, int maxWidth, int maxHeight) {
		int width = image.getWidth();
		int height = image.getHeight();
		float widthFactor = maxWidth / (float)width;
		float heightFactor = maxHeight / (float)height;
		float factor = Math.min(widthFactor, heightFactor);
		int newWidth = (int)(width * factor);
		int newHeight = (int)(height * factor);
		
		BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		
		g.drawImage(image, 0, 0, newWidth, newHeight, 0, 0, width, height, null);
		g.dispose();
		
		return resizedImage;
	}
	
}
