package client.model.internalContent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import common.entity.Chamado;
import common.exception.BusinessException;
import common.remote.ObservadorFila;
import client.Modal;
import client.controller.ObservadorFilaImpl;
import client.util.SpringUtilities;

public class ListarChamados implements InternalContent, Observer{

	private static final long serialVersionUID = 1L;
	private JInternalFrame jif;
	private JPanel form;
	private JPanel tabela;
	private JPanel buttons;
	private JTable tabelaChamados;
	private JScrollPane scrollPane;
	private ObservadorFila observadorFila;
	private FilaChamadoModel modeloFila;
	private List<Chamado> listaChamados;

	@Override
	public JInternalFrame getInternalContent() {

		jif = new JInternalFrame();
		jif.addInternalFrameListener(new ouvinteInternalContent());

		jif.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		jif.setBackground(Color.WHITE);
		jif.setSize(new Dimension(600, 600));
		jif.setClosable(true);
		jif.setResizable(true);	
		jif.setLayout(new BorderLayout());
		//inicializar();

		return jif;
	}

	private void inicializar(){
		String [] colunas = new String[]{"Codigo", "Cliente", "Prioridade", "Data Abertura", "Status"};
		
		listaChamados = new ArrayList<Chamado>();
		modeloFila = new FilaChamadoModel(listaChamados, colunas);
		tabelaChamados = new JTable(modeloFila);

		/* 
		 * form 
		 */ 
		form = new JPanel(new SpringLayout());
		
		JTextField buscar = new JTextField(100);
		JButton btBuscar = new JButton("Buscar");
		form.add(buscar);
		form.add(btBuscar);		
		
		SpringUtilities.makeCompactGrid(form,
	            1, 2,		//rows, cols
	            6, 6,     	//initX, initY
	            6, 6);      //xPad, yPad
		
		/* 
		 * tabela de chamados
		 */
		tabela = new JPanel();
		tabela.add(tabelaChamados);
		scrollPane = new JScrollPane(tabela);
		
		
		/* 
		 * botões
		 */
		buttons = new JPanel();
		JButton editarChamado = new JButton("Editar");
		editarChamado.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int sel = ListarChamados.this.tabelaChamados.getSelectedRow();
				System.out.println("Linha selecionada: " + sel );
			}
		});
		
		buttons.add(editarChamado);
		buttons.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
		
		jif.add(form, BorderLayout.NORTH);
		jif.add(scrollPane,BorderLayout.CENTER);
		jif.add(buttons, BorderLayout.SOUTH);
		
		try{
			setObservadorFila(new ObservadorFilaImpl(this));

		}catch(BusinessException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void atualizarFila(List<Chamado> listaChamados){
		modeloFila.setLinhas(listaChamados);
		tabelaChamados = new JTable(modeloFila);
		tabela.repaint();
		scrollPane.repaint();
		jif.repaint();

		System.out.println(new Date() + " - Fila atualizada. Size: " + listaChamados.size());
	}

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
