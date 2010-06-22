package common.remote;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Remote;
import common.entity.PessoaJuridica;
import common.entity.Usuario;
import common.entity.UsuarioAutenticado;
import common.exception.BusinessException;

@Remote
public interface ServiceUsuario 
{
	/**
	 * Metodo responsavel por autenticar um usuario
	 * @param observador
	 * @param usuario
	 * @return
	 * @throws RemoteException
	 * @throws BusinessException 
	 */
	public Usuario autenticar(ObserverUsuario observador, Usuario usuario) throws BusinessException;
	
	/**
	 * Atualiza o cliente quando ele manipulou a tela, para que n�o seja desativado
	 * @param usuario
	 * @throws RemoteException
	 */
	public void atualizarClient(Usuario usuario) throws BusinessException;
	
	/**
	 * Necessario para que o Service tenha controle sobre os clientController dos usuarios logados
	 * @param observador
	 * @param usuario
	 * @throws RemoteException
	 */
	public void adicionarObservador(ObserverUsuario observador, Usuario usuario)  throws BusinessException;
	
	/**
	 * Remove um usuario da lista de usuarios autenticados, quando faz logoff ou encerra a aplicacao
	 * @param usuario
	 * @throws RemoteException
	 */
	public void removerObservador(Usuario usuario) throws BusinessException;
	
	/**
	 * Notifica um usu�rio que o tempo de uso esta prestes a exceder
	 * @param usuarioAutenticado
	 * @throws RemoteException 
	 * @throws RemoteException
	 */
	public void notificarTempoExcedido(UsuarioAutenticado usuarioAutenticado) throws BusinessException, RemoteException;
	
	/**
	 * Retorna a lista com todos os usuario conectados
	 * @return
	 * @throws RemoteException
	 */
	public LinkedList<UsuarioAutenticado> getUsuarioAutenticado() throws BusinessException;
	
	/**
	 * Um metodo simples para verificar conexao com o serviceUsuario
	 * @return
	 * @throws RemoteException
	 */
	public boolean isAlive();
	
	/**
	 * Retorna lista de PJ
	 * @param pj
	 * @return
	 * @throws RemoteException
	 * @throws BusinessException 
	 */
	public List<PessoaJuridica> pesquisarPJ(String desc) throws BusinessException;
	
	/**
	 * Pega o cliente PJ
	 * @throws BusinessException 
	 */
	public PessoaJuridica pesquisarPJ(Usuario usuario) throws BusinessException;
}
