package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.entity.TipoChamado;
import common.exception.BusinessException;
import common.util.Utils;
import common.exception.BusinessException;

import persistencia.dao.DAOTipoChamado;
import persistencia.util.Conexao;

public class SQLTipoChamado implements DAOTipoChamado{
	private static final boolean DEBUG = true;
	private static String INSERIR_TIPO_CHAMADO = ".jdbc.INSERIR_TIPO_CHAMADO";
	private static String PROCURAR_TIPO_CHAMADO = ".jdbc.PROCURAR_TIPO_CHAMADO";
	private static String PROCURAR_TIPO_CHAMADO_BY_ID = ".jdbc.PROCURAR_TIPO_CHAMADO_BY_ID";
	private static String LISTAR_TODOS = ".jdbc.LISTAR_TODOS_TIPO_CHAMADO";

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
	 * @throws BusinessException 
	 */
	@Override
	public List<TipoChamado> listarTodos() throws BusinessException {
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
		
			List<TipoChamado> tipoChamado = new ArrayList<TipoChamado>();
			while(rs.next()){
				TipoChamado tc = new TipoChamado(rs.getInt("CODIGO"),rs.getString("NOME"), rs.getInt("VALOR_PRIORIDADE"));
				tipoChamado.add(tc);
			}					
			stmt.close();
			if(Utils.isEmptyCollection(tipoChamado)){
				throw new BusinessException("Não foi possível carregar os Tipos de chamado");
			}else
				return tipoChamado;
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

	@Override
	public int procurarTipoChamado(TipoChamado tipoChamado)
			throws BusinessException {
		return 0;
	}	
}
