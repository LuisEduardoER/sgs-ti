package client;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

public class Modal extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Modal() {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JTextField texto =  new JTextField("Oi eu sou um modal!!");
		JButton ok = new JButton("OK");	
		
		add(texto,BorderLayout.CENTER);
		add(ok,BorderLayout.SOUTH);
	}

	public List<String> pegarDados(){
		List<String> lista = new ArrayList<String>();
		lista.add("Pegar dados!");
		lista.add("Formulario!");
		return lista;
	};
}
