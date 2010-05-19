package persistencia.dao;

import common.entity.StatusChamado;

public interface DAOStatus 
{
	/**
	 * M�todo que adiciona no banco de dados um novo StatusChamado.
	 * 
	 */
	abstract boolean adicionaStatus(StatusChamado status);


	/**
	 * M�todo que procura o StatusChamado.
	 * 
	 */
	abstract int procurarStatus(StatusChamado status);
}
