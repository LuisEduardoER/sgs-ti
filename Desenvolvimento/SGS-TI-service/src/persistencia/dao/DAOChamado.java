package persistencia.dao;

import common.entity.Chamado;

public interface DAOChamado {
	
	/**
	 * M�todo que adiciona no banco de dados um novo Chamado.
	 * 
	 * @param user
	 * 		Objeto Chamado.
	 * @param chamado
	 * 		chamado.
	 * @return
	 * 		Boolean
	 */
	abstract boolean adicionaChamado(Chamado chamado);


	/**
	 * M�todo que atualiza o chamado.
	 * 
	 * @param user
	 * 		Objeto Chamado.
	 * @return
	 * 		Boolean
	 */
	abstract boolean atualizarChamado(Chamado chamado);

}
