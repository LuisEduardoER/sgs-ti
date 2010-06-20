package persistencia.dao;

import common.entity.Porte;
import common.exception.BusinessException;

public interface DAOPorte 
{
	/**
	 * Método que adiciona no banco de dados um novo StatusChamado.
	 * 
	 */
	abstract boolean adicionaPorte(Porte porte) throws BusinessException;

	/**
	 * Metodo que procura um objeto pelo ID
	 * @param codigo
	 * @return
	 */
	abstract Porte getById(int codigo) throws BusinessException;
}
