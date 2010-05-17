package common.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import common.entity.Chamado;

public interface ServiceChamado extends Remote{

	/**
	 * Metodo que cadastra interessados em receber notificações de alteração na fila.
	 * @param obs
	 * 		Observador da fila.
	 * @throws RemoteException
	 */
	public void adicionarObservadorFila(ObservadorFila obs) throws RemoteException;
	
	/**
	 * Remove um observador.
	 * @param obs
	 * 		Qual observador da fila que será removido.
	 * @throws RemoteException
	 */
	public void removerObservadorFila(ObservadorFila obs) throws RemoteException;
	
	/**
	 * Metodo que notifica os interessados.
	 * @throws RemoteException
	 */
	public void notificarObservadorFila() throws RemoteException;
	
	/**
	 * Método para obter os chamados da fila.
	 * @return
	 * 		Lista de Chamados da fila.
	 * @throws RemoteException
	 */
	//public List<Chamado> obterFila() throws RemoteException;
	
	
	/**
	 * Método para cadastrar um novo chamado.
	 */
	public void cadastrarChamado(Chamado chamado) throws RemoteException;
	
	/**
	 * Método para atualizar um chamado.
	 */
	public void atualizarChamado(Chamado chamado) throws RemoteException;
}
