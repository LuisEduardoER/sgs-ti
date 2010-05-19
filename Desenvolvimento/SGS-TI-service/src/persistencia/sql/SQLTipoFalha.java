package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.entity.TipoFalha;

import persistencia.dao.DAOTipoFalha;
import persistencia.util.Conexao;

public class SQLTipoFalha implements DAOTipoFalha{
	private static final boolean DEBUG = true;
	private static String INSERIR_TIPO_FALHA = ".jdbc.INSERIR_TIPO_FALHA";
	private static String PROCURAR_TIPO_FALHA = ".jdbc.PROCURAR_TIPO_FALHA";
	
	/**
	 * TODO - Descrever melhor os campos
	 */
	@Override
	public boolean adicionaTipoFalha(TipoFalha tipoFalha) {
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
	@Override
	public int procurarTipoFalha(TipoFalha tipoFalha) {
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
			throw new RuntimeException("Erro SQL", e);
			
		} finally {
			Conexao.fecharConexao(con);
		}
	}	
}
