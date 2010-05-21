package client.controller;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Observer;
import java.util.Set;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import common.entity.Chamado;
import common.entity.PessoaFisica;
import common.entity.PessoaJuridica;
import common.entity.Usuario;
import common.entity.UsuarioAutenticado;
import common.exception.BusinessException;
import common.remote.ObservadorFila;
import common.remote.ObserverUsuario;
import common.remote.ServiceChamado;
import common.remote.ServiceUsuario;
import common.util.SystemConstant;
import common.util.Utils;
import client.model.internalContent.InternalContent;
import client.model.sideMenu.SideMenu;
import client.view.MainView;

public class ClientController implements ObserverUsuario, Serializable{

	private static final long serialVersionUID = 1L;
	private static ClientController instance;
	private ServiceUsuario serviceUsuario;
	private ServiceChamado serviceChamado;
	private ObserverUsuario stubUsuario;
	private ObservadorFila observerFila;
	private Usuario usuario;
	private PessoaFisica pf;
	private PessoaJuridica pj;
	private boolean desativando;

	private ClientController() {
		this.usuario = new Usuario();
	}

	public static ClientController getInstance() {
		if(instance == null)
			instance = new ClientController();
		return instance;
	}

	/**
	 * Este metodo tem como objetivo inicializar comnunica��o com o Servidor Remoto.
	 * Caso n�o seja poss�vel estabelecer conex�o, a exce��o dever� ser tratada.
	 * @throws RemoteException
	 */
	public boolean inicializarServidorRemoto() throws RemoteException{
		try {
			if(Utils.isNullOrEmpty(serviceUsuario)){
				serviceUsuario = Utils.obterServiceUsuario();
			}
			if(Utils.isNullOrEmpty(serviceChamado)){
				serviceChamado = Utils.obterServiceChamado();
			}
			if(Utils.isNullOrEmpty(stubUsuario)){
				stubUsuario = (ObserverUsuario) UnicastRemoteObject
				.exportObject(this, 0);
			}

			return true;
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * Este metodo � utilizado pelo ThreadClientNotification para tentar
	 * re-ativar a aplica��o caso falhe.
	 * @return Condi��o de notifica��o
	 * @throws RemoteException 
	 * @throws BusinessException 
	 */
	public boolean reinicializarServidorRemoto() throws RemoteException{
		serviceUsuario = Utils.obterServiceUsuario();
		// Criar um stub
		try {
			return autenticar(this.usuario);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			mostrarMensagem(e.getMessage());
			return false;
		}
	
	}

	/**
	 * Este metodo encerra a sessao no ServicoUsuario e j� mata a aplica��o
	 * mesmo que o usu�rio n�o tenha confirmado se fica online ou n�o.
	 *  @throws RemoteException
	 */
	@Override
	public void encerrarClient() throws RemoteException {
		encerrarSessao();
		System.exit(0);		
	}

	/**
	 * Este metodo notifica a tela principal que o tempo de inatividade esta
	 * excedendo, o usuario dever� decidir se permanece ou n�o.
	 *  @throws RemoteException
	 */
	@Override
	public void notificarTempoExcedido() throws RemoteException {
		if(!desativando)
		{
			// Controle para evitar multiplos avisos simultaneos
			desativando = true;
			MainView.getInstance().tempoExcedido();
		}
	}

	/**
	 * Metodo necess�rio para autenticar o usu�rio no Servico Usuario remoto.
	 * @throws RemoteException
	 * @throws BusinessException 
	 */
	@Override
	public boolean autenticar(Usuario usuario) throws RemoteException, BusinessException{

		this.usuario = serviceUsuario.autenticar(stubUsuario, usuario);

		System.out.println(this.usuario.getNome());
		if(!Utils.isNullOrEmpty(this.usuario)){
			MainView.getInstance().alterarWelcomeMsg(SystemConstant.USUARIO_LOGADO + this.usuario.getUsername());
			// Pega o cliente;
			pj= serviceUsuario.pesquisarPJ(this.usuario);
			return true;
		}
		return false;
	}

	/**
	 * Toda atualiza��o do usu�rio ir� reiniciar o tempo de inatividade no 
	 * Service Usuario remoto.
	 * @throws RemoteException
	 */
	@Override
	public void atualizarCliente() throws RemoteException {
		try {
			serviceUsuario.atualizarClient(this.usuario);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			mostrarMensagem(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Tenta encerrar a sess�o do usu�rio antes que ele saia.
	 * Se n�o for poss�vel remover o usu�rio, deleta.
	 */
	@Override
	public void encerrarSessao(){
		try {
			serviceUsuario.removerObservador(this.usuario);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			mostrarMensagem(e.getMessage());
		}
	}

	/**
	 * Este metodo retornar todos os usuarios contectados e autenticados.
	 * @return Todos os usu�rios conectados no Servico Usu�rio
	 * @throws RemoteException 
	 */
	public Set<UsuarioAutenticado> getUsuariosAutenticados() throws RemoteException{
		try {
			return serviceUsuario.getUsuarioAutenticado();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			mostrarMensagem(e.getMessage());
			return null;
		}
	}

	/**
	 * Este metodo apenas envia uma chamada remota para verificar se o server
	 * est� respondendo ou n�o.
	 */
	@Override
	public boolean checkServerStatus() throws RemoteException {
		return serviceUsuario.isAlive();

	}

	/**
	 * Este metodo apenas envia mensagem de algum lugar para a tela principal.
	 */
	@Override
	public void mostrarMensagem(String msg){
		MainView.getInstance().mostrarMensagemPersonalizada(msg);
	}

	public void atualizarChamado(Chamado chamado)
	{
		try {
			Utils.printMsg(this.getClass().getName(), "Atualizando chamado");
			serviceChamado.atualizarChamado(chamado);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			mostrarMensagem(e.getMessage());
		}
	}
	
	/**
	 * Ativa o observador da fila, para receber notifica��es do servi�o remoto.
	 * @param obs
	 * 		Observer interessado em receber o notify
	 */
	public void ativarObservadorFila(Observer obs){
		try {
			this.observerFila = new ObservadorFilaImpl(obs);
			atualizarCliente();
			
		} catch (BusinessException e) {
			Utils.printErro(this.getClass().getName(), e);
			mostrarMensagem(e.getMessage());
			
		} catch (RemoteException e) {
			Utils.printErro(this.getClass().getName(), e);
			mostrarMensagem(SystemConstant.MSG_AM_ATUALIZAR_STATUS);
		}
	}
	
	/**
	 * Desativa o observador da fila, para parar de receber notifica��es do servi�o remoto.
	 * @param obs
	 * 		Observer que ser� removido.
	 */
	public void desativarObservadorFila(Observer obs){
		try {
			if(this.observerFila!= null){
				this.observerFila.removerObservador();
				atualizarCliente();
			}
			
		} catch (RemoteException e) {
			mostrarMensagem("Erro ao conectar com o servi�o de chamados.");
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			mostrarMensagem(e.getMessage());
		}	
	}
	
	/**
	 * Adicionar um observer ao Observador Fila
	 * @param obs
	 * 		Observer interessado em receber o notify
	 */
	public void addObserverNotificacoesFila(Observer obs){
		try {
			if(observerFila!=null){
				this.observerFila.addObserverNotificacoesFila(obs);
				
			}
			System.out.println("add obs");
		} catch (RemoteException e) {
			mostrarMensagem("N�o foi poss�vel adicionar observador da fila de chamados.");
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			mostrarMensagem(e.getMessage());
		}
	}
	/**
	 * Remove um observer ao Observador Fila
	 * @param obs
	 * 		Observer que ser� removido
	 */
	public void removeObserverNotificacoesFila(Observer obs){
		try {
			if(observerFila!=null)
				this.observerFila.removeObserverNotificacoesFila(obs);
		} catch (RemoteException e) {
			mostrarMensagem("N�o foi poss�vel remover observador da fila de chamados.");
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			mostrarMensagem(e.getMessage());
		}
	}
	
	public List<PessoaJuridica> pesquisarPJ(String descricao) throws RemoteException{
		try {
			return serviceUsuario.pesquisarPJ(descricao);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			mostrarMensagem(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Cria um chamado e adiciona no banco e na lista de chamados
	 * @throws RemoteException
	 */
	@Override
	public void criarChamado(Chamado chamado) throws RemoteException{
		try {
			serviceChamado.cadastrarChamado(chamado);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			mostrarMensagem(e.getMessage());
		}
	}

	// Metodos de controle
	public JPanel getSideMenu(String action){

		if(action == null || action.equals(""))
			throw new IllegalArgumentException("Action inv�lido.");

		SideMenu sm = SideMenuFactory.getSideMenu(action);
		JPanel sideMenu = sm.inicializaComponentes();

		return sideMenu;
	}

	public JInternalFrame getInternalContent(String action, Object param){
		if(action == null || action.equals(""))
			throw new IllegalArgumentException("Action inv�lido.");

		InternalContent ic = InternalContentFactory.getInternalContent(action);
		Utils.printMsg(this.getClass().getName(), "Fabricado nova InternalContent - " + ic.getClass().getName());

		return ic.getInternalContent(param);
	}


	public boolean isDesativando() {
		return desativando;
	}

	public void setDesativando(boolean desativando) {
		this.desativando = desativando;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public PessoaFisica getPf() {
		return pf;
	}

	public void setPf(PessoaFisica pf) {
		this.pf = pf;
	}

	public PessoaJuridica getPj() {
		return pj;
	}

	public void setPj(PessoaJuridica pj) {
		this.pj = pj;
	}


	
}
