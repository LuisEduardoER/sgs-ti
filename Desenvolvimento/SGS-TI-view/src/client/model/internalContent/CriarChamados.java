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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import common.entity.TipoFalha;
import common.exception.BusinessException;
import common.remote.ObservadorFila;
import common.remote.ServiceChamadoItens;
import common.util.Utils;
import client.ModalCliente;
import client.util.SpringUtilities;


public class CriarChamados implements InternalContent
{
	private JInternalFrame jif;
	private JPanel form;
	private ObservadorFila observadorFila;
	private ServiceChamadoItens servico;
	private List<TipoFalha> listFalha;
	private OuvinteEditarChamado oec;

	/**
	 * Componetes 
	 */
	private	JTextField clienteTextField;
	private JTextField responsavelTextField;
	private JTextField enderecoTextField;
	private JComboBox tipoFalhaComboBox;
	private JComboBox prioridadeTextField;
	private JTextArea descricaoTextArea;
	private JButton btSalvar;
	private JButton btCancelar;
	private JButton btBuscarCliente;
	
	public CriarChamados()
	{
		try {
			listFalha = new ArrayList<TipoFalha>();

			servico = Utils.obterServiceChamadoItens();
		} catch (BusinessException e) {
			// TODO Vanessa -  Arrumar Exceção
			new BusinessException("As listas estão vazias, favor preenche-las");
		}
	}

	public JInternalFrame getInternalContent(Object param) {

		//Utils.printMsg(this.getClass().getName(), "Editando chamado número: " + chamado.getNumeroChamado());

		jif = new JInternalFrame("Cadastrar chamado");
		jif.addInternalFrameListener(new ouvinteInternalContent());

		jif.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		jif.setBackground(Color.WHITE);
		jif.setSize(430,340);
		jif.setLocation(30, 30);
		jif.setClosable(true);
		jif.setResizable(false);	
		jif.setLayout(new BorderLayout());

		return jif;
	}

	private void inicializar()
	{
		//Form
		form = new JPanel(new SpringLayout());
		form.setSize(130,130);
		form.setOpaque(true);
		// Cria o ouvinte
		oec = new OuvinteEditarChamado();

		JLabel clienteL = new JLabel("Cliente: ");
		clienteTextField = new JTextField();
		btBuscarCliente = new JButton("Buscar");

		JLabel responsavelL = new JLabel("Responsavel: ");
		responsavelTextField = new JTextField();
		
		JLabel lblEndereco = new JLabel("Endereço: ");
		enderecoTextField = new JTextField();

		JLabel tipoFalhaL = new JLabel("Tipo de falha: ");
		tipoFalhaComboBox = new JComboBox();

		JLabel lblPrioridade = new JLabel("Prioridade: ");
		prioridadeTextField = new JComboBox();
		
		JLabel descricaoL = new JLabel("Descrição: ");		
		descricaoTextArea = new JTextArea();

		JScrollPane scroll = new JScrollPane(descricaoTextArea);

		btSalvar = new JButton();

		btCancelar = new JButton(); 

		inicializacaoComponentes();

		form.add(clienteL);
		form.add(clienteTextField);
		form.add(btBuscarCliente);
		
		form.add(responsavelL);
		form.add(responsavelTextField);
		form.add(new JLabel(""));
		
		form.add(lblEndereco);
		form.add(enderecoTextField);
		form.add(new JLabel(""));
		
		form.add(tipoFalhaL);
		form.add(tipoFalhaComboBox);
		form.add(new JLabel(""));
		
		form.add(lblPrioridade);
		form.add(prioridadeTextField);
		form.add(new JLabel(""));
		
		form.add(descricaoL);
		form.add(scroll);
		form.add(new JLabel(""));
		
		form.add(btSalvar);
		form.add(btCancelar);
		form.add(new JLabel(""));

		SpringUtilities.makeCompactGrid(form,
				7, 3,		//rows, cols
				6, 6,     	//initX, initY
				6, 6);      //xPad, yPad

		jif.add(form);



	}

	public void inicializacaoComponentes()
	{
		try {

			clienteTextField.setText("Pegar o cliente na modal");
			clienteTextField.setEditable(false);

			//responsavelTextField.setText(chamado.getResponsavel().toString());
			responsavelTextField.setEditable(false);

			descricaoTextArea.setAutoscrolls(true);
			descricaoTextArea.setRows(10);
			descricaoTextArea.setText("");
			
			btBuscarCliente.addActionListener(oec);
			btBuscarCliente.setToolTipText("Buscar um cliente");
			btBuscarCliente.setActionCommand("MODALCLIENTE");

			btSalvar.setText("Salvar");
			btSalvar.addActionListener(oec);
			btSalvar.setToolTipText("Salvar alterações");
			btSalvar.setActionCommand("SALVAR");

			btCancelar.setText("Cancelar");
			btCancelar.addActionListener(oec);
			btCancelar.setToolTipText("Cancelar alterações");
			btCancelar.setActionCommand("CANCELAR");

			listFalha = servico.procurarFalha();		

			for (int cont = 0; cont < listFalha.size(); cont++) {
				tipoFalhaComboBox.addItem(listFalha.get(cont).getNome());
			}			


		} catch (RemoteException e) {
			// TODO Vanessa - Colocar Exception
			e.printStackTrace();
		}
	}

	public void update(Observable o, Object arg) {
		// TODO Vanessa - Ver o que colocar aqui
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
			
		}

		@Override
		public void internalFrameActivated(InternalFrameEvent e) {
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
				//ClientController.getInstance().atualizarChamado(chamado);
			}

			if(evt.getActionCommand().equals("CANCELAR"))
			{
				jif.dispose();
			}
			
			if(evt.getActionCommand().equals("MODALCLIENTE")){
				ModalCliente modal = new ModalCliente();
				modal.setModal(true);

				modal.addWindowListener(new WindowAdapter() {
					// Quando fechar a modal, exibir
					@Override
					public void windowClosing(WindowEvent e) {
						String cliente = ((ModalCliente)e.getSource()).getCliente();
						clienteTextField.setText(cliente);
					}	
				});

				modal.setVisible(true);
			}

			/*if(evt.getActionCommand().equals("AGENDAR"))
			{
				if(statusComboBox.getSelectedItem().toString().equals(StatusChamado.AGENDADO))
				{
					dataAgentamentoDateChooser.setEnabled(true);
					horaAgendamentoTextField.setEnabled(true);
				}
				else
				{
					dataAgentamentoDateChooser.setEnabled(false);
					horaAgendamentoTextField.setEnabled(false);
				}
			}*/
		}
	}
	public JComboBox getPrioridade() {
		return prioridadeTextField;
	}

	public void setPrioridade(JComboBox prioridade) {
		this.prioridadeTextField = prioridade;
	}
	
	public JTextField getEnderecoTextField() {
		return enderecoTextField;
	}

	public void setEnderecoTextField(JTextField enderecoTextField) {
		this.enderecoTextField = enderecoTextField;
	}
}
