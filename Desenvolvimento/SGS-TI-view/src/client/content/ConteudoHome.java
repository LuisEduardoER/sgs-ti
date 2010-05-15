package client.content;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import client.view.Conteudo;
import client.view.ImagePanel;


/**
 * Classe que implementa a interface conteudo. Carrega o conteudo inicial. 
 *
 */
public class ConteudoHome implements Conteudo {
	/**
	 * Carrega o conteudo.
	 */
	@Override
	public JPanel inicializaComponentes() {
		JPanel content = new JPanel();
		content.setLayout(new FlowLayout());
		content.setBackground(Color.white);
		
		// carrega a imagem do LOGO
		Image img = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage("resources/imgs/LOGO_01.png")).getImage();
		
		ImagePanel logo = new ImagePanel(img);
		
		content.add(logo);
		return content;
	}	
}
