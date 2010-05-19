package common.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;

import common.entity.Usuario;
import common.entity.UsuarioAutenticado;

public interface ServiceUsuario extends Remote
{
	/**
	* TODO - Descrever melhor os campos
	*/
	/**
	 * 
	 * @param observador
	 * @param usuario
	 * @return
	 * @throws RemoteException
	 */
	public boolean autenticar(ObserverUsuario observador, Usuario usuario) throws RemoteException;
	
	/**
	* TODO - Descrever melhor os campos
	*/
	/**
	 * 
	 * @param usuario
	 * @throws RemoteException
	 */
	public void atualizarClient(Usuario usuario) throws RemoteException;
	
	/**
	* TODO - Descrever melhor os campos
	*/
	/**
	 * 
	 * @param observador
	 * @param usuario
	 * @throws RemoteException
	 */
	public void adicionarObservador(ObserverUsuario observador, Usuario usuario) throws RemoteException;
	
	/**
	* TODO - Descrever melhor os campos
	*/
	/**
	 * 
	 * @param usuario
	 * @throws RemoteException
	 */
	public void removerObservador(Usuario usuario) throws RemoteException;
	
	/**
	* TODO - Descrever melhor os campos
	*/
	/**
	 * 
	 * @param usuarioAutenticado
	 * @throws RemoteException
	 */
	public void notificarTempoExcedido(UsuarioAutenticado usuarioAutenticado) throws RemoteException;
	
	/**
	* TODO - Descrever melhor os campos
	*/
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public HashSet<UsuarioAutenticado> getUsuarioAutenticado() throws RemoteException;
	
	/**
	* TODO - Descrever melhor os campos
	*/
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public boolean isAlive() throws RemoteException;

}
