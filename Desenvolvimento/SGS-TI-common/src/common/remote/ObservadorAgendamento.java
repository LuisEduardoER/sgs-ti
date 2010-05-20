package common.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Observer;

import common.entity.Chamado;
import common.exception.BusinessException;

public interface ObservadorAgendamento extends Remote
{
	/**
	* TODO - Documentar
	*/
	/**
	 * 
	 * @param chamados
	 * @throws RemoteException
	 */
	public void atualizarFila(List<Chamado> chamados) throws RemoteException, BusinessException;
	
	/**
	* TODO - Documentar
	*/
	/**
	 * 
	 * @throws RemoteException
	 */
	public void removerObservador() throws RemoteException, BusinessException;
	
	/**
	 * Adicionar um observer ao Observador Agendamento
	 * @param obs
	 */
	public void addObserverNotificacoesAgendamento(Observer obs) throws RemoteException, BusinessException;
	/**
	 * Remove um observer ao Observador Agendamento
	 * @param obs
	 */
	public void removeObserverNotificacoesAgendamento(Observer obs) throws RemoteException, BusinessException;
}
