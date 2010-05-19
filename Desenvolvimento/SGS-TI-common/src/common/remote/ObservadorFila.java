package common.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Observer;

import common.entity.Chamado;

public interface ObservadorFila extends Remote
{
	/**
	* TODO - Documentar
	*/
	/**
	 * 
	 * @param chamados
	 * @throws RemoteException
	 */
	public void atualizarFila(List<Chamado> chamados) throws RemoteException;
	
	/**
	* TODO - Documentar
	*/
	/**
	 * 
	 * @throws RemoteException
	 */
	public void removerObservador() throws RemoteException;
	
	/**
	 * Adicionar um observer ao Observador Fila
	 * @param obs
	 */
	public void addObserverNotificacoesFila(Observer obs) throws RemoteException;
	/**
	 * Remove um observer ao Observador Fila
	 * @param obs
	 */
	public void removeObserverNotificacoesFila(Observer obs) throws RemoteException;
}
