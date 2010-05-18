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
	abstract boolean addChamado(Chamado chamado);


	/**
	 * M�todo que atualiza o chamado.
	 * 
	 * @param user
	 * 		Objeto Chamado.
	 * @return
	 * 		Boolean
	 */
	abstract boolean updateChamado(Chamado chamado);

	/**
	 * M�todo que verifica se o usu�rio j� existe.
	 * 
	 * @param user
	 * 		Login do Usu�rio.
	 * @return
	 * 		True se j� existe e false se n�o existe.
	 */
	abstract boolean existeChamado(String chamado);
	
	
	abstract int obterCodigo(Chamado chamado);
}
