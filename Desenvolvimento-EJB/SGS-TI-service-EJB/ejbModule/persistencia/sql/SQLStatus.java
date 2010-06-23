package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.entity.StatusChamado;
import common.exception.BusinessException;
import persistencia.dao.DAOStatus;
import persistencia.util.Conexao;
import persistencia.util.SQLExceptionHandler;

public class SQLStatus implements DAOStatus{
	private static final boolean DEBUG = true;
	private static String INSERIR_STATUS = ".jdbc.INSERIR_STATUS";
	private static String PROCURAR_STATUS = ".jdbc.PROCURAR_STATUS";
	private static String PROCURAR_STATUS_BY_ID = ".jdbc.PROCURAR_STATUS_BY_ID";
	private static String LISTAR_TODOS_STATUS = ".jdbc.LISTAR_TODOS_STATUS";
	
	public boolean adicionaStatus(StatusChamado status) throws BusinessException {
		Connection con = null;
		String sql= null;
		
		try {
			con = Conexao.getInstance().obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.getInstance().obterOrigem();
			sql = FabricaSql.getSql(origem + INSERIR_STATUS);
			
			if(DEBUG)
				System.out.println(sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			//TODO -colocar os parametros
			
			int qtd = stmt.executeUpdate();
			
			if(DEBUG)
				System.out.println("QTDE: "+ qtd);
			
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
			Conexao.getInstance().fecharConexao(con);
		}
		return true;
	}

	public int procurarStatus(StatusChamado status) throws BusinessException {
		Connection con = null;
		String sql = null;
			
		try {
			// Obtem a conexão
			con = Conexao.getInstance().obterConexao();
			
			String origem = Conexao.getInstance().obterOrigem();
			sql = FabricaSql.getSql(origem + PROCURAR_STATUS);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, status.getNome());
			
			ResultSet rs = stmt.executeQuery();
		
			int codigo = -1;
			
			while(rs.next()){
				codigo = rs.getInt("CODIGO");
			}					
			rs.close();
			stmt.close();
			return codigo;
			
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return -1;
			
		} finally {
			Conexao.getInstance().fecharConexao(con);
		}
	}

	@Override
	public StatusChamado getById(int codigo) throws BusinessException {
		Connection con = null;
		String sql = null;
			
		try {
			// Obtem a conexão
			con = Conexao.getInstance().obterConexao();
			
			String origem = Conexao.getInstance().obterOrigem();
			sql = FabricaSql.getSql(origem + PROCURAR_STATUS_BY_ID);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, codigo);
			
			ResultSet rs = stmt.executeQuery();
			StatusChamado status = null;
			while(rs.next()){
				String nome = rs.getString("NOME");
				status = new StatusChamado(nome);
			}			
			rs.close();
			stmt.close();
			return status;
			
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return null;
			
		} finally {
			Conexao.getInstance().fecharConexao(con);
		}
	}
	
	@Override
	public List<StatusChamado> listarTodos() throws BusinessException {
		Connection con = null;
		String sql = null;
			
		try {
			// Obtem a conexão
			con = Conexao.getInstance().obterConexao();
			
			String origem = Conexao.getInstance().obterOrigem();
			sql = FabricaSql.getSql(origem + LISTAR_TODOS_STATUS);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			List<StatusChamado> listaStatus = new ArrayList<StatusChamado>();
			while(rs.next()){
				String nome = rs.getString("NOME");
				StatusChamado status = new StatusChamado(nome);
				int codigo = rs.getInt("CODIGO");
				status.setCodigo(codigo);
				listaStatus.add(status);		
			}
			rs.close();
			stmt.close();
			return listaStatus;
			
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return null;
			
		} finally {
			Conexao.getInstance().fecharConexao(con);
		}
	}
}
