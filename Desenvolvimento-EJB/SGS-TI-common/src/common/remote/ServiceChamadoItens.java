package common.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import common.entity.TipoChamado;
import common.entity.TipoFalha;
import common.entity.StatusChamado;
import common.exception.BusinessException;

public interface ServiceChamadoItens extends Remote{
	
	/**
	 * Metodo que retorna todos os tipo de falha
	 * @return List<Falha>
	 * @throws RemoteException
	 */
	public List<TipoFalha> procurarFalha() throws RemoteException;
	
	/**
	 * Metodo que retorna todos os tipo de status
	 * @return List<StatusChamado>
	 * @throws RemoteException
	 */
	public List<StatusChamado> procurarStatus() throws RemoteException;
	
	/**
	 * procura chamados
	 * @return
	 * @throws RemoteException
	 * @throws BusinessException 
	 */
	public List<TipoChamado> procurarTipoChamado() throws RemoteException, BusinessException;
	
	public List<TipoChamado> tipoChamadoListarTodos() throws RemoteException, BusinessException;
	
	public List<TipoFalha> tipoFalhaListarTodos() throws RemoteException, BusinessException;

}
