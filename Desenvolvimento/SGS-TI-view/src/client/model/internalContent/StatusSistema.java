package client.model.internalContent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import common.entity.Usuario;
import common.entity.UsuarioAutenticado;
import common.util.Utils;
import client.controller.ClientController;
import client.util.SpringUtilities;


public class StatusSistema  implements InternalContent
{
	private JInternalFrame jif;
	private JPanel form;
	private JLabel usersOnline;	
	private JLabel numUser;

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
		
		usersOnline = new JLabel("Usuários Online: ");		
		numUser = new JLabel("N/A");
		
		JButton btAtualizar = new JButton("Atualizar");
		btAtualizar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HashSet<UsuarioAutenticado> ua = ClientController.getInstance().getStatusSistema();
				if(!Utils.isEmptyCollection(ua)){
					numUser.setText(String.valueOf(ua.size()));
				}
			}
		});
		btAtualizar.setVisible(true);
		
		JButton btKillAll = new JButton("Kill All");
		btKillAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Usuario user = ClientController.getInstance().getUsuario();
				HashSet<UsuarioAutenticado> ua = ClientController.getInstance().getStatusSistema();
				Iterator<UsuarioAutenticado> it = ua.iterator();
				while(it.hasNext()){
					UsuarioAutenticado usuario = it.next();
					if(!usuario.getUsuario().equals(user)){
						try {
							usuario.getObservador().encerrarClient();
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btAtualizar.setVisible(true);
				
		form.add(usersOnline);
		form.add(numUser);
		form.add(btAtualizar);
		form.add(btKillAll);
		
		SpringUtilities.makeCompactGrid(form,
	            2, 2,		//rows, cols
	            6, 6,     	//initX, initY
	            6, 6);      //xPad, yPad
				
		jif.add(form);
		
	}

}
