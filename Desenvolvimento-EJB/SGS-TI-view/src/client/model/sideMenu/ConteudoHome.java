package client.model.sideMenu;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;



/**
 * Classe que implementa a interface conteudo. Carrega o conteudo inicial. 
 *
 */
public class ConteudoHome implements SideMenu {
	/**
	 * Carrega o conteudo.
	 */
	@Override
	public JPanel inicializaComponentes() {

		JPanel painel = new JPanel();
		painel.setBackground(Color.WHITE);
		
		JButton botaoVane = new JButton("Teste");
		
		painel.add(botaoVane);
		return painel;
	}	
}
