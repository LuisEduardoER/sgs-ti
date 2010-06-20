package client.trayIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SGSTrayIcon extends TrayIcon {
	
	private JFrame msgFrame;
	
	public SGSTrayIcon(Image image, String tooltip, PopupMenu popup) {
		super(image, tooltip, popup);
		
		msgFrame = new JFrame();
		//msgFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
		
	
		int x = 700;
		int y = 600;
		System.out.println(x + ", " + y);
		
		msgFrame.setLocation(x,y);
		msgFrame.setUndecorated(true);
		msgFrame.setVisible(true);
		
		monitorMsg();
	}

	@Override
	public void displayMessage(String caption, String text,
			MessageType messageType) {
		
		RepositorioTrayIconMsg.getInstance()
				.addMsg(new TrayIconMessage(caption, text, messageType));
	
	}
	
	/**
	 * Metodo que criar uma Thread para monitorar o repositório de menssagens
	 */
	private void monitorMsg(){
		new Thread(){
			@Override
			public void run() {
				while(!Thread.interrupted()){
					TrayIconMessage msg = RepositorioTrayIconMsg.getInstance().getMsg();
					showMsg(msg);
				}
			}
		}.start();
	}

	private void showMsg(TrayIconMessage msg) {
		JPanel painel = new JPanel(new BorderLayout());
		painel.setSize(100, 100);
		painel.setBackground(Color.WHITE);
		
		JTextField title = new JTextField(msg.getCaption());
		JTextField texto = new JTextField(msg.getText());

		painel.add(title, BorderLayout.NORTH);
		painel.add(texto, BorderLayout.CENTER);
		
		painel.setVisible(true);
		msgFrame.add(painel);
	}

	public JFrame getMsgFrame() {
		return msgFrame;
	}

	public void setMsgFrame(JFrame msgFrame) {
		this.msgFrame = msgFrame;
	}
}
