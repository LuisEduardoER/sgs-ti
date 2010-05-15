package client.model.internalContent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import common.entity.Chamado;
import common.exception.BusinessException;
import common.remote.ObservadorFila;

import client.Modal;
import client.controller.ObservadorFilaImpl;
import client.util.FilaChamadoModel;


public class ListarChamados implements InternalContent, Observer
{
	private static final long serialVersionUID = 1L;
	private JInternalFrame jif;
	private ObservadorFila observadorFila;
	FilaChamadoModel modeloFila;
	private JTable tabelaChamados;
	private List<Chamado> listaChamados;

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

		/*
		JTextArea text = new JTextArea();
		text.setText("Oi! Eu sou um internalContent.");
		jif.add(text);
		 */

		jif.add(tabelaChamados);

		return jif;
}

private void inicializar(){
	listaChamados = new ArrayList<Chamado>();
	modeloFila = new FilaChamadoModel(listaChamados);
	tabelaChamados = new JTable(modeloFila);

	try{
		setObservadorFila(new ObservadorFilaImpl(this));

	}catch(BusinessException e){
		JOptionPane.showMessageDialog(null, e.getMessage());
	}
}

public void atualizarFila(List<Chamado> listaChamados){
	modeloFila.setLinhas(listaChamados);
	tabelaChamados = new JTable(modeloFila);
	jif.repaint();

	System.out.println(new Date() + " - Fila atualizada. Size: " + listaChamados.size());
}

@SuppressWarnings("unchecked")
@Override
public void update(Observable o, Object listaChamados) {
	try{

		atualizarFila((List<Chamado>)listaChamados);

	}catch(Exception e){
		// TODO: criar exception
		System.out.println("Erro ao converter objeto, " + e);
	}
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
