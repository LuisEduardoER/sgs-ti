package client.model.sideMenu;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

import client.util.ClientConstraint;

public class SideMenuChamado implements SideMenu {
	// Debug
	private final boolean DEBUG = false;
	
	@Override
	public JPanel inicializaComponentes() {
		if(DEBUG)
			System.out.println("Inicializando SideMenuChamado...");
		
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
		
		JButton btCadastrarChamado = new JButton("Cadastrar Chamado");
		btCadastrarChamado.setPreferredSize(new Dimension(160,25));
		btCadastrarChamado.setEnabled(false);
		
		JButton btListarChamado = new JButton("Listar Chamado");
		btListarChamado.setPreferredSize(new Dimension(160,25));
		btListarChamado.setEnabled(true);
		btListarChamado.setActionCommand(ClientConstraint.LISTAR_CHAMADOS);
		btListarChamado.addActionListener(new OuvinteSideMenu());
		
		JButton btAlterarChamado = new JButton("Editar Chamado");
		btAlterarChamado.setPreferredSize(new Dimension(160,25));
		btAlterarChamado.setEnabled(true);
		btAlterarChamado.setActionCommand(ClientConstraint.EDITAR_CHAMADOS);
		btAlterarChamado.addActionListener(new OuvinteSideMenu());
		
		painel.add(btCadastrarChamado);
		painel.add(btListarChamado);
		painel.add(btAlterarChamado);
		
		return painel;
	}
}
