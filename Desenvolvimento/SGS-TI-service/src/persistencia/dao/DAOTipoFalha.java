package persistencia.dao;

import common.entity.TipoFalha;

public interface DAOTipoFalha {
	
	/**
	 * M�todo que adiciona no banco de dados um novo TipoFalha.
	 * 
	 * @param user
	 * 		Objeto TipoFalha.
	 * @param chamado
	 * 		tipoFalha.
	 * @return
	 * 		Boolean
	 */
	abstract boolean adicionaTipoFalha(TipoFalha tipoFalha);


	/**
	 * M�todo que procura o tipoChamado.
	 * 
	 * @param user
	 * 		Objeto TipoChamado.
	 * @return
	 * 		int
	 */
	abstract int procurarTipoFalha(TipoFalha tipoFalha);
	
	
	/**
	 * Procura um objeto pelo id.
	 * @param codigo
	 * @return
	 */
	abstract TipoFalha getById(int codigo);
	

}
