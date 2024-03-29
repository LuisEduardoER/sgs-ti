package common.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import common.entity.Chamado;
import common.exception.BusinessException;

public interface ServiceChamado extends Remote{

	/**
	 * Metodo que cadastra interessados em receber notifica��es de altera��o na fila.
	 * @param obs
	 * 		Observador da fila.
	 * @throws RemoteException
	 */
	public void adicionarObservadorFila(ObservadorFila obs) throws RemoteException, BusinessException;
	
	/**
	 * Remove um observador.
	 * @param obs
	 * 		Qual observador da fila que ser� removido.
	 * @throws RemoteException
	 */
	public void removerObservadorFila(ObservadorFila obs) throws RemoteException;
	
	/**
	 * Metodo que notifica os interessados.
	 * @throws RemoteException
	 */
	public void notificarObservadorFila() throws RemoteException, BusinessException;
	
	/**
	 * M�todo para cadastrar um novo chamado.
	 */
	public void cadastrarChamado(Chamado chamado) throws RemoteException, BusinessException;
	
	/**
	 * Metodo para verificar se o server est� ok, e se o observador
	 * da fila est� devidamente cadastrado.
	 * @param obs
	 */
	public void verificarStatus(ObservadorFila obs) throws RemoteException,BusinessException;
	
	/**
	 * M�todo para atualizar um chamado.
	 */
	public void atualizarChamado(Chamado chamado) throws RemoteException, BusinessException;
	
	
	public void adicionarObservadorAgendamento(ObservadorAgendamento obs) throws RemoteException, BusinessException;
	
	/**
	 * Remove um observador.
	 * @param obs
	 * 		Qual observador da fila que ser� removido.
	 * @throws RemoteException
	 */
	public void removerObservadorAgendamento(ObservadorAgendamento obs) throws RemoteException, BusinessException;
	
	/**
	 * Metodo que notifica os interessados.
	 * @throws RemoteException
	 */
	public void notificarObservadorAgendamento() throws RemoteException, BusinessException;
}
