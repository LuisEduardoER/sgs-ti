package persistencia.dao;

import common.entity.PessoaFisica;
import common.entity.PessoaJuridica;
import common.entity.Usuario;
import common.exception.BusinessException;

/**
 * Interface do DAO
 */
public interface DAOClienteUsuario 
{		
	/**
	 * Busca um objeto pelo id.
	 * @param codigo
	 * @return
	 * @throws BusinessException 
	 */
	PessoaJuridica getPessoaJuridicaByUserId(Usuario codigo) throws BusinessException;

	PessoaFisica getPessoaFisicaByUserId(Usuario codigo) throws BusinessException;
}
