package client.model.sideMenu;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import client.util.ClientConstraint;

public class SideMenuUtilidade implements SideMenu {

	// Debug
	private final boolean DEBUG = false;
	
	@Override
	public JPanel inicializaComponentes() {
		if(DEBUG)
			System.out.println("Inicializando SideMenuCliente...");
		
		// Carrega o submenu 
		JPanel sideMenu = carregarSubMenuConteudos();
		sideMenu.setPreferredSize(new Dimension(180,400));

		return sideMenu;
	}
	
	

	/**
	 * Carrega a barra com o menu de conteudos.
	 * @return JPanel com o menu de conteudos.
	 */
	private JPanel carregarSubMenuConteudos(){
		JPanel painel = new JPanel();
		painel.setBackground(Color.white);
		
		JButton btStatusSistema= new JButton("Status Sistema");
		btStatusSistema.setPreferredSize(new Dimension(160,25));
		btStatusSistema.setEnabled(true);
		btStatusSistema.setActionCommand(ClientConstraint.STATUS_SISTEMA);
		btStatusSistema.addActionListener(new OuvinteSideMenu());
		
		JButton btShowUsers = new JButton("Mostrar Usuários");
		btShowUsers.setPreferredSize(new Dimension(160,25));
		btShowUsers.setEnabled(true);
		btShowUsers.setActionCommand(ClientConstraint.SHOW_USERS);
		btShowUsers.addActionListener(new OuvinteSideMenu());
		
		painel.add(btStatusSistema);
		painel.add(btShowUsers);
		return painel;
	}
}
