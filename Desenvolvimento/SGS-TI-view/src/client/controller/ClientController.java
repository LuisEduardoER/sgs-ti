package client.controller;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import common.exception.BusinessException;
import common.remote.ObserverUsuario;
import common.remote.ServiceUsuario;
import common.util.Utils;
import client.model.internalContent.InternalContent;
import client.model.sideMenu.SideMenu;
import client.view.MainView;

public class ClientController implements ObserverUsuario, Serializable{
	
	private static final long serialVersionUID = 1L;

	private static ClientController instance;
	private ServiceUsuario serviceUsuario;
	private ObserverUsuario stubUsuario;
	
	private ClientController() {
		
		try {
			serviceUsuario = Utils.obterServiceUsuario();
			gerarStub();
			
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
	
	
	public JPanel getSideMenu(String action){
		
		if(action == null || action.equals(""))
			throw new IllegalArgumentException("Action inválido.");
		
		SideMenu sm = SideMenuFactory.getSideMenu(action);
		JPanel sideMenu = sm.inicializaComponentes();
		
		return sideMenu;
	}
	
	public JInternalFrame getInternalContent(String action){
		if(action == null || action.equals(""))
			throw new IllegalArgumentException("Action inválido.");
		
		InternalContent ic = InternalContentFactory.getInternalContent(action);
		return ic.getInternalContent();
	}
	
	public boolean autenticar(){
		try {
			return serviceUsuario.autenticar(stubUsuario);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public void atualizarCliente(){
		
		
	}

	@Override
	public void update(String evento) throws RemoteException {
		System.out.println("Lancado pelo Client:" + evento);
	}
	
	public void gerarStub() throws RemoteException{
		// Criar um stub
		stubUsuario = (ObserverUsuario) UnicastRemoteObject
				.exportObject(this, 0);
	}

	@Override
	public void suicide() throws RemoteException {
		MainView.getInstance().suicide();
	}
	
	public void encerrarSessao(){
		try {
			System.out.println("Chamando o remover:");
			serviceUsuario.removerObservador(stubUsuario);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
