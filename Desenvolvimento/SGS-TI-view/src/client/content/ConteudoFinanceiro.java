package client.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import client.view.Conteudo;
import client.view.FabricaConteudo;

public class ConteudoFinanceiro implements Conteudo {
	// Debug
	private final boolean DEBUG = false;
	
	// Constantes
	private final String CONTEUDO_INICIAL = "CONTEUDO_INICIAL";
	
	// Variavel que contem o sub-conteudo exibido
	private JPanel subConteudo; 
	
	@Override
	public JPanel inicializaComponentes() {
		if(DEBUG)
			System.out.println("Inicializando CONTEUDO_FINANCEIRO");
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(5,5));
		
		// Carrega o submenu 
		JPanel subMenu = carregarSubMenuConteudos();
		subMenu.setPreferredSize(new Dimension(180,400));
		
		// Fabrica o sub-conteudo
		Conteudo novoConteudo = FabricaConteudo.obterConteudo(CONTEUDO_INICIAL);
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
		
		JButton btVenda = new JButton("Registrar Venda");
		btVenda.setPreferredSize(new Dimension(160,25));
		btVenda.setEnabled(false);
		
		JButton btControleCaixa = new JButton("Controle de Caixa");
		btControleCaixa.setPreferredSize(new Dimension(160,25));
		btControleCaixa.setEnabled(false);
		
		JButton btEstatisticas = new JButton("Estatisticas");
		btEstatisticas.setPreferredSize(new Dimension(160,25));
		btEstatisticas.setEnabled(false);
		
		painel.add(btVenda);
		painel.add(btControleCaixa);
		painel.add(btEstatisticas);
		
		return painel;
	}
}
