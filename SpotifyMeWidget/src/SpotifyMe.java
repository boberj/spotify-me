import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

import spotifyme.communication.protocol.CapabilityPacket;
import spotifyme.communication.protocol.ImagePacket;
import spotifyme.communication.protocol.Packet;
import spotifyme.communication.protocol.TitlePacket;
import spotifyme.communication.transport.TcpTransport;
import spotifyme.communication.transport.TransportListener;



public class SpotifyMe extends JFrame implements TransportListener, ComponentListener {
	
	private static final long serialVersionUID = 1L;
	
	BufferedImage image;
	TcpTransport transport;
	
	public SpotifyMe() {
		try {
			transport = TcpTransport.connect("localhost", 7768, this);
			transport.send(new CapabilityPacket(250, 250));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		buildUI();
	}

	private void buildUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(300, 300));
		addComponentListener(this);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		new SpotifyMe();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        getRootPane().getGraphics().drawImage(image, 0, 0, null);
    }

	@Override
	public void packetReceived(Packet packet) {
		if (packet instanceof TitlePacket) {
			setTitle(((TitlePacket) packet).getTitle());
		} else if (packet instanceof ImagePacket) {
			image = ((ImagePacket) packet).getImage();
			repaint();
		}
	}

	@Override
	public void transportError(IOException e) {
		e.printStackTrace();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		System.out.println(e);
		try {
			transport.send(new CapabilityPacket(e.getComponent().getWidth(), e.getComponent().getHeight()));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	


}
