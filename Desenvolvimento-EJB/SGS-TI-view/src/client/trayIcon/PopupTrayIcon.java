package client.trayIcon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JDialog;
import javax.swing.JLabel;
import common.util.SystemConstant;
import common.util.Utils;

public class PopupTrayIcon extends Observable {
	private static final long serialVersionUID = 1L;
	private String caption;
	private String text;
	private JDialog popup;
	private Thread killer;  
	
	/**
	 * Construtor
	 * 
	 * @param caption 
	 * @param msg
	 * @param screenSize
	 * @param numMsg
	 * @param obs
	 */
	public PopupTrayIcon(String caption, String msg, Dimension screenSize, int numMsg, Observer obs){
		addObserver(obs);
	    popup = new JDialog();
	    popup.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    popup.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent arg0) {
	    		killer.interrupt();
	    		fecharPopup();
	    	}
		});
	    
		popup.setSize(200, 80);
		popup.setLayout(new BorderLayout());
		popup.setTitle(caption);
		popup.setUndecorated(true);
		popup.setAlwaysOnTop(true);
		JLabel texto = new JLabel(msg);
		texto.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		texto.setAlignmentY(JLabel.CENTER);
		popup.add(texto);
		
		int x = screenSize.width - 10 - popup.getSize().width;
		int y = screenSize.height - 60 - (popup.getSize().height * numMsg);
		Utils.printMsg(this.getClass().getName(), "show msg - x: " + x + ", y: " + y);
		
		popup.setLocation(x, y);
		popup.setVisible(true);
		
		// Thread interna que mata a msg.
		killer = new Thread(){
			public void run() {
				try {
					Thread.sleep(SystemConstant.TIME_TO_LIVE_MSG_TRAY);
					fecharPopup();
					
				} catch (InterruptedException e) {
					// nao faz nada.
				}
			};
		};
		killer.start();
	}	
	
	/**
	 * Metodo que fecha a janela.
	 */
	public void fecharPopup(){
		if(popup != null){
			popup.setVisible(false);
			popup.dispose();
			setChanged();
			notifyObservers(this);
		}
	}
	
	/*
	 * GETTERs AND SETTERs
	 */
	public String getCaption() {
		return caption;
	}
	public JDialog getPopup() {
		return popup;
	}
	public void setPopup(JDialog popup) {
		this.popup = popup;
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
