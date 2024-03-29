package client.model.internalContent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
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
import common.remote.ObservadorFila;
import common.util.Utils;
import client.controller.ClientController;
import client.util.ClientConstraint;
import client.util.SpringUtilities;
import client.view.MainView;

public class ListarChamados implements InternalContent, Observer{

	private static final long serialVersionUID = 1L;
	public static final String FILTRAR_CHAMADOS = "FILTRAR_CHAMADOS";
	public static final String RESET_FILTRO	= "RESET_FILTRO";
	
	private JInternalFrame jif;
	private String [] colunas;
	private JPanel form;
	private JPanel tabela;
	private JPanel buttons;
	private JXTable tabelaChamados;
	private JScrollPane scrollPane;
	private ObservadorFila observadorFila;
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
		colunas = new String[]{"Codigo", "Cliente", "Descri��o", "Data Abertura", "Status"};
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


		/* 
		 * bot�es
		 */
		buttons = new JPanel();
		JButton editarChamado = new JButton("Editar");
		editarChamado.setActionCommand(ClientConstraint.EDITAR_CHAMADOS);
		editarChamado.addActionListener(new OuvinteBotoes());

		buttons.add(editarChamado);
		buttons.setAlignmentX(JPanel.RIGHT_ALIGNMENT);

		jif.add(form, BorderLayout.NORTH);
		jif.add(scrollPane,BorderLayout.CENTER);
		jif.add(buttons, BorderLayout.SOUTH);

		
		// ativa o observadorFila e se add como interessado em receber notify
		ClientController.getInstance().ativarObservadorFila(this);
	}

	private void finalizar(){
		// ativa o observadorFila e se add como interessado em receber notify
		ClientController.getInstance().desativarObservadorFila(ListarChamados.this);

	}
	
	public void atualizarFila(List<Chamado> listaChamados){
		if(listaChamados!=null){
			this.listaChamados = listaChamados;
			modeloFila.setLinhas(converterListEmMatriz(listaChamados));
		}
		
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
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

		// "Codigo", "Cliente", "Descri��o", "Data Abertura", "Status"
		for(int linha=0; linha<chamados.size(); linha++){
			Chamado chamado = chamados.get(linha);
			//Prioridade pri = new Prioridade(chamado.getTipoChamado().getValor(), chamado.getPj().getPorte().getValor(), new Date());
			matriz[linha][0] = String.valueOf(chamado.getCodigo());
			matriz[linha][1] = chamado.getPj().getNome();
			matriz[linha][2] = limitadorCaracteres(chamado.getDetalhes(),40);
			matriz[linha][3] = formatador.format(chamado.getDataAbertura()).toString();
			matriz[linha][4] = chamado.getStatus().getNome();

		}

		return matriz;
	}

	public Chamado buscarChamadobyCodigo(int codigo){
		for(Chamado c: listaChamados){
			if(c.getCodigo() == codigo)
				return c;
		}
		return null;
	}
	
	public String limitadorCaracteres(String input, int maxLength){
		if(input.length() > maxLength)
			return (input.substring(0, maxLength-3)+"...");
			
		return input;	
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
		public void internalFrameClosed(InternalFrameEvent e) {
			finalizar();
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

	public class OuvinteBotoes implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {
			String acao = evt.getActionCommand();
			if(acao.equals(ClientConstraint.EDITAR_CHAMADOS)){
				int linha = tabelaChamados.getSelectedRow();
				String numChamado = tabelaChamados.getValueAt(linha, 0).toString();

				Chamado chamadoSelecionado = buscarChamadobyCodigo(Integer.parseInt(numChamado));

				if(chamadoSelecionado == null)
					Utils.printMsg(this.getClass().getName(), "O chamado n�o foi encontrado na lista.");
				// TODO tratar erro

				MainView.getInstance().openNewInternalContent(evt.getActionCommand(),chamadoSelecionado);
				// TODO carregar dados do chamado na tela de edi��o
			}else if(acao.equals(FILTRAR_CHAMADOS)){
				aplicarFiltro(filtro.getText(), itensFiltro.getSelectedIndex());	
				
			}else if(acao.equals(RESET_FILTRO)){
				removerFiltro();
			}
		}

	}

}
