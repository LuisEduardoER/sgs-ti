package common.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import common.entity.Cliente;
import common.entity.PessoaJuridica;
import common.entity.Usuario;
import common.entity.UsuarioAutenticado;
import common.exception.BusinessException;

public interface ServiceUsuario extends Remote
{
	/**
	 * Metodo responsavel por autenticar um usuario
	 * @param observador
	 * @param usuario
	 * @return
	 * @throws RemoteException
	 * @throws BusinessException 
	 */
	public boolean autenticar(ObserverUsuario observador, Usuario usuario) throws RemoteException, BusinessException;
	
	/**
	 * Atualiza o cliente quando ele manipulou a tela, para que não seja desativado
	 * @param usuario
	 * @throws RemoteException
	 */
	public void atualizarClient(Usuario usuario) throws RemoteException;
	
	/**
	 * Necessario para que o Service tenha controle sobre os clientController dos usuarios logados
	 * @param observador
	 * @param usuario
	 * @throws RemoteException
	 */
	public void adicionarObservador(ObserverUsuario observador, Usuario usuario) throws RemoteException;
	
	/**
	 * Remove um usuario da lista de usuarios autenticados, quando faz logoff ou encerra a aplicacao
	 * @param usuario
	 * @throws RemoteException
	 */
	public void removerObservador(Usuario usuario) throws RemoteException;
	
	/**
	 * Notifica um usuário que o tempo de uso esta prestes a exceder
	 * @param usuarioAutenticado
	 * @throws RemoteException
	 */
	public void notificarTempoExcedido(UsuarioAutenticado usuarioAutenticado) throws RemoteException;
	
	/**
	 * Retorna a lista com todos os usuario conectados
	 * @return
	 * @throws RemoteException
	 */
	public Set<UsuarioAutenticado> getUsuarioAutenticado() throws RemoteException;
	
	/**
	 * Um metodo simples para verificar conexao com o serviceUsuario
	 * @return
	 * @throws RemoteException
	 */
	public boolean isAlive() throws RemoteException;
	
	/**
	 * Retorna lista de PJ
	 * @param pj
	 * @return
	 * @throws RemoteException
	 */
	public List<PessoaJuridica> pesquisarPJ(String desc) throws RemoteException;

}
