package persistencia.dao;

import common.entity.HistoricoChamado;
import common.exception.BusinessException;

public interface DAOHistoricoChamado {
	
	/**
	 * Método que adiciona no banco de dados um novo HistoricoChamado.
	 * 
	 * @param chamado
	 * 		chamado.
	 * @return
	 * 		Boolean
	 */
	abstract boolean adicionaHistoricoChamado(HistoricoChamado chamado) throws BusinessException;
}
