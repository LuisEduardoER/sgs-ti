package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import common.entity.TipoFalha;
import common.exception.BusinessException;
import common.util.Utils;
import persistencia.dao.DAOTipoFalha;
import persistencia.util.Conexao;
import persistencia.util.SQLExceptionHandler;

public class SQLTipoFalha implements DAOTipoFalha{
	private static final boolean DEBUG = true;
	private static String INSERIR_TIPO_FALHA = ".jdbc.INSERIR_TIPO_FALHA";
	private static String PROCURAR_TIPO_FALHA = ".jdbc.PROCURAR_TIPO_FALHA";
	private static String PROCURAR_TIPO_FALHA_BY_ID = ".jdbc.PROCURAR_TIPO_FALHA_BY_ID";
	
	private static String LISTAR_TODOS = ".jdbc.LISTAR_TODOS_TIPO_FALHA";
	
	/**
	 * TODO - Descrever melhor os campos
	 */
	@Override
	public boolean adicionaTipoFalha(TipoFalha tipoFalha) throws BusinessException {
		Connection con = null;
		String sql= null;
		
		try {
			con = Conexao.obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + INSERIR_TIPO_FALHA);
			
			if(DEBUG)
				System.out.println(sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			//TODO -colocar os parametros
			
			int qtd = stmt.executeUpdate();
			
			if(DEBUG)
				System.out.println("QTDE: "+ qtd);
			
			if (qtd != 1) {
				con.rollback();
				throw new BusinessException("Quantidade de linhas afetadas inválida: " + qtd);
			}else
				con.commit();

		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return false;
			
		} finally {
			Conexao.fecharConexao(con);
		}
		return true;
	}

	/**
	 * TODO - Descrever melhor os campos
	 */
	@Override
	public int procurarTipoFalha(TipoFalha tipoFalha) throws BusinessException {
		Connection con = null;
		String sql = null;
			
		try {
			// Obtem a conexão
			con = Conexao.obterConexao();
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + PROCURAR_TIPO_FALHA);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, tipoFalha.getNome());
			
			ResultSet rs = stmt.executeQuery();
		
			int codigo;
			
			while(rs.next()){
				codigo = Integer.parseInt(rs.getString("codigoTipoFalha"));
				return codigo;		
			}					
			stmt.close();
			return -1;
			
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return -1;
			
		} finally {
			Conexao.fecharConexao(con);
		}
	}
	
	public List<TipoFalha> listarTodos() throws BusinessException {
		Connection con = null;
		String sql = null;
			
		try {
			// Obtem a conexão
			con = Conexao.obterConexao();
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + LISTAR_TODOS);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
		
			List<TipoFalha> tipoFalha = new ArrayList<TipoFalha>();
			while(rs.next()){
				TipoFalha tf = new TipoFalha(rs.getInt("CODIGO"),rs.getString("NOME"));
				tipoFalha.add(tf);
			}					
			stmt.close();
			if(Utils.isEmptyCollection(tipoFalha)){
				throw new BusinessException("Não foi possível carregar os Tipos de falhas");
			}else
				return tipoFalha;
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return null;
			
		} finally {
			Conexao.fecharConexao(con);
		}
	}

	@Override
	public TipoFalha getById(int codigo) throws BusinessException {
		Connection con = null;
		String sql = null;
			
		try {
			// Obtem a conexão
			con = Conexao.obterConexao();
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + PROCURAR_TIPO_FALHA_BY_ID);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, codigo);
			
			ResultSet rs = stmt.executeQuery();
		
			
			while(rs.next()){
				String nome = rs.getString("NOME");
				TipoFalha tipoFalha = new TipoFalha(nome);
				return tipoFalha;
			}					
			stmt.close();
			return null;
			
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return null;
			
		} finally {
			Conexao.fecharConexao(con);
		}
	}	
}
