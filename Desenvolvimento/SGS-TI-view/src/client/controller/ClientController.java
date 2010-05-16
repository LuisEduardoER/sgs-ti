package client.controller;

import java.rmi.RemoteException;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import common.exception.BusinessException;
import common.remote.ObserverUsuario;
import common.remote.ServiceUsuario;
import common.util.Utils;

import client.model.internalContent.InternalContent;
import client.model.sideMenu.SideMenu;

public class ClientController implements ObserverUsuario{
	
	private static ClientController instance;
	private ServiceUsuario serviceUsuario;
	
	private ClientController() {
		
		try {
			serviceUsuario = Utils.obterServiceUsuario();
			
			// TODO: Possivel tratamento caso n tenha conseguido conexao
			
		} catch (BusinessException e) {
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
	
	public void autenticar(){
		//Utils.obterServiceUsuario().;
	}
	
	public void atualizarCliente(){
		
		
	}

	@Override
	public void update(String evento) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}
