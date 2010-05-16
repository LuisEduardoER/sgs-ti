package client.model.internalContent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import common.exception.BusinessException;
import common.remote.ObservadorFila;

import client.Modal;
import client.controller.ObservadorFilaImpl;


public class ListarAgenda implements InternalContent, Observer
{
	private static final long serialVersionUID = 1L;
	private JInternalFrame jif;
	private ObservadorFila observadorFila;

	@Override
	public JInternalFrame getInternalContent() {

		jif = new JInternalFrame();
		jif.addInternalFrameListener(new InternalFrameListener() {			

			@Override
			public void internalFrameOpened(InternalFrameEvent e) {
				inicializar();
			}

			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				/*
				try {
					getObservadorFila().removerObservador();
				} catch (RemoteException ex) {
					// TODO criar exception
					ex.printStackTrace();
				}
				 */
			}			
			@Override
			public void internalFrameIconified(InternalFrameEvent e) {

			}
			@Override
			public void internalFrameDeiconified(InternalFrameEvent e) {

			}
			@Override
			public void internalFrameDeactivated(InternalFrameEvent e) {

			}			
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				Modal modal = new Modal();
				modal.setVisible(true);

				modal.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						List<String> form = ((Modal)e.getSource()).pegarDados();
						for(String s : form)
							System.out.println(s);
					}	
				});

				modal.setModal(true);

			}
			@Override
			public void internalFrameActivated(InternalFrameEvent e) {
				// TODO Auto-generated method stub

			}
		});

		jif.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		jif.setBackground(Color.WHITE);
		jif.setPreferredSize(new Dimension(600, 600));
		jif.setClosable(true);
		jif.setResizable(true);	
		inicializar();

		return jif;
	}

	private void inicializar(){
		try{
			setObservadorFila(new ObservadorFilaImpl(this));

		}catch(BusinessException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO ListarAgenda : Fazer update
		System.out.println("Listar Agenda update");
		
	}
	
	/*
	 * GETTERs AND SETTERs
	 */
	public void setObservadorFila(ObservadorFila observadorFila) {
		this.observadorFila = observadorFila;
	}

	public ObservadorFila getObservadorFila() {
		return observadorFila;
	}
}