package persistencia.dao;

import common.entity.StatusChamado;
import common.exception.BusinessException;

public interface DAOStatus 
{
	/**
	 * M�todo que adiciona no banco de dados um novo StatusChamado.
	 * 
	 */
	abstract boolean adicionaStatus(StatusChamado status) throws BusinessException;


	/**
	 * M�todo que procura o StatusChamado.
	 * 
	 */
	abstract int procurarStatus(StatusChamado status) throws BusinessException;
	
	/**
	 * Metodo que procura um status pelo ID
	 * @param codigo
	 * @return
	 */
	abstract StatusChamado getById(int codigo) throws BusinessException;
}
