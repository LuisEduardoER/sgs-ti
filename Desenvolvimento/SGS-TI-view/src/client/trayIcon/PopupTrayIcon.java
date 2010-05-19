package client.trayIcon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JDialog;
import javax.swing.JTextField;
import common.util.Utils;

public class PopupTrayIcon extends Observable {
	private static final long serialVersionUID = 1L;
	private String caption;
	private String text;
	private JDialog popup;
	private long timeToLive = 4000;
	
	public PopupTrayIcon(String caption, String msg, Dimension screenSize, int numMsg, Observer obs){
		addObserver(obs);
	    popup = new JDialog();
		popup.setSize(200, 80);
		popup.setLayout(new BorderLayout());
		popup.setTitle(caption);
		popup.setUndecorated(true);
		popup.setAlwaysOnTop(true);
		popup.add(new JTextField(msg));
		
		int x = screenSize.width - 10 - popup.getSize().width;
		int y = screenSize.height - 60 - (popup.getSize().height * numMsg -3);
		Utils.printMsg(this.getClass().getName(), "show msg - x: " + x + ", y: " + y);
		
		popup.setLocation(x, y);
		popup.setVisible(true);
		
		// Thread interna que mata a msg.
		new Thread(){
			public void run() {
				try {
					Thread.sleep(timeToLive);
					popup.setVisible(false);
					popup.invalidate();
					setChanged();
					notifyObservers();
					
				} catch (InterruptedException e) {
					popup.setVisible(false);
					popup.invalidate();
				}
			};
		}.start();
	}
	
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
