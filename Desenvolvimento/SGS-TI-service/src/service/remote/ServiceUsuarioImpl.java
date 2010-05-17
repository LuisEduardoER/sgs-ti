package service.remote;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import service.task.ThreadUserMonitor;
import common.entity.Usuario;
import common.entity.UsuarioAutenticado;
import common.remote.ObserverUsuario;
import common.remote.ServiceUsuario;
import common.util.Utils;

public class ServiceUsuarioImpl implements ServiceUsuario{
	
	private HashSet<UsuarioAutenticado> usuarioAutenticado;

	public ServiceUsuarioImpl() {
		Utils.printMsg(this.getClass().getSimpleName(),"Criando Servico.");
		// Inicializa a HashSet
		usuarioAutenticado = new HashSet<UsuarioAutenticado>();
		// Lanca o UserMonitor
		new ThreadUserMonitor(this).start();
	}
	
	@Override
	public boolean autenticar(ObserverUsuario observador, Usuario usuario) throws RemoteException {
		adicionarObservador(observador, usuario);
		return true;
	}

	@Override
	public void adicionarObservador(ObserverUsuario observador, Usuario usuario) throws RemoteException {
		UsuarioAutenticado novoUA = new UsuarioAutenticado();
		novoUA.setObservador(observador);
		novoUA.setUltimaAtualizacao(new Date());
		novoUA.setUsuario(usuario);
		usuarioAutenticado.add(novoUA);
	}
	
	@Override
	public void removerObservador(Usuario usuario)
			throws RemoteException {
		Iterator<UsuarioAutenticado> it = usuarioAutenticado.iterator();
		while(it.hasNext()){
			UsuarioAutenticado ua = it.next();
			if(ua.getUsuario().equals(usuario)){
				usuarioAutenticado.remove(ua);
				break;
			}
		}
	}

	@Override
	public void atualizarClient(Usuario usuario) throws RemoteException {
		Utils.printMsg(this.getClass().getName(),"Atualizando horário atividade do cliente...");
		Iterator<UsuarioAutenticado> it = usuarioAutenticado.iterator();
		while(it.hasNext()){
			UsuarioAutenticado ua = it.next();
			if(ua.getUsuario().equals(usuario)){
				Utils.printMsg(this.getClass().getName(),"User encontrado, atualizando horario atual:"+ua.getUltimaAtualizacao().getTime());
				ua.setUltimaAtualizacao(new Date());
				Utils.printMsg(this.getClass().getName(),"Novo horário do usuario                   :"+ua.getUltimaAtualizacao().getTime());
				break;
			}
		}
	}
	
	@Override
	public void notificarTempoExcedido(UsuarioAutenticado usuarioAutenticado)
			throws RemoteException {
		Utils.printMsg(this.getClass().getName(),"Notificando tempo excedido");
		usuarioAutenticado.getObservador().notificarTempoExcedido();		
	}
	
	@Override
	public HashSet<UsuarioAutenticado> getUsuarioAutenticado() {
		return usuarioAutenticado;
	}

	public void setUsuarioAutenticado(HashSet<UsuarioAutenticado> usuarioAutenticado) {
		this.usuarioAutenticado = usuarioAutenticado;
	}

}
