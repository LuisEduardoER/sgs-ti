package common.remote;

import java.rmi.RemoteException;

import javax.ejb.Remote;

import common.entity.Chamado;
import common.exception.BusinessException;

@Remote
public interface ServiceChamado
{

	/**
	 * Metodo que cadastra interessados em receber notifica��es de altera��o na fila.
	 * @param obs
	 * 		Observador da fila.
	 * @throws BusinessException
	 * @throws RemoteException 
	 */
	public void adicionarObservadorFila(ObservadorFila obs) throws BusinessException, RemoteException;
	
	/**
	 * Remove um observador.
	 * @param obs
	 * 		Qual observador da fila que ser� removido
	 */
	public void removerObservadorFila(ObservadorFila obs);
	
	/**
	 * Metodo que notifica os interessados.
	 * @throws BusinessException
	 */
	public void notificarObservadorFila() throws BusinessException;
	
	/**
	 * M�todo para cadastrar um novo chamado.
	 * @throws BusinessException
	 */
	public void cadastrarChamado(Chamado chamado) throws BusinessException;
	
	/**
	 * Metodo para verificar se o server est� ok, e se o observador
	 * da fila est� devidamente cadastrado.
	 * @param obs
	 * @throws BusinessException
	 * @throws RemoteException 
	 */
	public void verificarStatus(ObservadorFila obs) throws BusinessException, RemoteException;
	
	/**
	 * M�todo para atualizar um chamado.
	 * @param chamado
	 * @throws BusinessException
	 */
	public void atualizarChamado(Chamado chamado) throws BusinessException;
	
	/**
	 * 
	 * @param obs
	 * @throws BusinessException
	 * @throws RemoteException 
	 */
	public void adicionarObservadorAgendamento(ObservadorAgendamento obs) throws BusinessException, RemoteException;
	
	/**
	 * Remove um observador.
	 * @param obs
	 * 		Qual observador da fila que ser� removido.
	 * @throws BusinessException
	 */
	public void removerObservadorAgendamento(ObservadorAgendamento obs) throws BusinessException;
	
	/**
	 * Metodo que notifica os interessados.
	 * @throws BusinessException
	 */
	public void notificarObservadorAgendamento() throws BusinessException;
}
