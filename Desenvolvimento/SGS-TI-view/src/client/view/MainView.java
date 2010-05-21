package client.view;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import common.util.SystemConstant;
import common.util.Utils;
import client.controller.ClientController;
import client.trayIcon.JTrayIcon;
import client.util.ClientConstraint;
import cliente.task.ThreadCheckServer;

public class MainView extends JFrame {
	

	private static final long serialVersionUID = 1L;
	
	// Debug
	private final boolean DEBUG = false;
	private final String ICON_PATH = "resources/imgs/window_icon.png";
	private static MainView instance;
	private JPanel mainMenu;
	private JDesktopPane conteudo;
	private JPanel sideMenu;
	private JTrayIcon icon;
	private JLabel systemStatus;
	private JLabel welcomeMsg;

	/**
	 * Construtor.
	 * @param nome
	 * 		Nome da Janela.
	 */
	private MainView(String nome){
		super(nome);
		JFrame.setDefaultLookAndFeelDecorated(true);
		inicializaComponentes();
		ThreadCheckServer serverCheck = new ThreadCheckServer();
		serverCheck.start();
	}
	
	/**
	 * Inicializa os componentes da JFrame.
	 */
	public void inicializaComponentes(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ICON_PATH));
		setSize(new Dimension(640,480));
		setLayout(new BorderLayout(5,5));
		setBackground(Color.white);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		criarTrayIcon();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fecharJanela();
			}
			@Override
			public void windowIconified(WindowEvent e) {
				showTrayIcon();
			}
			
		});
		setJMenuBar(carregaMenuTopo());
		mainMenu = carregarMenuConteudos();
		conteudo = new JDesktopPane();
		conteudo.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		
		sideMenu = new JPanel();
		
		changeSideMenu(ClientConstraint.SIDE_MENU_CLIENTE);
		JPanel rodape = new JPanel();
		rodape.setBackground(Color.white);
		rodape.setLayout(new BorderLayout(1,1));
		systemStatus = new JLabel(SystemConstant.SYSTEM_TITLE);
		systemStatus.setFont(new Font("Arial",Font.BOLD,12));
		welcomeMsg = new JLabel("WELCOME");
		welcomeMsg.setFont(new Font("Arial",Font.BOLD,12));
		rodape.add(welcomeMsg,BorderLayout.LINE_START);
		rodape.add(systemStatus,BorderLayout.LINE_END);
		
		add(mainMenu, BorderLayout.NORTH);
		add(conteudo, BorderLayout.CENTER);
		add(rodape,BorderLayout.SOUTH);
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
				fecharJanela();
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

	public void openNewInternalContent(String newInternalFrame, Object param) {
		Utils.printMsg(this.getClass().getName(), "openNewInternalContent - " + newInternalFrame);

		JInternalFrame jif = ClientController.getInstance().getInternalContent(newInternalFrame,param);
		jif.addInternalFrameListener(new OuvinteJIF());
		
		for(JInternalFrame j : conteudo.getAllFrames()){
			j.setLayer(0);
		}
		
		jif.setLayer(new Integer(200));
		jif.setEnabled(true);
		jif.setVisible(true);

		conteudo.add(jif);
		conteudo.setVisible(true);
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

	/**
	 * Classe ouvinte de todos os JInternalFrames
	 * @author admin	 
	 */
	class OuvinteJIF implements InternalFrameListener{
		@Override
		public void internalFrameActivated(InternalFrameEvent e) {
			e.getInternalFrame().setLayer(new Integer(200));
		}
		@Override
		public void internalFrameClosed(InternalFrameEvent e) {
		}
		@Override
		public void internalFrameClosing(InternalFrameEvent e) {	
		}
		@Override
		public void internalFrameDeactivated(InternalFrameEvent e) {
			e.getInternalFrame().setLayer(0);
		}
		@Override
		public void internalFrameDeiconified(InternalFrameEvent e) {
		}
		@Override
		public void internalFrameIconified(InternalFrameEvent e) {
		}
		@Override
		public void internalFrameOpened(InternalFrameEvent e) {
		}
	}
	
	/**
	 * Pegar instancia
	 * @return
	 * 		MainView
	 */
	public static MainView getInstance() 
	{
		if(instance == null)
			instance = new MainView(SystemConstant.SYSTEM_TITLE);
		return instance;
	}

	public static void setInstance(MainView instance) {
		MainView.instance = instance;
	}

	public void tempoExcedido(){
		if(JOptionPane.showConfirmDialog(null,"O tempo máximo de inatividade irá exceder em "+SystemConstant.TEMPO_PARA_DESLIGAR+" , \r\n" + 
				"Caso em NÃO para permanecer ou SIM para encerrar.","ATENÇÃO ",javax.swing.JOptionPane.YES_NO_OPTION)==0){
			ClientController.getInstance().encerrarSessao();
			System.exit(0);
		}else
		{
			// Atualiza o horario de ultimaAtualizacao e notifica que não será desativado.
			ClientController.getInstance().setDesativando(false);
			try {
				ClientController.getInstance().atualizarCliente();
			} catch (RemoteException e) {
				mostrarMensagemErroRemoto();
			}
		}
	}

	public void fecharJanela(){
		if(JOptionPane.showConfirmDialog(null,"Deseja Fechar?","ATENÇÃO ",javax.swing.JOptionPane.YES_NO_OPTION)==0){
			ClientController.getInstance().encerrarSessao();
			System.exit(0);
		} else
			try {
				ClientController.getInstance().atualizarCliente();
			} catch (RemoteException e) {
				mostrarMensagemErroRemoto();
			}
	}

	/*Funções do TrayIcon*/
	public void showTrayIcon(){
		if(SystemTray.isSupported() && icon != null){
			this.setVisible(false);
			
			try {
				SystemTray.getSystemTray().add(icon);
				
			} catch (AWTException e1) {
				// TODO tratar exception
				e1.printStackTrace();
			}
			ClientController.getInstance().addObserverNotificacoesFila(icon);
			icon.displayMessage("SGS-TI", "Aplicativo continua rodando...",TrayIcon.MessageType.INFO);
		}
	}
	public void removeIcon(){
		if(SystemTray.isSupported() && icon != null){
			ClientController.getInstance().removeObserverNotificacoesFila(icon);
			
			SystemTray.getSystemTray().remove(icon);
			this.setVisible(true);
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
	}
	public void criarTrayIcon(){
		if(SystemTray.isSupported()){			
			
			icon = new JTrayIcon(getImage(),"SGS-TI", createPopupMenu());
			icon.setImageAutoSize(true);
			icon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					removeIcon();				
				}
			});
		}
	}	
	private Image getImage(){
		return Toolkit.getDefaultToolkit().getImage(ICON_PATH);
	}
	private PopupMenu createPopupMenu(){

		PopupMenu menu = new PopupMenu();
		MenuItem exit = new MenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fecharJanela();
			}
		});
		menu.add(exit);
		return menu;
	}
	
	public void mostrarMensagemPersonalizada(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}
	
	public void alterarStatusSistema(boolean status){
		if(status){
			this.systemStatus.setForeground(new Color(155,206,0));
			this.systemStatus.setText(SystemConstant.SERVER_STATUS + " " + SystemConstant.STATUS_ONLINE);
		}else{
			this.systemStatus.setForeground(Color.RED);
			this.systemStatus.setText(SystemConstant.SERVER_STATUS + " " + SystemConstant.STATUS_OFFLINE);
		}
	}
	
	public void mostrarMensagemErroRemoto(){
		JOptionPane.showMessageDialog(null, SystemConstant.MSG_AM_SEM_CONEXAO_REMOTA);
	}
	
	public void alterarWelcomeMsg(String msg){
		this.welcomeMsg.setText(msg);
	}
	
	public JLabel getSystemStatus() {
		return systemStatus;
	}

	public void setSystemStatus(JLabel systemStatus) {
		this.systemStatus = systemStatus;
	}
	
	public JLabel getWelcomeMsg() {
		return welcomeMsg;
	}

	public void setWelcomeMsg(JLabel welcomeMsg) {
		this.welcomeMsg = welcomeMsg;
	}

	
}
