package persistencia.dao;

import common.entity.HistoricoChamado;

public interface DAOHistoricoChamado {
	
	/**
	 * Método que adiciona no banco de dados um novo HistoricoChamado.
	 * 
	 * @param chamado
	 * 		chamado.
	 * @return
	 * 		Boolean
	 */
	abstract boolean adicionaHistoricoChamado(HistoricoChamado chamado);
}
