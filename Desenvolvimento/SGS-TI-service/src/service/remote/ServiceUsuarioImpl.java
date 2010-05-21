package service.remote;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import persistencia.facade.FacadeClienteUsuario;
import persistencia.facade.FacadePessoaJuridica;
import persistencia.facade.FacadeUsuario;
import service.task.ThreadUserMonitor;
import common.entity.PessoaJuridica;
import common.entity.Usuario;
import common.entity.UsuarioAutenticado;
import common.exception.BusinessException;
import common.remote.ObserverUsuario;
import common.remote.ServiceUsuario;
import common.util.Utils;

public class ServiceUsuarioImpl implements ServiceUsuario{
	
	private Set<UsuarioAutenticado> usuarioAutenticado;

	public ServiceUsuarioImpl() {
		Utils.printMsg(this.getClass().getSimpleName(),"Criando Servico.");
		// Inicializa a HashSet
		usuarioAutenticado = Collections.synchronizedSet(new HashSet<UsuarioAutenticado>());
		// Lanca o UserMonitor
		new ThreadUserMonitor(this).start();
	}
	
	@Override
	public Usuario autenticar(ObserverUsuario observador, Usuario usuario) throws RemoteException, BusinessException {
		Usuario logado = FacadeUsuario.autenticarUser(usuario);
		// Valida, mas se deu erro na autenticacao vai jogar exception, nem vira aqui
		if(!Utils.isNullOrEmpty(logado))
			adicionarObservador(observador, logado);
		return logado;
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
	public Set<UsuarioAutenticado> getUsuarioAutenticado() throws RemoteException {
		return usuarioAutenticado;
	}
	
	public List<PessoaJuridica> pesquisarPJ(String desc) throws RemoteException, BusinessException{
		return FacadePessoaJuridica.pesquisarPessoaJuridicaPorDesc(desc);
	}

	public void setUsuarioAutenticado(HashSet<UsuarioAutenticado> usuarioAutenticado) {
		this.usuarioAutenticado = usuarioAutenticado;
	}

	@Override
	public boolean isAlive() {
		return true;
	}

	@Override
	public PessoaJuridica pesquisarPF(Usuario usuario) throws RemoteException {
		// nao implementado devido ausencia do modulo cliente
		return null;
	}

	@Override
	public PessoaJuridica pesquisarPJ(Usuario usuario) throws RemoteException, BusinessException {
		return FacadeClienteUsuario.getClientePJ(usuario);
	}

}
