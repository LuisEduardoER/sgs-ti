package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import common.entity.HistoricoChamado;
import common.exception.BusinessException;

import persistencia.dao.DAOHistoricoChamado;
import persistencia.util.Conexao;

public class SQLHistoricoChamado implements DAOHistoricoChamado
{
	private static final boolean DEBUG = true;
	private static String INSERIR_HISTORICO_CHAMADO = ".jdbc.INSERIR_HISTORICO_CHAMADO";

	/**
	 * TODO - Descrever melhor os campos
	 */
	@Override
	public boolean adicionaHistoricoChamado(HistoricoChamado chamado)  throws BusinessException 
	{
		Connection con = null;
		String sql= null;
		
		try {
			con = Conexao.obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + INSERIR_HISTORICO_CHAMADO);
			
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
}
