package persistencia.dao;

import common.entity.TipoChamado;

public interface DAOTipoChamado {
	
	/**
	 * Método que adiciona no banco de dados um novo TipoChamado.
	 * 
	 * @param user
	 * 		Objeto TipoChamado.
	 * @param chamado
	 * 		tipoChamado.
	 * @return
	 * 		Boolean
	 */
	abstract boolean adicionaTipoChamado(TipoChamado tipoChamado);


	/**
	 * Método que procura o tipoChamado.
	 * 
	 * @param user
	 * 		Objeto TipoChamado.
	 * @return
	 * 		int
	 */
	abstract int procurarTipoChamado(TipoChamado tipoChamado);

}
