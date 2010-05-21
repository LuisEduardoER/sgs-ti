package persistencia.facade;

import common.entity.PessoaJuridica;
import common.entity.Usuario;
import common.exception.BusinessException;

import persistencia.dao.DAOClienteUsuario;
import persistencia.sql.SQLClienteUsuario;

public class FacadeClienteUsuario 
{
	
	/**
	 * Busca um objeto pelo id.
	 * @param codigo
	 * @return
	 * @throws BusinessException 
	 */
	public static PessoaJuridica getClientePJ(Usuario usuario) throws BusinessException{
		DAOClienteUsuario dao = new SQLClienteUsuario();
		return dao.getPessoaJuridicaByUserId(usuario);
	}
}
