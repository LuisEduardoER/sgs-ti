package persistencia.dao;

import java.util.List;

import common.entity.StatusChamado;
import common.exception.BusinessException;

public interface DAOStatus 
{
	/**
	 * Método que adiciona no banco de dados um novo StatusChamado.
	 * 
	 */
	abstract boolean adicionaStatus(StatusChamado status) throws BusinessException;


	/**
	 * Método que procura o StatusChamado.
	 * 
	 */
	abstract int procurarStatus(StatusChamado status) throws BusinessException;
	
	/**
	 * Metodo que procura um status pelo ID
	 * @param codigo
	 * @return
	 */
	abstract StatusChamado getById(int codigo) throws BusinessException;
	
	/**
	 * Lista todos status
	 * @return
	 * @throws BusinessException
	 */
	abstract List<StatusChamado> listarTodos() throws BusinessException;
}
