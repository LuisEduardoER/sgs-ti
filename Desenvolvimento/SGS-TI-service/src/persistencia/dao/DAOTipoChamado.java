package persistencia.dao;

import common.entity.TipoChamado;

public interface DAOTipoChamado {
	
	/**
	 * M�todo que adiciona no banco de dados um novo TipoChamado.
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
	 * M�todo que procura o tipoChamado.
	 * 
	 * @param user
	 * 		Objeto TipoChamado.
	 * @return
	 * 		int
	 */
	abstract int procurarTipoChamado(TipoChamado tipoChamado);

}
