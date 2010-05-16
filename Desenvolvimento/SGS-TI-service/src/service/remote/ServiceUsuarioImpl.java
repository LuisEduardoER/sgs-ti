package service.remote;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import service.task.UserMonitor;
import common.entity.UsuarioAutenticado;
import common.remote.ObserverUsuario;
import common.remote.ServiceUsuario;
import common.util.SystemConstant;

public class ServiceUsuarioImpl implements ServiceUsuario{
	
	private HashSet<UsuarioAutenticado> usuarioAutenticado;

	public ServiceUsuarioImpl() {
		printMsg("Criando Servico");
		// Inicializa a HashSet
		usuarioAutenticado = new HashSet<UsuarioAutenticado>();
		// Lanca o UserMonitor
		new UserMonitor(this).start();
	}
	
	
	@Override
	public boolean autenticar(ObserverUsuario observador) throws RemoteException {
		printMsg("Logando um usuário...");
		adicionarObservador(observador);
		return true;
	}

	@Override
	public void adicionarObservador(ObserverUsuario observador) throws RemoteException {
		UsuarioAutenticado novoUA = new UsuarioAutenticado();
		novoUA.setObservador(observador);
		novoUA.setUltimaAtualizacao(new Date());
		usuarioAutenticado.add(novoUA);
		printMsg("Cliente adicionado como logado...");
	}
	

	@Override
	public void notificarClient() throws RemoteException {
		Iterator<UsuarioAutenticado> it = usuarioAutenticado.iterator();
		while(it.hasNext()){
			printMsg("Matando cliente...");
			UsuarioAutenticado ua = (UsuarioAutenticado) it.next();
			ua.getObservador().suicide();
		}
	}
	
	public HashSet<UsuarioAutenticado> getUsuarioAutenticado() {
		return usuarioAutenticado;
	}


	public void setUsuarioAutenticado(HashSet<UsuarioAutenticado> usuarioAutenticado) {
		this.usuarioAutenticado = usuarioAutenticado;
	}


	@Override
	public void removerObservador(ObserverUsuario observador)
			throws RemoteException {
		printMsg("Removendo observador");
		printMsg("Usuarios:"+usuarioAutenticado.size()+", removendo...");
		
		Iterator<UsuarioAutenticado> it = usuarioAutenticado.iterator();
		while(it.hasNext()){
			UsuarioAutenticado ua = it.next();
			if(ua.getObservador().equals(observador)){
				printMsg("é igual, KILL HIM!!!");
				usuarioAutenticado.remove(ua);
				break;
			}else
				printMsg("nao é igual");
		}
		
		printMsg("Usuarios:"+usuarioAutenticado.size()+", removeu?");
	}
	
	public void printMsg(String msg){
		if(SystemConstant.DEBUG_MODE)
			System.out.println("[SERVICE USUARIO]: " + msg);
	}


	@Override
	public void autenticar() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
}
