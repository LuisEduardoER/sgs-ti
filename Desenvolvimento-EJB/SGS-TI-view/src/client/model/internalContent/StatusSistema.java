package client.model.internalContent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;
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
import client.view.MainView;


public class StatusSistema  implements InternalContent
{
	private JInternalFrame jif;
	private JPanel form;
	private JLabel usersOnline;	
	private JLabel numUser;

	public JInternalFrame getInternalContent(Object param) {
		jif = new JInternalFrame();
		jif.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		jif.setBackground(Color.WHITE);
		jif.setSize(200,200);
		jif.setLocation(100, 100);
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
		
		usersOnline = new JLabel("Usu�rios Online: ");		
		numUser = new JLabel("N/A");
		
		JButton btAtualizar = new JButton("Atualizar");
		btAtualizar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					if(!Utils.isEmptyCollection(ClientController.getInstance().getUsuariosAutenticados())){
						numUser.setText(String.valueOf(ClientController.getInstance().getUsuariosAutenticados().size()));
					}
				}catch(RemoteException ex){
					MainView.getInstance().mostrarMensagemErroRemoto();
				}
			}
		});
		btAtualizar.setVisible(true);
		
		JButton btKillAll = new JButton("Kill All");
		btKillAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					// Mata todos os usu�rios menos ele mesmo
					Usuario user = ClientController.getInstance().getUsuario();
					LinkedList<UsuarioAutenticado> ua = ClientController.getInstance().getUsuariosAutenticados();
					
					for(UsuarioAutenticado usuario : ua){
						if(!usuario.getUsuario().equals(user)){
							try {
								usuario.getObservador().encerrarClient();
							} catch (RemoteException e1) {
								// possivel ocorrer uma RE caso o usu�rio ja tenha sido removido da sessao
								// ignorar
							}
						}
					}
					
						
				}catch (RemoteException ex) {
					MainView.getInstance().mostrarMensagemErroRemoto();
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
