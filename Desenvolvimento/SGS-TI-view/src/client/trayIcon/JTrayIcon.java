package client.trayIcon;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.util.Observable;
import java.util.Observer;

import common.util.Utils;

public class JTrayIcon extends TrayIcon implements Observer{

	private int numMsg;
	private Dimension screenSize;
	
	/**
	 * Construtor
	 * @param image
	 * @param tooltip
	 * @param popup
	 */
	public JTrayIcon(Image image, String tooltip, PopupMenu popup) {
		super(image, tooltip, popup);
		setNumMsg(0);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		setScreenSize(size);
	}
	
	@Override
	public void displayMessage(String caption, String text,
			MessageType messageType) {
		
		setNumMsg(getNumMsg()+1);
		new PopupTrayIcon(caption, text, getScreenSize(), getNumMsg(), this);		
	}
	
	
	/*
	 * GETTERs AND SETTERs
	 */
	public int getNumMsg() {
		return numMsg;
	}

	public void setNumMsg(int numMsg) {
		this.numMsg = numMsg;
	}

	public Dimension getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(Dimension screenSize) {
		this.screenSize = screenSize;
	}

	@Override
	public void update(Observable o, Object arg) {
		Utils.printMsg(this.getClass().getName(), "Notificação de " + o.getClass().toString());
		if(o.getClass().toString().toLowerCase().contains("observadorfila")){
			displayMessage("Aviso", "Novo chamado", MessageType.INFO);
		}else
			setNumMsg(getNumMsg()-1);
	}
}
