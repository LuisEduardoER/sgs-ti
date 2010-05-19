package persistencia.dao;

import common.entity.TipoFalha;

public interface DAOTipoFalha {
	
	/**
	 * Método que adiciona no banco de dados um novo TipoFalha.
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
	 * Método que procura o tipoChamado.
	 * 
	 * @param user
	 * 		Objeto TipoChamado.
	 * @return
	 * 		int
	 */
	abstract int procurarTipoFalha(TipoFalha tipoFalha);

}
