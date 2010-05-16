package client.model.internalContent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import com.toedter.calendar.JDateChooser;

import common.exception.BusinessException;
import common.remote.ObservadorFila;

import client.Modal;
import client.controller.ObservadorFilaImpl;
import client.util.SpringUtilities;


public class EditarChamados  implements InternalContent, Observer
{
	private JInternalFrame jif;
	private JPanel form;
	private ObservadorFila observadorFila;

	public JInternalFrame getInternalContent() {
		jif = new JInternalFrame();
		jif.addInternalFrameListener(new ouvinteInternalContent());

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
		
		JLabel dataAberturaL = new JLabel("Data Abertura: ");		
		JDateChooser dataAberturaT = new JDateChooser();
		
		JLabel aberturaL = new JLabel("Aberto por: ");
		JTextField aberturaT = new JTextField();
		aberturaT.setEditable(false);
		
		JLabel clienteL = new JLabel("Cliente: ");
		JTextField clienteT = new JTextField();
		clienteT.setEditable(false);
		
		JLabel responsavelL = new JLabel("Responsavel: ");
		JTextField responsavelT = new JTextField();
		
		JLabel tipoFalhaL = new JLabel("Tipo de falha: ");
		JComboBox tipoFalhaC = new JComboBox();
				
		form.add(dataAberturaL);
		form.add(dataAberturaT);
		form.add(aberturaL);
		form.add(aberturaT);
		form.add(clienteL);
		form.add(clienteT);
		form.add(responsavelL);
		form.add(responsavelT);
		
		SpringUtilities.makeCompactGrid(form,
	            4, 2,		//rows, cols
	            6, 6,     	//initX, initY
	            6, 6);      //xPad, yPad
				
		jif.add(form);
		
		try{
			setObservadorFila(new ObservadorFilaImpl(this));

		}catch(BusinessException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
	}

	public void update(Observable o, Object arg) {
		
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
	
	public class ouvinteInternalContent implements InternalFrameListener{
		@Override
		public void internalFrameOpened(InternalFrameEvent e) {
			inicializar();
		}

		@Override
		public void internalFrameClosing(InternalFrameEvent e) {
		}			

		@Override
		public void internalFrameClosed(InternalFrameEvent e) {
			try {
				if(getObservadorFila() != null)
					getObservadorFila().removerObservador();

			} catch (RemoteException ex) {
				// TODO criar exception
				ex.printStackTrace();
			}	
			
			Modal modal = new Modal();
			modal.setModal(true);

			modal.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					List<String> form = ((Modal)e.getSource()).pegarDados();
					for(String s : form)
						System.out.println(s);
				}	
			});

			modal.setVisible(true);
		}

		@Override
		public void internalFrameActivated(InternalFrameEvent e) {
			// TODO Auto-generated method stub

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
	}
}
