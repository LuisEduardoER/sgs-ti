package persistencia.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import common.entity.HistoricoChamado;
import common.exception.BusinessException;

import persistencia.dao.DAOHistoricoChamado;
import persistencia.util.Conexao;
import persistencia.util.SQLExceptionHandler;

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
			chamado = new HistoricoChamado();
			
			stmt.setDate(1, (Date) chamado.getDataAtualizacao());
			stmt.setString(2, chamado.getDescricao());
			stmt.setDate(3, (Date) chamado.getDataAgentamento());
			stmt.setInt(4, chamado.getCod_status());
			stmt.setInt(5, chamado.getCod_usuario_registro());
			stmt.setInt(6, chamado.getCod_chamado());
			
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
}
