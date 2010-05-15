package main;

import javax.swing.SwingUtilities;
import client.view.Login;

public class Principal {
	/**
	 * Main
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Login login = new Login();
				login.setVisible(true);
			}
		});
	}
}
