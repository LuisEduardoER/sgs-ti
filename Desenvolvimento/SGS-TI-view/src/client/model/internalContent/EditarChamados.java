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

import common.entity.Chamado;
import common.entity.StatusChamado;
import common.entity.TipoFalha;
import common.exception.BusinessException;
import common.remote.ObservadorFila;
import common.remote.ServiceChamadoItens;
import common.util.Utils;

import client.Modal;
import client.controller.ClientController;
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
	private Chamado chamado;
	private OuvinteEditarChamado oec;
	
	/**
	 * Componetes 
	 */
	private JTextField dataAberturaT;
	private JTextField aberturaT;
	private	JTextField clienteT;
	private JTextField responsavelT;
	private JComboBox tipoFalhaC;
	private	JComboBox statusC;
	private JDateChooser dataAgentamentoD;
	private JTextField horaAgendamentoD;
	private JTextArea descricaoD;
	private JButton btSalvar;
	private JButton btCancelar;

	public EditarChamados()
	{
		try {
			listFalha = new ArrayList<TipoFalha>();
			listStatus = new ArrayList<StatusChamado>();

			servico = Utils.obterServiceChamadoItens();
		} catch (BusinessException e) {
			// TODO Colocar Exceção
			e.printStackTrace();
		}
	}

	public JInternalFrame getInternalContent(Object param) {
		chamado = (Chamado) param;

		Utils.printMsg(this.getClass().getName(), "Editando chamado número: " + chamado.getNumeroChamado());
		
		jif = new JInternalFrame();
		jif.addInternalFrameListener(new ouvinteInternalContent());

		jif.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		jif.setBackground(Color.WHITE);
		jif.setSize(600, 600);
		jif.setLocation(30, 30);
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
			oec = new OuvinteEditarChamado();

			JLabel dataAberturaL = new JLabel("Data Abertura: ");		
			dataAberturaT = new JTextField();

			JLabel aberturaL = new JLabel("Aberto por: ");
			aberturaT = new JTextField();

			JLabel clienteL = new JLabel("Cliente: ");
			clienteT = new JTextField();

			JLabel responsavelL = new JLabel("Responsavel: ");
			responsavelT = new JTextField();
			
			JLabel tipoFalhaL = new JLabel("Tipo de falha: ");
			tipoFalhaC = new JComboBox();

			JLabel statusL = new JLabel("Status: ");
			statusC = new JComboBox();

			JLabel dataAgendamentoL = new JLabel("Data: ");		
			dataAgentamentoD = new JDateChooser();

			JLabel horaAgendamentoL = new JLabel("Hora: ");		
			horaAgendamentoD = new JTextField();
			
			JLabel descricaoL = new JLabel("Descrição: ");		
			descricaoD = new JTextArea();
			
			JScrollPane scroll = new JScrollPane(descricaoD);
			
			btSalvar = new JButton();
			
			btCancelar = new JButton(); 
			
			inicializacaoComponentes();
			
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
		}

	}
	
	public void inicializacaoComponentes()
	{
		try {
			dataAberturaT.setText(chamado.getDataHoraAbertura().toString());
	
			//TODO EditarChamado - Colocar o atributo "aberto por" no chamado
			aberturaT.setEditable(false);
	
			clienteT.setText(chamado.getReclamante().getNome().toString());
			clienteT.setEditable(false);
			
			//TODO EditarChamado - Colocar o atributo "responsavel" no chamado
			responsavelT = new JTextField();
			
			//TODO EditarChamado - Colocar o atributo "tipo falha no chamado" no chamado
			//tipoFalhaC.setSelectedItem(chamado.)
	
			statusC.setSelectedItem(chamado.getStatus().toString());
	
			descricaoD.setAutoscrolls(true);
			descricaoD.setRows(10);
			descricaoD.setText(chamado.getDetalhes());
			
			btSalvar.setText("Salvar");
			btSalvar.addActionListener(oec);
			btSalvar.setToolTipText("Salvar alterações");
			
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
			statusC.setSelectedItem(chamado.getStatus().toString());
			
		} catch (RemoteException e) {
			// TODO EditarChamado - Colocar Exception
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
		@Override
		public void actionPerformed(ActionEvent evt) {
			if (evt.getActionCommand().equals("SALVAR")) {
				ClientController.getInstance().atualizarChamado(chamado);
			}

			if(evt.getActionCommand().equals("CANCELAR"))
			{
				jif.dispose();
			}			
		}
	}
}
