package persistencia.dao;

import common.entity.Chamado;

public interface DAOChamado {
	
	/**
	 * Método que adiciona no banco de dados um novo Chamado.
	 * 
	 * @param user
	 * 		Objeto Chamado.
	 * @param chamado
	 * 		chamado.
	 * @return
	 * 		Boolean
	 */
	abstract boolean addChamado(Chamado chamado);


	/**
	 * Método que atualiza o chamado.
	 * 
	 * @param user
	 * 		Objeto Chamado.
	 * @return
	 * 		Boolean
	 */
	abstract boolean updateChamado(Chamado chamado);

	/**
	 * Método que verifica se o usuário já existe.
	 * 
	 * @param user
	 * 		Login do Usuário.
	 * @return
	 * 		True se já existe e false se não existe.
	 */
	abstract boolean existeChamado(String chamado);
	
	
	abstract int obterCodigo(Chamado chamado);
}
