package persistencia.dao;

import common.entity.TipoChamado;
import common.exception.BusinessException;

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
	abstract boolean adicionaTipoChamado(TipoChamado tipoChamado) throws BusinessException;


	/**
	 * Método que procura o tipoChamado.
	 * 
	 * @param user
	 * 		Objeto TipoChamado.
	 * @return
	 * 		int
	 */
	abstract int procurarTipoChamado(TipoChamado tipoChamado) throws BusinessException;
	
	/**
	 * Procura um tipo de chamado pelo id.
	 * @param codigo
	 * @return
	 */
	abstract TipoChamado getById(int codigo) throws BusinessException;

}
