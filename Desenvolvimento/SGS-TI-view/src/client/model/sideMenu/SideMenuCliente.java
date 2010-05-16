package client.model.sideMenu;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SideMenuCliente implements SideMenu {
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
		
		JButton btListarClientes = new JButton("Listar Clientes");
		btListarClientes.setPreferredSize(new Dimension(160,25));
		btListarClientes.setEnabled(false);
//		btListarClientes.setActionCommand(ClientConstraint.LISTAR_CHAMADOS);
//		btListarClientes.addActionListener(new OuvinteSideMenu());
		
		JButton btCadastrarCliente = new JButton("Cadastrar Cliente");
		btCadastrarCliente.setPreferredSize(new Dimension(160,25));
		btCadastrarCliente.setEnabled(false);
		
		JButton btAlterarCliente = new JButton("Alterar Cliente");
		btAlterarCliente.setPreferredSize(new Dimension(160,25));
		btAlterarCliente.setEnabled(false);
		
		painel.add(btListarClientes);
		painel.add(btCadastrarCliente);
		painel.add(btAlterarCliente);
		
		return painel;
	}
}
