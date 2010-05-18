package client.model.internalContent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import common.entity.Usuario;
import common.entity.UsuarioAutenticado;
import client.controller.ClientController;
import client.util.SpringUtilities;


public class ShowUsers  implements InternalContent
{
	private JInternalFrame jif;
	private JPanel form;
	private JComboBox acoes;
	private JComboBox jcb;

	public JInternalFrame getInternalContent(Object param) {
		jif = new JInternalFrame("Visualizando usuários");
		jif.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		jif.setBackground(Color.WHITE);
		jif.setSize(300,200);
		jif.setLocation(100, 100);
		jif.setClosable(true);
		jif.setResizable(false);	
		jif.setLayout(new SpringLayout());
		inicializar();
		return jif;
	}

	private void inicializar(){
		
		/* 
		 * form 
		 */ 
		
		jcb = new JComboBox();
		
		
		String [] userActions = {"Kill","Notificar"};
		acoes = new JComboBox(userActions);
		JButton btExecutar = new JButton("Executar");
		btExecutar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(acoes.getSelectedIndex()==0){
					String user = jcb.getSelectedItem().toString();
					HashSet<UsuarioAutenticado> ua = ClientController.getInstance().getStatusSistema();
					Iterator<UsuarioAutenticado> it = ua.iterator();
					while(it.hasNext()){
						UsuarioAutenticado usuario = it.next();
						if(!usuario.getUsuario().equals(user)){
							try {
								usuario.getObservador().encerrarClient();
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
						}
					}
				}else{
					JOptionPane.showMessageDialog(null, "Ainda não foi implementado.");
				}
			}
		});
		btExecutar.setVisible(true);
		HashSet<UsuarioAutenticado> ua = ClientController.getInstance().getStatusSistema();
		Iterator<UsuarioAutenticado> it = ua.iterator();
		while(it.hasNext()){
			UsuarioAutenticado usuario = it.next();
			jcb.addItem(usuario.getUsuario().getUsername());
		}
		
        form = new JPanel(new SpringLayout());
        form.setSize(new Dimension(355,230));
        form.setBackground(Color.WHITE);
        
        // Usuario e seu combobox
        JLabel usuarios = new JLabel("Usuários: ", JLabel.TRAILING);
        form.add(usuarios);
        usuarios.setLabelFor(jcb);
        form.add(jcb);
        
        // Acao e seu combobox
        JLabel acao = 	  new JLabel("Ação: ", JLabel.TRAILING);
        form.add(acao);
        acao.setLabelFor(acoes);
        form.add(acoes);
        
        JLabel vazio = new JLabel();
        form.add(vazio);
        form.add(btExecutar);
        

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(form,
                                		3, 2, 		 //rows, cols
                                        6, 6,        //initX, initY
                                        6, 6);       //xPad, yPad
		
		jif.add(form);
	}
	


}
