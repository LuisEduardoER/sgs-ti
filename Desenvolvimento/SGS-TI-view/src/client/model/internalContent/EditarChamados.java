package client.model.internalContent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
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
import com.toedter.calendar.JDateChooser;
import common.entity.Chamado;
import common.entity.StatusChamado;
import common.entity.TipoFalha;
import common.exception.BusinessException;
import common.remote.ServiceChamadoItens;
import common.util.Utils;
import client.Modal;
import client.controller.ClientController;
import client.util.SpringUtilities;

public class EditarChamados extends Observable implements InternalContent
{
	private JInternalFrame jif;
	private JPanel form;
	private ServiceChamadoItens servico;
	private List<TipoFalha> listFalha;
	private List<StatusChamado> listStatus;
	private Chamado chamado;
	private OuvinteEditarChamado oec;

	/**
	 * Componetes 
	 */
	private JTextField dataAberturaTextField;
	private JTextField aberturaTextField;
	private	JTextField clienteTextField;
	private JTextField responsavelTextField;
	private JComboBox tipoFalhaComboBox;
	private	JComboBox statusComboBox;
	private JDateChooser dataAgentamentoDateChooser;
	private JTextField horaAgendamentoTextField;
	private JTextArea descricaoTextArea;
	private JButton btSalvar;
	private JButton btCancelar;

