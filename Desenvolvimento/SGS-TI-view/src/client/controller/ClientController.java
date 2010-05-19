package client.controller;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import common.entity.Chamado;
import common.entity.Usuario;
import common.entity.UsuarioAutenticado;
import common.remote.ObserverUsuario;
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
	private ObserverUsuario stubUsuario;
	private Usuario usuario;

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
	 * Este metodo tem como objetivo inicializar comnunicação com o Servidor Remoto.
	 * Caso não seja possível estabelecer conexão, a exceção deverá ser tratada.
	 * @throws RemoteException
	 */
	public boolean inicializarServidorRemoto() throws RemoteException{
		serviceUsuario = Utils.obterServiceUsuario();
		// Criar um stub
		stubUsuario = (ObserverUsuario) UnicastRemoteObject
		.exportObject(this, 0);
		return true;
	}
	/**
	 * Este metodo é utilizado pelo ThreadClientNotification para tentar
	 * re-ativar a aplicação caso falhe.
	 * @return Condição de notificação
	 * @throws RemoteException
	 */
	public boolean reinicializarServidorRemoto() throws RemoteException{
		serviceUsuario = Utils.obterServiceUsuario();
		// Criar um stub
		return autenticar(this.usuario);
	}

	/**
	 * Este metodo encerra a sessao no ServicoUsuario e já mata a aplicação
	 * mesmo que o usuário não tenha confirmado se fica online ou não.
	 *  @throws RemoteException
	 */
	@Override
	public void encerrarClient() throws RemoteException {
		encerrarSessao();
		System.exit(0);		
	}

	/**
	 * Este metodo notifica a tela principal que o tempo de inatividade esta
	 * excedendo, o usuario deverá decidir se permanece ou não.
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
	 * Metodo necessário para autenticar o usuário no Servico Usuario remoto.
	 * @throws RemoteException
	 */
	@Override
	public boolean autenticar(Usuario usuario) throws RemoteException{
		boolean acesso = serviceUsuario.autenticar(stubUsuario, usuario);
		if(acesso){
			this.usuario = usuario;
			MainView.getInstance().alterarWelcomeMsg(SystemConstant.USUARIO_LOGADO + this.usuario.getUsername());
		}
		return acesso;
	}

	/**
	 * Toda atualização do usuário irá reiniciar o tempo de inatividade no 
	 * Service Usuario remoto.
	 * @throws RemoteException
	 */
	@Override
	public void atualizarCliente() throws RemoteException {
		serviceUsuario.atualizarClient(this.usuario);
	}

	/**
	 * Tenta encerrar a sessão do usuário antes que ele saia.
	 * Se não for possível remover o usuário, deleta.
	 */
	@Override
	public void encerrarSessao(){
		try {
			serviceUsuario.removerObservador(this.usuario);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Este metodo retornar todos os usuarios contectados e autenticados.
	 * @return Todos os usuários conectados no Servico Usuário
	 * @throws RemoteException 
	 */
	public HashSet<UsuarioAutenticado> getUsuariosAutenticados() throws RemoteException{
		return serviceUsuario.getUsuarioAutenticado();
	}
	
	/**
	 * Este metodo apenas envia uma chamada remota para verificar se o server
	 * está respondendo ou não.
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
		Utils.printMsg(this.getClass().getName(), "Atualizando chamado");
	}
	
	// Metodos de controle
	public JPanel getSideMenu(String action){

		if(action == null || action.equals(""))
			throw new IllegalArgumentException("Action inválido.");

		SideMenu sm = SideMenuFactory.getSideMenu(action);
		JPanel sideMenu = sm.inicializaComponentes();

		return sideMenu;
	}

	public JInternalFrame getInternalContent(String action, Object param){
		if(action == null || action.equals(""))
			throw new IllegalArgumentException("Action inválido.");

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

	
}
