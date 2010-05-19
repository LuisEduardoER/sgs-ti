package persistencia.dao;

import common.entity.StatusChamado;

public interface DAOStatus 
{
	/**
	 * Método que adiciona no banco de dados um novo StatusChamado.
	 * 
	 */
	abstract boolean adicionaStatus(StatusChamado status);


	/**
	 * Método que procura o StatusChamado.
	 * 
	 */
	abstract int procurarStatus(StatusChamado status);
}
