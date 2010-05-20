package persistencia.dao;

import common.entity.Usuario;
import common.exception.BusinessException;

/**
 * Interface do DAO
 */
public interface DAOUsuario 
{		
	/**
	 * Método que faz a autenticação o usuário.
	 * 
	 * @param user
	 * 		Login do Usuário. 
	 * @param senha
	 * 		Senha criptografada.
	 * @return
	 * 		Boolean 
	 * 
	 */
	Usuario autenticar(Usuario user) throws BusinessException;
	
	/**
	 * Método que adiciona no banco de dados um novo usuário.
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
	 * Método que atualiza o user.
	 * 
	 * @param user
	 * 		Objeto Usuario.
	 * @return
	 * 		Boolean
	 */
	boolean updateUser(Usuario user);

	/**
	 * Método que verifica se o usuário já existe.
	 * 
	 * @param user
	 * 		Login do Usuário.
	 * @return
	 * 		True se já existe e false se não existe.
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
