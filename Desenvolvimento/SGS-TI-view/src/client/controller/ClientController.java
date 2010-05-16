package client.controller;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import client.model.internalContent.InternalContent;
import client.model.sideMenu.SideMenu;

public class ClientController {
	private static ClientController instance;
	
	private ClientController() {
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
}
