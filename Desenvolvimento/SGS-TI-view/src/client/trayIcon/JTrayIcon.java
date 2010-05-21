package client.trayIcon;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import common.util.Utils;

public class JTrayIcon extends TrayIcon implements Observer{

	private List<PopupTrayIcon> mensagens;
	private Dimension screenSize;
	
	/**
	 * Construtor
	 * @param image
	 * @param tooltip
	 * @param popup
	 */
	public JTrayIcon(Image image, String tooltip, PopupMenu popup) {
		super(image, tooltip, popup);
		mensagens = new ArrayList<PopupTrayIcon>();
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		setScreenSize(size);
	}
	
	@Override
	public void displayMessage(String caption, String text,
			MessageType messageType) {
		
		PopupTrayIcon msg = new PopupTrayIcon(caption, text, getScreenSize(), mensagens.size()+1, this);
		mensagens.add(msg);
	}

	@Override
	public void update(Observable o, Object arg) {
		Utils.printMsg(this.getClass().getName(), "Notificação de " + o.getClass().toString());
	
		if(o.getClass().toString().toLowerCase().contains("observadorfila")){
			displayMessage("Aviso", "Novo chamado", MessageType.INFO);
		}else{
			mensagens.remove(0);
			reposicionaMensagens();
		}
	}
	
	public void reposicionaMensagens(){
		for(PopupTrayIcon msg : getMensagens()){
			
			int x = msg.getPopup().getLocation().x;
			int y = msg.getPopup().getLocation().y + msg.getPopup().getSize().height;
			
			msg.getPopup().setVisible(false);
			msg.getPopup().setLocation(x,y);
			msg.getPopup().setVisible(true);
		}
	}
	
	/*
	 * GETTERs AND SETTERs
	 */
	public Dimension getScreenSize() {
		return screenSize;
	}
	public void setScreenSize(Dimension screenSize) {
		this.screenSize = screenSize;
	}
	public List<PopupTrayIcon> getMensagens() {
		return mensagens;
	}
	public void setMensagens(List<PopupTrayIcon> mensagens) {
		this.mensagens = mensagens;
	}
}
