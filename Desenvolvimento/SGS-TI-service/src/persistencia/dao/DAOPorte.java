package persistencia.dao;

import common.entity.Porte;

public interface DAOPorte 
{
	/**
	 * Método que adiciona no banco de dados um novo StatusChamado.
	 * 
	 */
	abstract boolean adicionaPorte(Porte porte);

	/**
	 * Metodo que procura um objeto pelo ID
	 * @param codigo
	 * @return
	 */
	abstract Porte getById(int codigo);
}
