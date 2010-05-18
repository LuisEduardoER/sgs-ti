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
import common.exception.BusinessException;
import common.remote.ObserverUsuario;
import common.remote.ServiceChamado;
import common.remote.ServiceUsuario;
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
	private Usuario usuario;

	private boolean desativando;

	private ClientController() {

		try {
			serviceUsuario = Utils.obterServiceUsuario();
			// Criar um stub
			stubUsuario = (ObserverUsuario) UnicastRemoteObject.exportObject(this, 0);

			serviceChamado = Utils.obterServiceChamado();
			
			// TODO: Possivel tratamento caso n tenha conseguido conexao

		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ClientController getInstance() {
		if(instance == null)
			instance = new ClientController();
		return instance;
	}

	// Metodos remotos
	@Override
	public void encerrarClient() throws RemoteException {
		encerrarSessao();
		System.exit(0);		
	}

	@Override
	public void notificarTempoExcedido() throws RemoteException {
		if(!desativando)
		{
			// Controle para evitar multiplos avisos simultaneos
			desativando = true;
			// A view decide se irá fechar ou não, caso não ele volta a ser false;
			MainView.getInstance().tempoExcedido();
		}
	}

	@Override
	public boolean autenticar(Usuario usuario){
		try {
			boolean acesso = serviceUsuario.autenticar(stubUsuario, usuario);
			if(acesso)
				this.usuario = usuario;
			return acesso;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void atualizarCliente(){
		try {
			serviceUsuario.atualizarClient(this.usuario);
		} catch (RemoteException e) {
			// TODO =  Arrumar exeção
			e.printStackTrace();
		}
	}

	@Override
	public void encerrarSessao(){
		try {
			Utils.printMsg(this.getClass().getName(), "Encerrando cliente...");
			serviceUsuario.removerObservador(this.usuario);
		} catch (RemoteException e) {
			// TODO =  Arrumar exeção
			e.printStackTrace();
		}
	}

	public void atualizarChamado(Chamado chamado)
	{
		try {
			Utils.printMsg(this.getClass().getName(), "Atualizando chamado");
			serviceChamado.atualizarChamado(chamado);
		} catch (RemoteException e) {
			// TODO =  Arrumar exeção
			e.printStackTrace();
		}
	}
	
	public HashSet<UsuarioAutenticado> getStatusSistema(){
		try {
			return serviceUsuario.getUsuarioAutenticado();
		} catch (RemoteException e) {
			// TODO =  Arrumar exeção
			e.printStackTrace();
		}
		return null;
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
