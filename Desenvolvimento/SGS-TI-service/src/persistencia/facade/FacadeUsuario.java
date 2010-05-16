package persistencia.facade;

import persistencia.dao.DAOUsuario;
import persistencia.sql.SQLUsuario;
import common.util.MD5Encryption;

public class FacadeUsuario {
	
	public static boolean criarUser(Usuario user){
		
		// Usa o utilitario para criptografar a senha
		user.setSenha( MD5Encryption.encript(user.getSenha()) );
		
		DAOUsuario dao = new SQLUsuario();
		
		boolean inserido = dao.addUsuario(user);
		return inserido;
	}
	
	public static boolean autenticarUser(Usuario user){

		// Usa o utilitario para criptografar a senha
		user.setSenha( MD5Encryption.encript(user.getSenha()) );
		
		DAOUsuario dao = new SQLUsuario();
		
		return dao.autenticar(user);
	}

	public static boolean atualizarPerfil(Usuario user) {
		
		// Usa o utilitario para criptografar a senha
		user.setSenha( MD5Encryption.encript(user.getSenha()) );
		
		DAOUsuario dao = new SQLUsuario();
		boolean atualizado = dao.updateUser(user);
		
		return atualizado;
	}
	
	public static boolean verificarUsername(String username){
		DAOUsuario dao = new SQLUsuario();
		return dao.existeUser(username);
	}
}
