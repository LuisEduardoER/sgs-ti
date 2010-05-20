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
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.PatternFilter;
import common.entity.Chamado;
import common.exception.BusinessException;
import common.remote.ObservadorAgendamento;
import common.util.Utils;
import client.Modal;
import client.controller.ObservadorFilaImplAgendamento;
import client.util.SpringUtilities;

public class ListarAgenda implements InternalContent, Observer
{

	private static final long serialVersionUID = 1L;
	public static final String FILTRAR_CHAMADOS = "FILTRAR_CHAMADOS";
	public static final String RESET_FILTRO	= "RESET_FILTRO";
	
	private JInternalFrame jif;
	private String [] colunas;
	private JPanel form;
	private JPanel tabela;
	private JXTable tabelaChamados;
	private JScrollPane scrollPane;
	private ObservadorAgendamento observadorAgendamento;
	private JXTableModel modeloFila;
	private List<Chamado> listaChamados;
	private JTextField filtro;
	private JComboBox itensFiltro;
	
	@Override
	public JInternalFrame getInternalContent(Object param) {
	
		jif = new JInternalFrame();
		jif.addInternalFrameListener(new ouvinteInternalContent());

		jif.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		jif.setBackground(Color.WHITE);
		jif.setLocation(10, 10);
		jif.setSize(new Dimension(800, 450));
		jif.setClosable(true);
		jif.setResizable(true);	
		jif.setLayout(new BorderLayout());
		//inicializar();

		return jif;
	}

	private void inicializar(){
		colunas = new String[]{"Chamado", "Cliente", "Prioridade", "Endere�o", "Contato"};
		listaChamados = new ArrayList<Chamado>();
		modeloFila = new JXTableModel(converterListEmMatriz(listaChamados), colunas);
		tabelaChamados = new JXTable(modeloFila);	
		tabelaChamados.setDragEnabled(false);
		tabelaChamados.setDoubleBuffered(false);

		/* 
		 * form 
		 */ 
		form = new JPanel(new SpringLayout());

		filtro = new JTextField(70);
		itensFiltro = new JComboBox();
		for(String s : colunas)
			itensFiltro.addItem(s);
			
		JButton btFiltrar = new JButton("Filtrar");
		btFiltrar.setActionCommand(FILTRAR_CHAMADOS);
		JButton btReset = new JButton("Reset");
		btReset.setActionCommand(RESET_FILTRO);
		
		btFiltrar.addActionListener(new OuvinteBotoes());
		btReset.addActionListener(new OuvinteBotoes());

		form.add(filtro);
		form.add(itensFiltro);
		form.add(btFiltrar);
		form.add(btReset);

		SpringUtilities.makeCompactGrid(form,
				1, 4,		//rows, cols
				6, 6,     	//initX, initY
				6, 6);      //xPad, yPad

		/* 
		 * tabela de chamados
		 */
		tabela = new JPanel(new BorderLayout());
		scrollPane = new JScrollPane(tabelaChamados);

		tabela.add(tabelaChamados.getTableHeader(),BorderLayout.NORTH);
		tabela.add(scrollPane,BorderLayout.CENTER);


		jif.add(form, BorderLayout.NORTH);
		jif.add(scrollPane,BorderLayout.CENTER);

		try{
			setObservadorAgendamento(new ObservadorFilaImplAgendamento(this));

		}catch(BusinessException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		
		new Thread()
		{
			public void run() {
				while (true) {
					for(Chamado c : listaChamados)
					{
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						System.out.println("@  Data agendamento = " + c.getDataHoraAbertura());
						System.out.println("@  Data atual = " + new Date());
						System.out.println("@  Data agendamento string = " + c.getDataHoraAbertura().toString());
						System.out.println("@  Data atual string = " + new Date().toString());
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						
						long d = c.getDataHoraAgendamento().getTime();
						long dd = new Date().getTime();
						
						//if(c.getDataHoraAgendamento().after(new Date()))
						if(d < dd)
							System.out.println("Agendamento " + c.getDataHoraAbertura() + "j� passou");
						if(d == dd)
							System.out.println("Agendamento " + c.getDataHoraAbertura() + "AGORA");
						if(d > dd)
							System.out.println("Agendamento " + c.getDataHoraAbertura() + "ira vir");
							
						
						try {
							sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			};
		}.start();
		
	}

	public void atualizarFila(List<Chamado> listaChamados){
		this.listaChamados = listaChamados;
		modeloFila.setLinhas(converterListEmMatriz(listaChamados));
		modeloFila.fireTableDataChanged();

		Utils.printMsg(this.getClass().getName(), new Date() + " - Fila atualizada. Size: " + listaChamados.size());
	}

	private void aplicarFiltro(String valor, int coluna) {
		Filter[] filterArray = { new PatternFilter("(.*"+valor+".*)|(.*"+valor.toUpperCase()+".*)|(.*"+valor.toLowerCase()+".*)", 0, coluna) };
		FilterPipeline filters = new FilterPipeline(filterArray);
		tabelaChamados.setFilters(filters);
	}
	
	private void removerFiltro(){
		filtro.setText(null);
		tabelaChamados.setFilters(null);
		tabelaChamados.clearSelection();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object listaChamados) {
		try{

			atualizarFila((List<Chamado>)listaChamados);

		}catch(Exception e){
			// TODO: criar exception
			Utils.printMsg(this.getClass().getName(),"Erro ao converter objeto, " + e);
		}
	}

	public String[][] converterListEmMatriz(List<Chamado> chamados){
		String [][]matriz = new String [chamados.size()][5];

		// "Codigo", "Cliente", "Prioridade", "Reclamante", "Contato"
		for(int linha=0; linha<chamados.size(); linha++){
			Chamado chamado = chamados.get(linha);
			matriz[linha][0] = String.valueOf(chamado.getNumeroChamado());
			matriz[linha][1] = chamado.getReclamante().getNome();
			matriz[linha][2] = String.valueOf(chamado.getPrioridade().getValorPrioridade());
			matriz[linha][3] = chamado.getReclamante().getEndereco().toString();
			matriz[linha][4] = chamado.getContato().toString();
		}

		return matriz;
	}

	public Chamado buscarChamadobyCodigo(int codigo){
		for(Chamado c: listaChamados){
			if(c.getNumeroChamado() == codigo)
				return c;
		}
		return null;
	}


	/*
	 * GETTERs AND SETTERs
	 */
	public ObservadorAgendamento getObservadorAgendamento() {
		return observadorAgendamento;
	}

	public void setObservadorAgendamento(ObservadorAgendamento observadorAgendamento) {
		this.observadorAgendamento = observadorAgendamento;
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
				if(getObservadorAgendamento() != null)
					getObservadorAgendamento().removerObservador();

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

	public class OuvinteBotoes implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {
			String acao = evt.getActionCommand();
			if(acao.equals(FILTRAR_CHAMADOS)){
				aplicarFiltro(filtro.getText(), itensFiltro.getSelectedIndex());	
				
			}else if(acao.equals(RESET_FILTRO)){
				removerFiltro();
			}
		}

	}
}
