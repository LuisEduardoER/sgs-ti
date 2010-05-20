package persistencia.facade;

import persistencia.dao.DAOUsuario;
import persistencia.sql.SQLUsuario;
import common.entity.Usuario;
import common.util.MD5Encryption;

public class FacadeUsuario 
{
	
	/**
	 * TODO - Descrever melhor os campos
	 */
	/**
	 * 	
	 * @param user
	 * @return
	 */
	public static boolean criarUser(Usuario user)
	{	
		// Usa o utilitario para criptografar a senha
		user.setPassword( MD5Encryption.encript(user.getPassword()) );
		
		DAOUsuario dao = new SQLUsuario();
		
		boolean inserido = dao.addUsuario(user);
		return inserido;
	}
	
	/**
	 * TODO - Descrever melhor os campos
	 */
	/**
	 * 
	 * @param user
	 * @return
	 */
	public static boolean autenticarUser(Usuario user)
	{
		// Usa o utilitario para criptografar a senha
		user.setPassword( MD5Encryption.encript(user.getPassword()) );
		
		DAOUsuario dao = new SQLUsuario();
		
		return dao.autenticar(user);
	}

	/**
	 * TODO - Descrever melhor os campos
	 */
	/**
	 * 
	 * @param user
	 * @return
	 */
	public static boolean atualizarPerfil(Usuario user) 
	{
		// Usa o utilitario para criptografar a senha
		user.setPassword( MD5Encryption.encript(user.getPassword()) );
		
		DAOUsuario dao = new SQLUsuario();
		boolean atualizado = dao.updateUser(user);
		
		return atualizado;
	}
	
	/**
	 * TODO - Descrever melhor os campos
	 */
	/**
	 * 
	 * @param username
	 * @return
	 */
	public static boolean verificarUsername(String username){
		DAOUsuario dao = new SQLUsuario();
		return dao.existeUser(username);
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public static int obterCodigo(Usuario user){
		DAOUsuario dao = new SQLUsuario();
		return dao.obterCodigo(user);
	}
	
	
	/**
	 * Busca um objeto pelo id.
	 * @param codigo
	 * @return
	 */
	public static Usuario getById(int codigo){
		DAOUsuario dao = new SQLUsuario();
		return dao.getById(codigo);
	}
}
