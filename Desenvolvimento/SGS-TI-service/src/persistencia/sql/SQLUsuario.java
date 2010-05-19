package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.entity.Usuario;

import persistencia.dao.DAOUsuario;
import persistencia.util.Conexao;

public class SQLUsuario implements DAOUsuario{
	private static final boolean DEBUG = true;
	private static String AUTENTICAR_USER = ".jdbc.AUTENTICA_USUARIO";
	private static String INSERIR_USER = ".jdbc.INSERE_USUARIO";
	private static String VERIFICA_USERNAME = ".jdbc.VERIFICA_USERNAME";
	private static String OBTER_CODIGO_USUARIO = ".jdbc.OBTER_CODIGO_USUARIO";

	
	/**
	* TODO - Documentar
	*/
	@Override
	public boolean existeUser(String user) {
		Connection con = null;
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
			throw new RuntimeException("Erro SQL", e);
			
		} finally {
			Conexao.fecharConexao(con);
		}
	}

	/**
	* TODO - Documentar
	*/
	@Override
	public boolean addUsuario(Usuario user) {
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
				con.rollback();
				throw new RuntimeException("Quantidade de linhas afetadas inválida: " + qtd);
			}else
				con.commit();

		} catch (SQLException e) {
			throw new RuntimeException("Erro SQL", e);
			
		} finally {
			Conexao.fecharConexao(con);
		}
		return true;

	}

	/**
	* TODO - Documentar
	*/
	@Override
	public boolean autenticar(Usuario user) {
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
			sql = FabricaSql.getSql(origem + AUTENTICAR_USER);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			
			ResultSet rs = stmt.executeQuery();
		
			while(rs.next()){
				System.out.println(rs.getString("RESULT"));
				int r = Integer.parseInt(rs.getString("RESULT"));
				if(r!=1){
					stmt.close();
					
					// TODO 09: VERICAR ERRO DA QUERY
					return false;
				}else{
					stmt.close();
					return true;
				}	
			}					
			stmt.close();
			return false;
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro SQL",e);
		} finally {
			
			Conexao.fecharConexao(con);
		}
		}

	/**
	* TODO - Documentar
	*/
	@Override
	public int obterCodigo(Usuario user) {
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
		
			while(rs.next()){
				int r = Integer.parseInt(rs.getString("cod_usuario"));

				return r;
			}					
			stmt.close();
			return -1;
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro SQL",e);
		} finally {
			Conexao.fecharConexao(con);
		}
	}

	/**
	* TODO - Documentar
	*/
	@Override
	public boolean updateUser(Usuario user) {
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
				throw new RuntimeException("Quantidade de linhas afetadas inválida: " + qtd);
			}else
				con.commit();

		} catch (SQLException e) {
			throw new RuntimeException("Erro SQL", e);
			
		} finally {
			Conexao.fecharConexao(con);
		}
		return true;
	}
}
