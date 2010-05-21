package persistencia.dao;

import java.util.List;

import common.entity.TipoFalha;
import common.exception.BusinessException;

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
	abstract boolean adicionaTipoFalha(TipoFalha tipoFalha) throws BusinessException;


	/**
	 * Método que procura o tipoChamado.
	 * 
	 * @param user
	 * 		Objeto TipoChamado.
	 * @return
	 * 		int
	 */
	abstract int procurarTipoFalha(TipoFalha tipoFalha) throws BusinessException;
	
	
	/**
	 * Procura um objeto pelo id.
	 * @param codigo
	 * @return
	 */
	abstract TipoFalha getById(int codigo) throws BusinessException;
	
	
	abstract List<TipoFalha> listarTodos() throws BusinessException;
	

}
