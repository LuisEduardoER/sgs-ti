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

	@Override
	public void atualizarClient(ObserverUsuario observador) throws RemoteException {
		printMsg("Atualizando horário atividade do cliente...");
		Iterator<UsuarioAutenticado> it = usuarioAutenticado.iterator();
		while(it.hasNext()){
			UsuarioAutenticado ua = it.next();
			if(ua.getObservador().equals(observador)){
				printMsg("User encontrado, atualizando horario atual:"+ua.getUltimaAtualizacao().getTime());
				ua.setUltimaAtualizacao(new Date());
				printMsg("Novo horário do usuario                   :"+ua.getUltimaAtualizacao().getTime());
				break;
			}
		}
	}
	
	@Override
	public void notificarTempoExcedido(UsuarioAutenticado usuarioAutenticado)
			throws RemoteException {
		printMsg("Notificando tempo excedido");
		usuarioAutenticado.getObservador().notificarTempoExcedido();		
	}
	
	public void printMsg(String msg){
		if(SystemConstant.DEBUG_MODE)
			System.out.println("[SERVICE USUARIO]: " + msg);
	}


	public HashSet<UsuarioAutenticado> getUsuarioAutenticado() {
		return usuarioAutenticado;
	}


	public void setUsuarioAutenticado(HashSet<UsuarioAutenticado> usuarioAutenticado) {
		this.usuarioAutenticado = usuarioAutenticado;
	}
}
