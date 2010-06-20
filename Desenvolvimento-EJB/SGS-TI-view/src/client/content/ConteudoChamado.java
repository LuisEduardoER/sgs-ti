package client.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

import client.view.Conteudo;
import client.view.FabricaConteudo;

public class ConteudoChamado implements Conteudo {
	// Debug
	private final boolean DEBUG = false;
	
	// Constantes
	private final String CONTEUDO_CHAMADO = "CONTEUDO_CHAMADO";
	
	// Variavel que contem o sub-conteudo exibido
	private JPanel subConteudo; 
	
	@Override
	public JPanel inicializaComponentes() {
		if(DEBUG)
			System.out.println("Inicializando CONTEUDO_CHAMADO");
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(5,5));
		
		// Carrega o submenu 
		JPanel subMenu = carregarSubMenuConteudos();
		subMenu.setPreferredSize(new Dimension(180,400));
		
		// Fabrica o sub-conteudo
		Conteudo novoConteudo = FabricaConteudo.obterConteudo(CONTEUDO_CHAMADO);
		// Carrega o sub-conteudo
		subConteudo = novoConteudo.inicializaComponentes();
		
		content.add(subMenu, BorderLayout.WEST);
		content.add(subConteudo, BorderLayout.CENTER);
		return content;
	}
	
	

	/**
	 * Carrega a barra com o menu de conteudos.
	 * @return JPanel com o menu de conteudos.
	 */
	private JPanel carregarSubMenuConteudos(){
		JPanel painel = new JPanel();
		painel.setBackground(Color.white);
		
		JButton btListarChamado = new JButton("Listar Chamado");
		btListarChamado.setPreferredSize(new Dimension(160,25));
		btListarChamado.setEnabled(false);
		
		
		JButton btCadastrarChamado = new JButton("Cadastrar Chamado");
		btCadastrarChamado.setPreferredSize(new Dimension(160,25));
		btCadastrarChamado.setEnabled(false);
		
		JButton btAlterarChamado = new JButton("Alterar Chamado");
		btAlterarChamado.setPreferredSize(new Dimension(160,25));
		btAlterarChamado.setEnabled(true);
		
		painel.add(btListarChamado);
		painel.add(btCadastrarChamado);
		painel.add(btAlterarChamado);
		
		return painel;
	}
}
