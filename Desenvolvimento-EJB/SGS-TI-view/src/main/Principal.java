package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import client.view.Login;

public class Principal {
	/**
	 * Main
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				try {
					// select Look and Feel
					UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
					Login login = new Login();
					login.setVisible(true);
				
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}
}