	public EditarChamados()
	{
		try {
			listFalha = new ArrayList<TipoFalha>();
			listStatus = new ArrayList<StatusChamado>();

			servico = Utils.obterServiceChamadoItens();
		} catch (BusinessException e) {
			// TODO Vanessa -  Arrumar Exceção
			new BusinessException("As listas estão vazias, favor preenche-las");
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

	private void inicializar()
	{
		//Form
		form = new JPanel(new SpringLayout());

		// Cria o ouvinte
		oec = new OuvinteEditarChamado();

		JLabel dataAberturaL = new JLabel("Data Abertura: ");		
		dataAberturaTextField = new JTextField();

		JLabel aberturaL = new JLabel("Aberto por: ");
		aberturaTextField = new JTextField();

		JLabel clienteL = new JLabel("Cliente: ");
		clienteTextField = new JTextField();

		JLabel responsavelL = new JLabel("Responsavel: ");
		responsavelTextField = new JTextField();

		JLabel tipoFalhaL = new JLabel("Tipo de falha: ");
		tipoFalhaComboBox = new JComboBox();

		JLabel statusL = new JLabel("Status: ");
		statusComboBox = new JComboBox();

		JLabel dataAgendamentoL = new JLabel("Data: ");		
		dataAgentamentoDateChooser = new JDateChooser();
		
		JLabel horaAgendamentoL = new JLabel("Hora: ");		
		horaAgendamentoTextField = new JTextField();

		JLabel descricaoL = new JLabel("Descrição: ");		
		descricaoTextArea = new JTextArea();

		JScrollPane scroll = new JScrollPane(descricaoTextArea);

		btSalvar = new JButton();

		btCancelar = new JButton(); 

		inicializacaoComponentes();

		form.add(dataAberturaL);
		form.add(dataAberturaTextField);
		form.add(aberturaL);
		form.add(aberturaTextField);
		form.add(clienteL);
		form.add(clienteTextField);
		form.add(responsavelL);
		form.add(responsavelTextField);
		form.add(tipoFalhaL);
		form.add(tipoFalhaComboBox);
		form.add(statusL);
		form.add(statusComboBox);
		form.add(dataAgendamentoL);
		form.add(dataAgentamentoDateChooser);
		form.add(horaAgendamentoL);
		form.add(horaAgendamentoTextField);
		form.add(descricaoL);
		form.add(scroll);
		form.add(btSalvar);
		form.add(btCancelar);

		SpringUtilities.makeCompactGrid(form,
				10, 2,		//rows, cols
				6, 6,     	//initX, initY
				6, 6);      //xPad, yPad

		jif.add(form);
	}

	public void inicializacaoComponentes()
	{
		try {
			dataAberturaTextField.setText(chamado.getDataHoraAbertura().toString());
			dataAberturaTextField.setEditable(false);

			aberturaTextField.setText(chamado.getUsuarioResgistro().getUsername().toString());
			aberturaTextField.setEditable(false);

			clienteTextField.setText(chamado.getReclamante().getNome().toString());
			clienteTextField.setEditable(false);

			statusComboBox.setActionCommand("AGENDAR");
			statusComboBox.addActionListener(oec);

			responsavelTextField.setText(chamado.getResponsavel().toString());
			responsavelTextField.setEditable(false);

			descricaoTextArea.setAutoscrolls(true);
			descricaoTextArea.setRows(10);
			descricaoTextArea.setText(chamado.getDetalhes());

			btSalvar.setText("Salvar");
			btSalvar.addActionListener(oec);
			btSalvar.setToolTipText("Salvar alterações");
			btSalvar.setActionCommand("SALVAR");

			btCancelar.setText("Cancelar");
			btCancelar.addActionListener(oec);
			btCancelar.setToolTipText("Cancelar alterações");
			btCancelar.setActionCommand("CANCELAR");

			listFalha = servico.procurarFalha();		
			listStatus = servico.procurarStatus();

			for (int cont = 0; cont < listFalha.size(); cont++) {
				tipoFalhaComboBox.addItem(listFalha.get(cont).getNome());
			}			
			for (int i = 0; i < listFalha.size(); i++) {
				if(chamado.getTipoFalha().getNome().toString().equals(listFalha.get(i).getNome().toString()))
					tipoFalhaComboBox.setSelectedIndex(i);
			}

			for (int cont = 0; cont < listStatus.size(); cont++) {
				statusComboBox.addItem(listStatus.get(cont).getNome());
			}  
			for (int i = 0; i < listStatus.size(); i++) {
				if(chamado.getStatus().getNome().toString().equals(listStatus.get(i).getNome().toString()))
					statusComboBox.setSelectedIndex(i);
			}

			dataAgentamentoDateChooser.setDateFormatString("dd/MM/yyyy");
			
		} catch (RemoteException e) {
			// TODO Vanessa - Colocar Exception
			e.printStackTrace();
		}
	}

	public class ouvinteInternalContent implements InternalFrameListener{
		@Override
		public void internalFrameOpened(InternalFrameEvent e) {
			inicializar();
		}
		
		@Override
		public void internalFrameClosed(InternalFrameEvent e) {
			Modal modal = new Modal();
			modal.setModal(true);

			modal.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					List<String> form = ((Modal)e.getSource()).pegarDados();
					for(String s : form)
						Utils.printMsg(this.getClass().getName(), s);
				}	
			});

			modal.setVisible(true);
		}
		
		@Override
		public void internalFrameClosing(InternalFrameEvent e) {
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
				
				System.out.println("TRGFFFF = " + dataAgentamentoDateChooser.getDate());
	
				
				Chamado newChamado = new Chamado(chamado.getNumeroChamado(), 
						(Date) chamado.getDataHoraAbertura(),
						(Date) chamado.getDataHoraFechamento(),
						descricaoTextArea.getText().toString(),
						new StatusChamado(statusComboBox.getSelectedItem().toString()),
						chamado.getPrioridade(),
						chamado.getTipoChamado(),
						chamado.getReclamante(),
						new TipoFalha(tipoFalhaComboBox.getSelectedItem().toString()), 
						chamado.getResponsavel(),
						chamado.getUsuarioResgistro(),
						(Date) dataAgentamentoDateChooser.getDate());
				ClientController.getInstance().atualizarChamado(newChamado);
			}

			if(evt.getActionCommand().equals("CANCELAR"))
			{
				jif.dispose();
			}	

			if(evt.getActionCommand().equals("AGENDAR"))
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
			}
		}
	}
}
