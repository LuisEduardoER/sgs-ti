package client.model.internalContent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import common.entity.UsuarioAutenticado;
import client.controller.ClientController;
import client.util.SpringUtilities;


public class ShowUsers  implements InternalContent
{
	private JInternalFrame jif;
	private JPanel form;

	public JInternalFrame getInternalContent() {
		jif = new JInternalFrame();
		jif.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		jif.setBackground(Color.WHITE);
		jif.setPreferredSize(new Dimension(600, 600));
		jif.setClosable(true);
		jif.setResizable(true);	
		jif.setLayout(new BorderLayout());
		inicializar();
		return jif;
	}

	private void inicializar(){
		
		/* 
		 * form 
		 */ 
		form = new JPanel(new SpringLayout());
		
		JLabel usersOnline = new JLabel("Usuários online:");
		form.add(usersOnline);
		
		HashSet<UsuarioAutenticado> ua = ClientController.getInstance().getStatusSistema();
		Iterator<UsuarioAutenticado> it = ua.iterator();
		while(it.hasNext()){
			UsuarioAutenticado usuario = it.next();
			JLabel temp = new JLabel(usuario.getUsuario().getUsername());
			form.add(temp);
		}
		
		
		SpringUtilities.makeCompactGrid(form,
				form.getComponentCount(), 1,		//rows, cols
	            6, 6,     	//initX, initY
	            6, 6);      //xPad, yPad
				
		jif.add(form);
		
	}
	


}
