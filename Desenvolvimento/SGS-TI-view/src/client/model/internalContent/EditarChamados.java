package client.model.internalContent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import com.toedter.calendar.JDateChooser;

import common.entity.StatusChamado;
import common.entity.TipoFalha;
import common.exception.BusinessException;
import common.remote.ObservadorFila;
import common.remote.ServiceChamadoItens;
import common.util.Utils;

import client.Modal;
import client.controller.ObservadorFilaImpl;
import client.util.SpringUtilities;


public class EditarChamados  implements InternalContent, Observer
{
	private JInternalFrame jif;
	private JPanel form;
	private ObservadorFila observadorFila;
	private ServiceChamadoItens servico;
	private List<TipoFalha> listFalha;
	private List<StatusChamado> listStatus;

	public EditarChamados()
	{
		try {
			listFalha = new ArrayList<TipoFalha>();
			listStatus = new ArrayList<StatusChamado>();

			servico = Utils.obterServiceChamadoI();
		} catch (BusinessException e) {
			// TODO Colocar Exceção
			e.printStackTrace();
		}
	}

	public JInternalFrame getInternalContent() {
		jif = new JInternalFrame();
		jif.addInternalFrameListener(new ouvinteInternalContent());

		jif.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		jif.setBackground(Color.WHITE);
		jif.setSize(600, 600);
		jif.setLocation(10, 10);
		jif.setClosable(true);
		jif.setResizable(true);	
		jif.setLayout(new BorderLayout());

		return jif;
	}

	private void inicializar(){

		try {
			/* 
			 * form 
			 */ 
			form = new JPanel(new SpringLayout());
			
			// Cria o ouvinte
			OuvinteEditarChamado oec = new OuvinteEditarChamado();

			JLabel dataAberturaL = new JLabel("Data Abertura: ");		
			JTextField dataAberturaT = new JTextField();

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

			JLabel statusL = new JLabel("Status: ");
			JComboBox statusC = new JComboBox();

			JLabel dataAgendamentoL = new JLabel("Data: ");		
			JDateChooser dataAgentamentoD = new JDateChooser();

			JLabel horaAgendamentoL = new JLabel("Hora: ");		
			JTextField horaAgendamentoD = new JTextField();
			
			JLabel descricaoL = new JLabel("Descrição: ");		
			JTextArea descricaoD = new JTextArea();
			descricaoD.setAutoscrolls(true);
			descricaoD.setRows(10);
			
			JScrollPane scroll = new JScrollPane(descricaoD);
			
			JButton btSalvar = new JButton();
			btSalvar.setText("Salvar");
			btSalvar.addActionListener(oec);
			btSalvar.setToolTipText("Salvar alterações");
			
			JButton btCancelar = new JButton(); 
			btCancelar.setText("Cancelar");
			btCancelar.addActionListener(oec);
			btCancelar.setToolTipText("Cancelar alterações");
			
			
			listFalha = servico.procurarFalha();
			listStatus = servico.procurarStatus();

			for (int cont = 0; cont < listFalha.size(); cont++) {
				tipoFalhaC.addItem(listFalha.get(cont).getNome());
			}

			for (int cont = 0; cont < listStatus.size(); cont++) {
				statusC.addItem(listStatus.get(cont).getNome());
			}

			form.add(dataAberturaL);
			form.add(dataAberturaT);
			form.add(aberturaL);
			form.add(aberturaT);
			form.add(clienteL);
			form.add(clienteT);
			form.add(responsavelL);
			form.add(responsavelT);
			form.add(tipoFalhaL);
			form.add(tipoFalhaC);
			form.add(statusL);
			form.add(statusC);
			form.add(dataAgendamentoL);
			form.add(dataAgentamentoD);
			form.add(horaAgendamentoL);
			form.add(horaAgendamentoD);
			form.add(descricaoL);
			form.add(scroll);
			form.add(btSalvar);
			form.add(btCancelar);

			SpringUtilities.makeCompactGrid(form,
					10, 2,		//rows, cols
					6, 6,     	//initX, initY
					6, 6);      //xPad, yPad

			jif.add(form);


			setObservadorFila(new ObservadorFilaImpl(this));

		}catch(BusinessException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (RemoteException e) {
			// TODO Colocar Excecao
			e.printStackTrace();
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
	
	class OuvinteEditarChamado implements ActionListener 
	{
		/**
		  @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			//jif.dispose();
		}
	}
}
