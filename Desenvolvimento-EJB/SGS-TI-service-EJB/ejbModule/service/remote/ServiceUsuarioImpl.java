package service.remote;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import org.jboss.ejb3.annotation.RemoteBinding;
import persistencia.facade.FacadeClienteUsuario;
import persistencia.facade.FacadePessoaJuridica;
import persistencia.facade.FacadeUsuario;
import service.base.RepositorioObsUsuario;
import service.task.ThreadUserMonitor;
import common.entity.PessoaJuridica;
import common.entity.Usuario;
import common.entity.UsuarioAutenticado;
import common.exception.BusinessException;
import common.remote.ObserverUsuario;
import common.remote.ServiceUsuario;
import common.util.Utils;

@Stateless
@RemoteBinding(jndiBinding = "serviceUsuario")
public class ServiceUsuarioImpl implements ServiceUsuario{
	

	public ServiceUsuarioImpl() {
		Utils.printMsg(this.getClass().getSimpleName(),"Criando Servico.");
		// Lanca o UserMonitor
		new ThreadUserMonitor(this).start();
	}
	
	@Override
	public Usuario autenticar(ObserverUsuario observador, Usuario usuario) throws BusinessException {
		Usuario logado = FacadeUsuario.autenticarUser(usuario);
		// Valida, mas se deu erro na autenticacao vai jogar exception, nem vira aqui
		if(!Utils.isNullOrEmpty(logado)){
			verificaUsuariosLogados(logado);
			adicionarObservador(observador, logado);
		}
		return logado;
	}

	@Override
	public void adicionarObservador(ObserverUsuario observador, Usuario usuario) {
		UsuarioAutenticado novoUA = new UsuarioAutenticado();
		novoUA.setObservador(observador);
		novoUA.setUltimaAtualizacao(new Date());
		novoUA.setUsuario(usuario);
		RepositorioObsUsuario.getInstance().addObserver(novoUA); 
	}
	
	@Override
	public void removerObservador(Usuario usuario) {
		
		/*Iterator<UsuarioAutenticado> it = usuarioAutenticado.iterator();
		while(it.hasNext()){
			UsuarioAutenticado ua = it.next();
			if(ua.getUsuario().equals(usuario)){
				RepositorioObsUsuario.getInstance().removeObserver(ua);
				break;
			}
		}*/
		
		LinkedList<UsuarioAutenticado> lista = (LinkedList<UsuarioAutenticado>) RepositorioObsUsuario.getInstance().getObservers();
		for(UsuarioAutenticado uas : lista){
			UsuarioAutenticado ua = uas;
			if(ua.getUsuario().equals(usuario)){
				RepositorioObsUsuario.getInstance().removeObserver(ua);
				break;
			}
		}
		
		
	}

	@Override
	public void atualizarClient(Usuario usuario) {
		Utils.printMsg(this.getClass().getName(),"Atualizando horário atividade do cliente...");
		/*Iterator<UsuarioAutenticado> it = usuarioAutenticado.iterator();
		while(it.hasNext()){
			UsuarioAutenticado ua = it.next();
			if(ua.getUsuario().equals(usuario)){
				Utils.printMsg(this.getClass().getName(),"User encontrado, atualizando horario atual:"+ua.getUltimaAtualizacao().getTime());
				ua.setUltimaAtualizacao(new Date());
				Utils.printMsg(this.getClass().getName(),"Novo horário do usuario                   :"+ua.getUltimaAtualizacao().getTime());
				break;
			}
		}*/
		
		LinkedList<UsuarioAutenticado> lista = (LinkedList<UsuarioAutenticado>) RepositorioObsUsuario.getInstance().getObservers();
		for(UsuarioAutenticado uas : lista){
			UsuarioAutenticado ua = uas;
			if(ua.getUsuario().equals(usuario)){
				Utils.printMsg(this.getClass().getName(),"User encontrado, atualizando horario atual:"+ua.getUltimaAtualizacao().getTime());
				ua.setUltimaAtualizacao(new Date());
				Utils.printMsg(this.getClass().getName(),"Novo horário do usuario                   :"+ua.getUltimaAtualizacao().getTime());
				break;
			}
		}
	}
	
	@Override
	public void notificarTempoExcedido(UsuarioAutenticado usuarioAutenticado) throws RemoteException {
		Utils.printMsg(this.getClass().getName(),"Notificando tempo excedido");
		usuarioAutenticado.getObservador().notificarTempoExcedido();		
	}
	
	public void verificaUsuariosLogados(Usuario usuario){
		/*while(it.hasNext()){
			UsuarioAutenticado ua = (UsuarioAutenticado) it.next();
			String uaUser = ua.getUsuario().getUsername();
			String newUser = usuario.getUsername();
			if(uaUser.equals(newUser)){
				System.out.println("Ja tem na lista, removendo...");
				try {
					ua.getObservador().encerrarClient();
				} catch (RemoteException e) {
					// Se não notificou é pq perdeu conexao, somente remove da lista.
					RepositorioObsUsuario.getInstance().removeObserver(uaUser);
					break;
				}
				RepositorioObsUsuario.getInstance().removeObserver(uaUser);
				break;
			}else
				System.out.println("Nao tem na lista");
		}*/
		
		LinkedList<UsuarioAutenticado> lista = (LinkedList<UsuarioAutenticado>) RepositorioObsUsuario.getInstance().getObservers();
		for(UsuarioAutenticado uas : lista){
			UsuarioAutenticado ua = uas;
					
			String newUser = usuario.getUsername();
			if(ua.getUsuario().getUsername().equals(newUser)){
				System.out.println("Ja tem na lista, removendo...");
				try {
					ua.getObservador().encerrarClient();
				} catch (RemoteException e) {
					// Se não notificou é pq perdeu conexao, somente remove da lista.
					RepositorioObsUsuario.getInstance().removeObserver(ua);
					break;
				}
				RepositorioObsUsuario.getInstance().removeObserver(ua);
				break;
			}else
				System.out.println("Nao tem na lista");
		}
		
		
		
	}
	
	public List<PessoaJuridica> pesquisarPJ(String desc) throws BusinessException{
		return FacadePessoaJuridica.pesquisarPessoaJuridicaPorDesc(desc);
	}

	@Override
	public boolean isAlive() {
		return true;
	}

	@Override
	public PessoaJuridica pesquisarPJ(Usuario usuario) throws BusinessException {
		return FacadeClienteUsuario.getClientePJ(usuario);
	}

	@Override
	public LinkedList<UsuarioAutenticado> getUsuarioAutenticado() throws BusinessException {
		LinkedList<UsuarioAutenticado> lista = (LinkedList<UsuarioAutenticado>) RepositorioObsUsuario.getInstance().getObservers();
		return lista;
	}

}
