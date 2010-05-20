package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import common.entity.Chamado;
import common.entity.HistoricoChamado;

import persistencia.dao.DAOChamado;
import persistencia.facade.FacadeTipoChamado;
import persistencia.facade.FacadeTipoFalha;
import persistencia.util.Conexao;

public class SQLChamado implements DAOChamado{
	private static final boolean DEBUG = true;
	private static String INSERIR_CHAMADO = ".jdbc.INSERIR_CHAMADO";
	private static String ATUALIZAR_CHAMADO = ".jdbc.ATUALIZAR_CHAMADO";
	private static String BUSCAR_CHAMADO = ".jdbc.BUSCAR_CHAMADO";

	/**
	 * TODO - Descrever melhor os campos
	 */
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
	
	/**
	 * TODO - Descrever melhor os campos
	 */
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

	@Override
	public HistoricoChamado buscarChamado(Chamado chamado) {
		Connection con = null;
		String sql = null;
		
		try {
			// Obtem a conexão
			con = Conexao.obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + BUSCAR_CHAMADO);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setFloat(1, chamado.getNumeroChamado());
			
			ResultSet rs = stmt.executeQuery();
		
			while(rs.next()){
				String descricao = rs.getString("detalhes");
				Date dataAgendamento = rs.getDate("data_agendamento");
				int codStatus = rs.getInt("codigo_status");
				int codUsuario = rs.getInt("codigo_usu_registro");
				
				HistoricoChamado historicoChamado = new HistoricoChamado(new Date(), descricao, dataAgendamento,
						codStatus, codUsuario, (int)chamado.getNumeroChamado());
				return historicoChamado;
			}					
			stmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro SQL",e);
		} finally {
			Conexao.fecharConexao(con);
		}
		return null;
	}
}
