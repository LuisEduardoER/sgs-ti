package common.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

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
}
