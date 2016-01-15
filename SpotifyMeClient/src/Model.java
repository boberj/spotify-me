import javax.microedition.lcdui.Image;

public class Model {
	
	public static class Status {
	    public static final Status CONNECTING = new Status();
	    public static final Status CONNECTED = new Status();
	    public static final Status DISCONNECTED = new Status();
	}
	
	private ChangeEventListener changeEventListener;
	private Status status = Status.DISCONNECTED;
	private String title = "";
	public Image coverArt = Image.createRGBImage(new int[] { 0 }, 1, 1, false);
	
	public Model() {
	}

	public void setChangeEventListener(ChangeEventListener listener) {
		this.changeEventListener = listener;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
		fireChanged();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		fireChanged();
	}
	
	public Image getCoverArt() {
		return coverArt;
	}

	public void setCoverArt(Image image) {
		this.coverArt = image;
		fireChanged();
	}
	
	private void fireChanged() {
		if (changeEventListener != null) {
			changeEventListener.onChange();
		}
	}
	
}
