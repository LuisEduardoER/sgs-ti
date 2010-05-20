package persistencia.dao;

import common.entity.Usuario;
import common.exception.BusinessException;

/**
 * Interface do DAO
 */
public interface DAOUsuario 
{		
	/**
	 * M�todo que faz a autentica��o o usu�rio.
	 * 
	 * @param user
	 * 		Login do Usu�rio. 
	 * @param senha
	 * 		Senha criptografada.
	 * @return
	 * 		Boolean 
	 * 
	 */
	Usuario autenticar(Usuario user) throws BusinessException;
	
	/**
	 * M�todo que adiciona no banco de dados um novo usu�rio.
	 * 
	 * @param user
	 * 		Objeto Usuario.
	 * @param senha
	 * 		Senha criptografada.
	 * @return
	 * 		Boolean
	 */
	boolean addUsuario(Usuario user);


	/**
	 * M�todo que atualiza o user.
	 * 
	 * @param user
	 * 		Objeto Usuario.
	 * @return
	 * 		Boolean
	 */
	boolean updateUser(Usuario user);

	/**
	 * M�todo que verifica se o usu�rio j� existe.
	 * 
	 * @param user
	 * 		Login do Usu�rio.
	 * @return
	 * 		True se j� existe e false se n�o existe.
	 */
	boolean existeUser(String user);
	
	/**
	 * Descrever melhor os campos
	 */
	/**
	 * 
	 * @param user
	 * @return
	 */
	int obterCodigo(Usuario user);
	
	/**
	 * Busca um objeto pelo id.
	 * @param codigo
	 * @return
	 */
	Usuario getById(int codigo);
}
