package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.entity.TipoChamado;
import common.exception.BusinessException;

import persistencia.dao.DAOTipoChamado;
import persistencia.util.Conexao;

public class SQLTipoChamado implements DAOTipoChamado{
	private static final boolean DEBUG = true;
	private static String INSERIR_TIPO_CHAMADO = ".jdbc.INSERIR_TIPO_CHAMADO";
	private static String PROCURAR_TIPO_CHAMADO = ".jdbc.PROCURAR_TIPO_CHAMADO";
	private static String PROCURAR_TIPO_CHAMADO_BY_ID = ".jdbc.PROCURAR_TIPO_CHAMADO_BY_ID";

	/**
	 * TODO - Descrever melhor os campos
	 */	
	@Override
	public boolean adicionaTipoChamado(TipoChamado tipoChamado) throws BusinessException {
		Connection con = null;
		String sql= null;
		
		try {
			con = Conexao.obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + INSERIR_TIPO_CHAMADO);
			
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
	public int procurarTipoChamado(TipoChamado tipoChamado) throws BusinessException {
		Connection con = null;
		String sql = null;
			
		try {
			// Obtem a conexão
			con = Conexao.obterConexao();
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + PROCURAR_TIPO_CHAMADO);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, tipoChamado.getNome());
			
			ResultSet rs = stmt.executeQuery();
		
			int codigo;
			
			while(rs.next()){
				codigo = Integer.parseInt(rs.getString("codigoTipoChamado"));
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
	public TipoChamado getById(int codigo) throws BusinessException {
		Connection con = null;
		String sql = null;
			
		try {
			// Obtem a conexão
			con = Conexao.obterConexao();
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + PROCURAR_TIPO_CHAMADO_BY_ID);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, codigo);
			
			ResultSet rs = stmt.executeQuery();
	
			while(rs.next()){
				String nome = rs.getString("NOME");
				int prio = rs.getInt("VALOR_PRIORIDADE");
				
				return new TipoChamado(nome,prio);
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
