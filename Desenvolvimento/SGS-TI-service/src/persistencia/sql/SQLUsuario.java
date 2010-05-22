package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.entity.Usuario;
import common.exception.BusinessException;
import common.util.Utils;

import persistencia.dao.DAOUsuario;
import persistencia.util.Conexao;
import persistencia.util.SQLExceptionHandler;

public class SQLUsuario implements DAOUsuario{
	private static final boolean DEBUG = true;
	private static String AUTENTICAR_USER = ".jdbc.AUTENTICAR_USER";
	private static String INSERIR_USER = ".jdbc.INSERE_USUARIO";
	private static String VERIFICA_USERNAME = ".jdbc.VERIFICA_USERNAME";
	private static String OBTER_CODIGO_USUARIO = ".jdbc.OBTER_CODIGO_USUARIO";
	private static String OBTER_USUARIO_BY_ID = ".jdbc.OBTER_USUARIO_BY_ID";

	
	/**
	* TODO - Documentar
	*/
	@Override
	public boolean existeUser(String user)  throws BusinessException{
		/*Connection con = null;
		String sql = null;
		
		if(user.contains("%"))
			return false;
			
		try {
			// Obtem a conexão
			con = Conexao.obterConexao();
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + VERIFICA_USERNAME);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, user);
			
			ResultSet rs = stmt.executeQuery();
		
			while(rs.next()){
				int r = Integer.parseInt(rs.getString("result"));
				if(r>=1){
					stmt.close();
					return true;
				}else{
					stmt.close();
					return false;
				}	
			}					
			
			stmt.close();
			return true;
			
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return false;
			
		} finally {
			Conexao.fecharConexao(con);
		}*/
		return true;
	}

	/**
	* TODO - Documentar
	*/
	@Override
	public boolean addUsuario(Usuario user)  throws BusinessException{
		Connection con = null;
		String sql= null;
		
		try {
			con = Conexao.obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + INSERIR_USER);
			
			if(DEBUG)
				System.out.println(sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			
			int qtd = stmt.executeUpdate();
			
			if(DEBUG)
				System.out.println("QTDE: "+ qtd);
			
			if (qtd != 1) {
				stmt.close();
				con.rollback();
				throw new BusinessException("Quantidade de linhas afetadas inválida: " + qtd);
			}else
				con.commit();
			stmt.close();
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return false;
			
		} finally {
			Conexao.fecharConexao(con);
		}
		return true;

	}

	/**
	* TODO - Documentar
	 * @throws BusinessException 
	*/
	@Override
	public Usuario autenticar(Usuario user) throws BusinessException {
		Connection con = null;
		String sql = null;

		try {
			// Obtem a conexão
			con = Conexao.obterConexao();
			con.setAutoCommit(false);

			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + AUTENTICAR_USER);

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());

			ResultSet rs = stmt.executeQuery();
			Usuario usuario = null;
			while(rs.next()){
				int codigo = Integer.parseInt(rs.getString("CODIGO"));
				String nome = rs.getString("NOME");
				usuario = user;
				usuario.setCodigo(codigo);
				usuario.setNome(nome);
			}
			rs.close();
			stmt.close();
			if(Utils.isNullOrEmpty(usuario))
				throw new BusinessException("Usuário não encontrado!");
			else if(Utils.isNullOrEmpty(usuario.getNome()))
				throw new BusinessException("Usuário não possui nome.");
			return usuario;
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return null;
		} finally {
			Conexao.fecharConexao(con);
		}
		
	}

	/**
	* TODO - Documentar
	*/
	@Override
	public int obterCodigo(Usuario user) throws BusinessException {
		Connection con = null;
		String sql = null;
		
		if(DEBUG){
			System.out.println("USER:" + user.getUsername());
			System.out.println("SENHA:" + user.getPassword());
		}
		
		try {
			// Obtem a conexão
			con = Conexao.obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + OBTER_CODIGO_USUARIO);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			
			ResultSet rs = stmt.executeQuery();
			int r = -1;
			while(rs.next()){
				r = Integer.parseInt(rs.getString("cod_usuario"));
			}
			rs.close();
			stmt.close();
			return r;
			
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return -1;
		} finally {
			Conexao.fecharConexao(con);
		}
	}

	/**
	* TODO - Documentar
	*/
	@Override
	public boolean updateUser(Usuario user)  throws BusinessException{
		Connection con = null;
		String sql= "UPDATE usuario SET senha=? WHERE login=?";

		try {
			con = Conexao.obterConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setString(1, user.getPassword());
			stmt.setString(2, user.getUsername());		
			
			int qtd = stmt.executeUpdate();

			if (qtd != 1) {
				con.rollback();
				stmt.close();
				throw new BusinessException("Quantidade de linhas afetadas inválida: " + qtd);
			}else
				con.commit();
			stmt.close();

		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return false;
			
		} finally {
			Conexao.fecharConexao(con);
		}
		return true;
	}

	@Override
	public Usuario getById(int codigo) throws BusinessException{
		Connection con = null;
		String sql = null;		
		
		try {
			// Obtem a conexão
			con = Conexao.obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + OBTER_USUARIO_BY_ID);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, codigo);
			
			ResultSet rs = stmt.executeQuery();
			Usuario usu = null;
			while(rs.next()){
				usu = new Usuario();
				String nome = rs.getString("NOME");
				String username = rs.getString("USERNAME");
				
				usu.setNome(nome);
				usu.setUsername(username);
			}				
			rs.close();
			stmt.close();
			return usu;
			
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return null;
		} finally {
			Conexao.fecharConexao(con);
		}
	}
}
