package client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.controller.ClientController;
import client.util.ClientConstraint;

public class MainView extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// Debug
	private final boolean DEBUG = false;
	
	private static MainView instance;
	private JPanel mainMenu;
	private JDesktopPane conteudo;
	private JPanel sideMenu;

	/**
	 * Construtor.
	 * @param nome
	 * 		Nome da Janela.
	 */
	private MainView(String nome){
		super(nome);
		JFrame.setDefaultLookAndFeelDecorated(true);
		inicializaComponentes();
	}
	
	/**
	 * Inicializa os componentes da JFrame.
	 */
	public void inicializaComponentes(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resources/imgs/window_icon.png"));
		setSize(new Dimension(1024,768));
		setLayout(new BorderLayout(5,5));
		setBackground(Color.white);
		
		setJMenuBar(carregaMenuTopo());
		mainMenu = carregarMenuConteudos();
		conteudo = new JDesktopPane();
		conteudo.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		
		sideMenu = new JPanel();
		
		changeSideMenu(ClientConstraint.SIDE_MENU_CLIENTE);
		//openNewInternalContent(ClientConstraint.LISTAR_CHAMADOS);
		
		add(mainMenu, BorderLayout.NORTH);
		add(conteudo, BorderLayout.CENTER);
	}
	
	/**
	 * Carrega a barra de menu (menu do topo).
	 * @return JMenuBar 
	 */
	private JMenuBar carregaMenuTopo(){
		// Cria a barra de menu
		JMenuBar menuBar = new JMenuBar();
		
		// Cria o menu "Arquivo"
		JMenu file = new JMenu("Arquivo");
		// Cria o menu "Ações"
		JMenu actions = new JMenu("Ações");
		// Cria o menu "Ações"
		JMenu edit = new JMenu("Editar");
				
		// Cria os itens do menu "arquivo"
		JMenuItem sair = new JMenuItem("Sair",KeyEvent.VK_S);
		
		sair.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null,"Deseja Fechar?","ATENÇÃO ",javax.swing.JOptionPane.YES_NO_OPTION)==0){
					System.exit(0);
				}
			}
			
		});
		
		// Cria os itens do menu "editar"
		JMenu layout = new JMenu("Layout do Mapa");
			// Cria os submenus do edLayout
			JMenuItem newLayout  = new JMenuItem("Novo");	
			// Cria os submenus do edLayout
			JMenuItem edLayout  = new JMenuItem("Editar");
			// Cria os submenus do edLayout
			JMenuItem loadLayout  = new JMenuItem("Carregar");
			// Cria os submenus do edLayout
			JMenuItem saveLayout  = new JMenuItem("Salvar");

		layout.add(newLayout);
		layout.add(edLayout);	
		layout.add(loadLayout);	
		layout.add(saveLayout);
					
		file.add(sair);
		edit.add(layout);
		
		menuBar.add(file);
		menuBar.add(actions);
		menuBar.add(edit);
		
		// Desabilita o que ainda esta em construção
		actions.setEnabled(false);
		edit.setEnabled(false);
		return menuBar;
	}

	
	/**
	 * Carrega a barra com o menu de conteudos.
	 * @return JPanel com o menu de conteudos.
	 */
	private JPanel carregarMenuConteudos(){
		JPanel painel = new JPanel();
		painel.setBackground(Color.white);
		
		// Cria o ouvinte
		OuvinteMainMenu oc = new OuvinteMainMenu();
		// Toolkit criado para carregar as imagens
		java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
			
		// Botão de clientes
		JButton btClientes = new JButton();
		btClientes.setIcon(new ImageIcon(tk.getImage("resources/imgs/clientes.png")));
		btClientes.setBackground(Color.white);
		btClientes.setActionCommand(ClientConstraint.SIDE_MENU_CLIENTE);
		btClientes.addActionListener(oc);
		btClientes.setToolTipText("Ir para Administração de Clientes");

		// Botão de chamado
		JButton btChamados = new JButton();
		btChamados.setIcon(new ImageIcon(tk.getImage("resources/imgs/chamados.png")));
		btChamados.setBackground(Color.white);
		btChamados.setActionCommand(ClientConstraint.SIDE_MENU_CHAMADO);
		btChamados.addActionListener(oc);
		btChamados.setToolTipText("Ir para Administração de Chamados");
		
		// Botão de agenda
		JButton btAgenda = new JButton();
		btAgenda.setIcon(new ImageIcon(tk.getImage("resources/imgs/agenda.png")));
		btAgenda.setBackground(Color.white);
		btAgenda.setActionCommand(ClientConstraint.SIDE_MENU_AGENDA);
		btAgenda.addActionListener(oc);
		btAgenda.setToolTipText("Ir para Administração de Agenda");
		
		// Botão do financeiro
		JButton btFinanceiro = new JButton();
		btFinanceiro.setIcon(new ImageIcon(tk.getImage("resources/imgs/financeiro.png")));
		btFinanceiro.setBackground(Color.white);
		btFinanceiro.setActionCommand(ClientConstraint.CONTEUDO_FINANCEIRO);
		btFinanceiro.addActionListener(oc);
		btFinanceiro.setToolTipText("Ir para Financeiro");
		
		// Botão de utilidades
		JButton btUtilidades = new JButton();
		btUtilidades.setIcon(new ImageIcon(tk.getImage("resources/imgs/utilidades.png")));
		btUtilidades.setBackground(Color.white);
		btUtilidades.setActionCommand(ClientConstraint.CONTEUDO_UTILIDADES);
		btUtilidades.addActionListener(oc);
		btUtilidades.setToolTipText("Ir para Utilidades");
		
		// Adiciona os botões
		painel.add(btClientes);
		painel.add(btChamados);
		painel.add(btAgenda);
		painel.add(btFinanceiro);
		painel.add(btUtilidades);
		
		return painel;
	}
	
	
	public void changeSideMenu(String newSideMenu){
		if(sideMenu != null){
			sideMenu.setVisible(false);
			getContentPane().remove(sideMenu);
		}
		
		if(DEBUG)
			System.out.println("Change side menu: " + newSideMenu);
		
		// Fabrica o novo conteudo de acordo com a ação do botão
		sideMenu = ClientController.getInstance().getSideMenu(newSideMenu);
		
		if(sideMenu != null){
			sideMenu.setVisible(true);
			getContentPane().add(sideMenu, BorderLayout.WEST);
		}
	}
	public void openNewInternalContent(String newInternalFrame) {
		System.out.println("openNewInternalContent - " + newInternalFrame);
		
		JInternalFrame jif = ClientController.getInstance().getInternalContent(newInternalFrame);
		
		try {
			jif.setBackground(Color.BLUE);
			jif.setSize(200,200);
			jif.setLocation(100, 100);
			jif.setSelected(true);
			jif.setVisible(true);
			
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

		conteudo.add(jif);
	}
	
	/**
	 * Classe OuvinteConteudo que interpreta os cliques dos botoes do menu de conteudos.
	 */
	class OuvinteMainMenu implements ActionListener 
	{
		/**
		  @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent evt) {
			changeSideMenu(evt.getActionCommand());
		}
	}

	public static MainView getInstance() 
	{
		if(instance == null)
			instance = new MainView("SGS-TI");
		return instance;
	}

	public static void setInstance(MainView instance) {
		MainView.instance = instance;
	}
}
