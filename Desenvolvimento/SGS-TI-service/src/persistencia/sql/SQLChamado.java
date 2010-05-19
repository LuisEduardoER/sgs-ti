package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import common.entity.Chamado;

import persistencia.dao.DAOChamado;
import persistencia.facade.FacadeTipoChamado;
import persistencia.facade.FacadeTipoFalha;
import persistencia.util.Conexao;

public class SQLChamado implements DAOChamado{
	private static final boolean DEBUG = true;
	private static String INSERIR_CHAMADO = ".jdbc.INSERIR_CHAMADO";
	private static String ATUALIZAR_CHAMADO = ".jdbc.ATUALIZAR_CHAMADO";

	@Override
	public boolean adicionaChamado(Chamado chamado) {
		Connection con = null;
		String sql= null;
		
		try {
			con = Conexao.obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + INSERIR_CHAMADO);
			
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
	
	@Override
	public boolean atualizarChamado(Chamado chamado) {
		Connection con = null;
		String sql= null;
		
		String origem = Conexao.obterOrigem();
		sql = FabricaSql.getSql(origem + ATUALIZAR_CHAMADO);
		
		/*String sql= "UPDATE chamado SET dataFechamento=?,detalhes=?,dataHoraAgendamento=?," +
				"cod_tipoChamado=?,cod_tipoFalha=? WHERE codigo_chamado=?";*/

		int codigoTipoChamado = FacadeTipoChamado.procurarTipoChamado(chamado.getTipoChamado());
		int codigoTipoFalha = FacadeTipoFalha.procurarTipoFalha(chamado.getTipoFalha());
		
		try {
			con = Conexao.obterConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setDate(1, (java.sql.Date) chamado.getDataHoraFechamento());
			stmt.setString(2, chamado.getDetalhes());
			stmt.setDate(3, (java.sql.Date) chamado.getDataHoraAbertura());
			stmt.setInt(4, codigoTipoChamado);
			stmt.setInt(5, codigoTipoFalha);
			stmt.setLong(6, chamado.getNumeroChamado());
			
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
