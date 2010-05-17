package common.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import common.entity.TipoFalha;
import common.entity.StatusChamado;

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

}
