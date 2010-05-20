package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import common.entity.StatusChamado;
import persistencia.dao.DAOStatus;
import persistencia.util.Conexao;

public class SQLStatus implements DAOStatus{
	private static final boolean DEBUG = true;
	private static String INSERIR_STATUS = ".jdbc.INSERIR_STATUS";
	private static String PROCURAR_STATUS = ".jdbc.PROCURAR_STATUS";
	private static String PROCURAR_STATUS_BY_ID = ".jdbc.PROCURAR_STATUS_BY_ID";
	
	/**
	 * TODO - Descrever melhor os campos
	 */
	public boolean adicionaStatus(StatusChamado status) {
		Connection con = null;
		String sql= null;
		
		try {
			con = Conexao.obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.obterOrigem();
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
	 * TODO - Descrever melhor os campos
	 */
	public int procurarStatus(StatusChamado status) {
		Connection con = null;
		String sql = null;
			
		try {
			// Obtem a conexão
			con = Conexao.obterConexao();
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + PROCURAR_STATUS);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, status.getNome());
			
			ResultSet rs = stmt.executeQuery();
		
			int codigo;
			
			while(rs.next()){
				codigo = Integer.parseInt(rs.getString("codigoTipoFalha"));
				return codigo;		
			}					
			stmt.close();
			return -1;
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro SQL", e);
			
		} finally {
			Conexao.fecharConexao(con);
		}
	}

	@Override
	public StatusChamado getById(int codigo) {
		Connection con = null;
		String sql = null;
			
		try {
			// Obtem a conexão
			con = Conexao.obterConexao();
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + PROCURAR_STATUS_BY_ID);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, codigo);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				String nome = rs.getString("NOME");
				StatusChamado status = new StatusChamado(nome);
				return status;		
			}					
			stmt.close();
			return null;
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro SQL", e);
			
		} finally {
			Conexao.fecharConexao(con);
		}
	}
}
