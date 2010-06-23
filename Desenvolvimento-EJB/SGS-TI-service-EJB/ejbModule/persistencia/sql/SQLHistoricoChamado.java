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


	@Override
	public boolean adicionaHistoricoChamado(HistoricoChamado chamado)  throws BusinessException 
	{
		Connection con = null;
		String sql= null;
		
		try {
			con = Conexao.getInstance().obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.getInstance().obterOrigem();
			sql = FabricaSql.getSql(origem + INSERIR_HISTORICO_CHAMADO);
			
			if(DEBUG)
				System.out.println(sql);
			
			java.util.Date agendamento;
			
			if(chamado.getDataAgentamento() == null)
				agendamento = null;
			else
				agendamento = new Date (chamado.getDataAgentamento().getTime());
			     
			
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setDate(1, new Date(chamado.getDataAtualizacao().getTime()));
			stmt.setString(2, chamado.getDescricao());
			stmt.setDate(3, (Date) agendamento);
			stmt.setInt(4, chamado.getCod_status());
			stmt.setInt(5, chamado.getCod_usuario_registro());
			stmt.setInt(6, chamado.getCod_chamado());
			
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
}
