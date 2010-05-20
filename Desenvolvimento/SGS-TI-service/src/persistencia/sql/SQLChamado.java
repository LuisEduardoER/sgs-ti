package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import common.entity.Chamado;
import common.entity.HistoricoChamado;
import common.exception.BusinessException;
import persistencia.dao.DAOChamado;
import persistencia.facade.FacadePessoaJuridica;
import persistencia.facade.FacadeStatus;
import persistencia.facade.FacadeTipoChamado;
import persistencia.facade.FacadeTipoFalha;
import persistencia.facade.FacadeUsuario;
import persistencia.util.Conexao;

public class SQLChamado implements DAOChamado{
	private static final boolean DEBUG = true;
	private static String INSERIR_CHAMADO = ".jdbc.INSERIR_CHAMADO";
	private static String ATUALIZAR_CHAMADO = ".jdbc.ATUALIZAR_CHAMADO";
	private static String BUSCAR_CHAMADO = ".jdbc.BUSCAR_CHAMADO";
	private static String BUSCAR_CHAMADOS_ABERTOS = ".jdbc.BUSCAR_CHAMADOS_ABERTOS";
	private static String BUSCAR_CHAMADOS_AGENDADOS = ".jdbc.BUSCAR_CHAMADOS_AGENDADOS";
	
	/**
	 * TODO - Descrever melhor os campos
	 */
	@Override
	public boolean adicionaChamado(Chamado chamado)  throws BusinessException{
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
			//insert into CHAMADO values(chamadoSeq.nextVal,?,null,?,?,?,?,?,?,?,?,?,?) 
			//,1data_aber,NULLdata_fech,2detalhes,3responsavel,4contato,5data_agendamento,
			//6codigo_status,7codigo_tipo_chamado,8codigo_tipo_falah,9codigo_usu_registro,10codigo_pf,11codigo_pj
			chamado = new Chamado();
			chamado.setResponsavel("denis");
			chamado.setDetalhes("lixo");
			
			stmt.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
			stmt.setDate(2, null);
			stmt.setString(3,chamado.getDetalhes());
			stmt.setString(4,chamado.getResponsavel());
			stmt.setString(5,null);
			stmt.setInt(6,1);
			stmt.setInt(7,1);
			stmt.setInt(8,1);
			stmt.setInt(9,1);
			stmt.setNull(10,1);
			stmt.setInt(11,4);
			
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
	public boolean atualizarChamado(Chamado chamado) throws BusinessException{
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
	public HistoricoChamado buscarChamado(Chamado chamado) throws BusinessException{
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

	@Override
	public List<Chamado> buscarChamadosAbertos() throws BusinessException{
		Connection con = null;
		String sql = null;
		
		try {
			// Obtem a conexão
			con = Conexao.obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + BUSCAR_CHAMADOS_ABERTOS);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);			
			ResultSet rs = stmt.executeQuery();
		
			List<Chamado> chamados = new LinkedList<Chamado>();
			
			while(rs.next()){
				Chamado chamado = new Chamado();
				chamado.setDataHoraAbertura(rs.getDate("DATA_ABERTURA"));
				chamado.setDataHoraFechamento(rs.getDate("DATA_FECHAMENTO"));
				chamado.setDetalhes(rs.getString("DETALHES"));
				chamado.setResponsavel(rs.getString("RESPONSAVEL"));
				chamado.setContato(rs.getString("CONTATO"));
				chamado.setDataHoraAgendamento(rs.getDate("DATA_AGENDAMENTO"));
						
				int codStatus = rs.getInt("CODIGO_STATUS");
				chamado.setStatus(FacadeStatus.getById(codStatus));
				
				int codTipoChamado = rs.getInt("CODIGO_TIPO_CHAMADO");
				chamado.setTipoChamado(FacadeTipoChamado.getById(codTipoChamado));
				
				int codTipoFalha = rs.getInt("CODIGO_TIPO_FALHA");
				chamado.setTipoFalha(FacadeTipoFalha.getById(codTipoFalha));
				
				int codUsuReg = rs.getInt("CODIGO_USU_REGISTRO");
				chamado.setUsuarioResgistro(FacadeUsuario.getById(codUsuReg));
				
				//int codPF = rs.getInt("CODIGO_PF");
				int codPJ = rs.getInt("CODIGO_PJ");
				chamado.setReclamante(FacadePessoaJuridica.getById(codPJ));

				chamado.atualizaPrioridade();
				chamados.add(chamado);
			}	
			
			stmt.close();
			return chamados;
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro SQL",e);
		} finally {
			Conexao.fecharConexao(con);
		}
	}

	@Override
	public List<Chamado> buscarChamadosAgendado() throws BusinessException {
		Connection con = null;
		String sql = null;
		
		try {
			// Obtem a conexão
			con = Conexao.obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + BUSCAR_CHAMADOS_AGENDADOS);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);			
			ResultSet rs = stmt.executeQuery();
		
			List<Chamado> chamados = new LinkedList<Chamado>();
			
			while(rs.next()){
				Chamado chamado = new Chamado();
				chamado.setDataHoraAbertura(rs.getDate("DATA_ABERTURA"));
				chamado.setDataHoraFechamento(rs.getDate("DATA_FECHAMENTO"));
				chamado.setDetalhes(rs.getString("DETALHES"));
				chamado.setResponsavel(rs.getString("RESPONSAVEL"));
				chamado.setContato(rs.getString("CONTATO"));
				chamado.setDataHoraAgendamento(rs.getDate("DATA_AGENDAMENTO"));
						
				int codStatus = rs.getInt("CODIGO_STATUS");
				chamado.setStatus(FacadeStatus.getById(codStatus));
				
				int codTipoChamado = rs.getInt("CODIGO_TIPO_CHAMADO");
				chamado.setTipoChamado(FacadeTipoChamado.getById(codTipoChamado));
				
				int codTipoFalha = rs.getInt("CODIGO_TIPO_FALHA");
				chamado.setTipoFalha(FacadeTipoFalha.getById(codTipoFalha));
				
				int codUsuReg = rs.getInt("CODIGO_USU_REGISTRO");
				chamado.setUsuarioResgistro(FacadeUsuario.getById(codUsuReg));
				
				//int codPF = rs.getInt("CODIGO_PF");
				int codPJ = rs.getInt("CODIGO_PJ");
				chamado.setReclamante(FacadePessoaJuridica.getById(codPJ));

				chamado.atualizaPrioridade();
				chamados.add(chamado);
			}	
			
			stmt.close();
			return chamados;
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro SQL",e);
		} finally {
			Conexao.fecharConexao(con);
		}
	}
}
