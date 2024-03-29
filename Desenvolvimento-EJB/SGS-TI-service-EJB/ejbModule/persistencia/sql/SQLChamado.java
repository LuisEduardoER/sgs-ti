package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
import persistencia.util.SQLExceptionHandler;

public class SQLChamado implements DAOChamado{
	private static final boolean DEBUG = true;
	private static String INSERIR_CHAMADO = ".jdbc.INSERIR_CHAMADO";
	//private static String ATUALIZAR_CHAMADO = ".jdbc.ATUALIZAR_CHAMADO";
	private static String ATUALIZAR_CHAMADO_CAMPOS = ".jdbc.ATUALIZAR_CHAMADO_CAMPOS";
	private static String BUSCAR_CHAMADO = ".jdbc.BUSCAR_CHAMADO";
	private static String BUSCAR_CHAMADOS_ABERTOS = ".jdbc.BUSCAR_CHAMADOS_ABERTOS";
	private static String BUSCAR_CHAMADOS_AGENDADOS = ".jdbc.BUSCAR_CHAMADOS_AGENDADOS";
	
	@Override
	public boolean adicionaChamado(Chamado chamado)  throws BusinessException{
		Connection con = null;
		String sql= null;
		
		try {
			con = Conexao.getInstance().obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.getInstance().obterOrigem();
			sql = FabricaSql.getSql(origem + INSERIR_CHAMADO);
			
			if(DEBUG)
				System.out.println(sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setDate(1, new java.sql.Date(new java.util.Date().getTime()));//data_abertura
			stmt.setString(2,chamado.getDetalhes());//detalhes
			stmt.setString(3,chamado.getUsuario().getNome());//resposnavel
			stmt.setString(4,"0033334444");//contato
			stmt.setNull(5,Types.DATE);//data_agendamento
			stmt.setInt(6,1);//codigo_status
			stmt.setInt(7,chamado.getTipoChamado().getCodigo());//codigo_tipo_chamado
			stmt.setInt(8,chamado.getTipoFalha().getCodigo());//codigo_tipo_falha
			stmt.setInt(9,chamado.getUsuario().getCodigo());//codigo_usu_registro
			stmt.setNull(10,Types.INTEGER);//codigo_pf
			stmt.setInt(11,chamado.getPj().getCodigo());//codigo_pj

			
			int qtd = stmt.executeUpdate();
			
			if(DEBUG)
				System.out.println("QTDE: "+ qtd);
			
			if (qtd != 1) {
				con.rollback();
				stmt.close();
				throw new BusinessException("Quantidade de linhas afetadas inv�lida: " + qtd);
			}else
				con.commit();
			stmt.close();
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			
		} finally {
			Conexao.getInstance().fecharConexao(con);
		}
		return true;
	}
	
	@Override
	public boolean atualizarChamado(Chamado chamado) throws BusinessException{
		Connection con = null;
		String sql= null;
		
		String origem = Conexao.getInstance().obterOrigem();
		sql = FabricaSql.getSql(origem + ATUALIZAR_CHAMADO_CAMPOS);

		int codigoTipoChamado = FacadeTipoChamado.procurarTipoChamado(chamado.getTipoChamado());
		int codigoTipoFalha = FacadeTipoFalha.procurarTipoFalha(chamado.getTipoFalha());
		int codigoStatus = FacadeStatus.procurarStatus(chamado.getStatus());
		
		try {
			con = Conexao.getInstance().obterConexao();
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setDate(1, (java.sql.Date) chamado.getDataFechamento());	//DATA_FECHAMENTO
			stmt.setString(2, chamado.getDetalhes());	//DETALHES
			if (chamado.getDataAgendamento() == null) {
				stmt.setDate(3, null);	//DATA_AGENDAMENTO
			}else
				stmt.setDate(3, new java.sql.Date(chamado.getDataAgendamento().getTime()));	//DATA_AGENDAMENTO
			stmt.setInt(4, codigoStatus);	//CODIGO_STATUS
			stmt.setInt(5, codigoTipoChamado);	//CODIGO_TIPO_CHAMADO
			stmt.setInt(6, codigoTipoFalha);	//CODIGO_TIPO_FALHA
			stmt.setLong(7, chamado.getCodigo());	//CODIGO
			
			int qtd = stmt.executeUpdate();

			if (qtd != 1) {
				con.rollback();
				stmt.close();
				throw new BusinessException("Quantidade de linhas afetadas inv�lida: " + qtd);
			}else
				con.commit();
			stmt.close();
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			
		} finally {
			Conexao.getInstance().fecharConexao(con);
		}
		return true;
	}

	@Override
	public HistoricoChamado buscarChamado(Chamado chamado) throws BusinessException{
		Connection con = null;
		String sql = null;
		
		try {
			// Obtem a conex�o
			con = Conexao.getInstance().obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.getInstance().obterOrigem();
			sql = FabricaSql.getSql(origem + BUSCAR_CHAMADO);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setFloat(1, chamado.getCodigo()); //chamado.getNumeroChamado());
			
			ResultSet rs = stmt.executeQuery();
			HistoricoChamado historicoChamado = null;
			while(rs.next()){
				String descricao = rs.getString("detalhes");
				Date dataAgendamento = rs.getDate("data_agendamento");
				int codStatus = rs.getInt("codigo_status");
				int codUsuario = rs.getInt("codigo_usu_registro");
				
				 historicoChamado = new HistoricoChamado(new Date(), descricao, dataAgendamento,
						codStatus, codUsuario, (int)chamado.getCodigo());
			}		
			rs.close();
			stmt.close();
			return historicoChamado;
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
		} finally {
			Conexao.getInstance().fecharConexao(con);
		}
		return null;
	}

	@Override
	public List<Chamado> buscarChamadosAbertos() throws BusinessException{
		Connection con = null;
		String sql = null;
		
		try {
			// Obtem a conex�o
			con = Conexao.getInstance().obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.getInstance().obterOrigem();
			sql = FabricaSql.getSql(origem + BUSCAR_CHAMADOS_ABERTOS);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);			
			ResultSet rs = stmt.executeQuery();
		
			List<Chamado> chamados = new LinkedList<Chamado>();
			
			while(rs.next()){
				Chamado chamado = new Chamado();
				chamado.setCodigo(rs.getInt("CODIGO"));
				chamado.setDataAbertura(rs.getDate("DATA_ABERTURA"));
				chamado.setDataFechamento(rs.getDate("DATA_FECHAMENTO"));
				chamado.setDetalhes(rs.getString("DETALHES"));
				chamado.setResponsavel(rs.getString("RESPONSAVEL"));
				chamado.setContato(rs.getString("CONTATO"));
				chamado.setDataAgendamento(rs.getDate("DATA_AGENDAMENTO"));
						
				int codStatus = rs.getInt("CODIGO_STATUS");
				chamado.setStatus(FacadeStatus.getById(codStatus));
				
				int codTipoChamado = rs.getInt("CODIGO_TIPO_CHAMADO");
				chamado.setTipoChamado(FacadeTipoChamado.getById(codTipoChamado));
				
				int codTipoFalha = rs.getInt("CODIGO_TIPO_FALHA");
				chamado.setTipoFalha(FacadeTipoFalha.getById(codTipoFalha));
				
				int codUsuReg = rs.getInt("CODIGO_USU_REGISTRO");
				chamado.setUsuario(FacadeUsuario.getById(codUsuReg));
				
				//int codPF = rs.getInt("CODIGO_PF");
				int codPJ = rs.getInt("CODIGO_PJ");
				chamado.setPj(FacadePessoaJuridica.getById(codPJ));

				chamado.atualizaPrioridade();
				chamados.add(chamado);
			}	
			rs.close();
			stmt.close();
			return chamados;
			
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return null;
		} finally {
			Conexao.getInstance().fecharConexao(con);
		}
	}

	@Override
	public List<Chamado> buscarChamadosAgendado() throws BusinessException {
		Connection con = null;
		String sql = null;
		
		try {
			// Obtem a conex�o
			con = Conexao.getInstance().obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.getInstance().obterOrigem();
			sql = FabricaSql.getSql(origem + BUSCAR_CHAMADOS_AGENDADOS);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);			
			ResultSet rs = stmt.executeQuery();
		
			List<Chamado> chamados = new LinkedList<Chamado>();
			
			while(rs.next()){
				Chamado chamado = new Chamado();
				chamado.setCodigo(rs.getInt("CODIGO"));
				chamado.setDataAbertura(rs.getDate("DATA_ABERTURA"));
				chamado.setDataFechamento(rs.getDate("DATA_FECHAMENTO"));
				chamado.setDetalhes(rs.getString("DETALHES"));
				chamado.setResponsavel(rs.getString("RESPONSAVEL"));
				chamado.setContato(rs.getString("CONTATO"));
				chamado.setDataAgendamento(rs.getDate("DATA_AGENDAMENTO"));
						
				int codStatus = rs.getInt("CODIGO_STATUS");
				chamado.setStatus(FacadeStatus.getById(codStatus));
				
				int codTipoChamado = rs.getInt("CODIGO_TIPO_CHAMADO");
				chamado.setTipoChamado(FacadeTipoChamado.getById(codTipoChamado));
				
				int codTipoFalha = rs.getInt("CODIGO_TIPO_FALHA");
				chamado.setTipoFalha(FacadeTipoFalha.getById(codTipoFalha));
				
				int codUsuReg = rs.getInt("CODIGO_USU_REGISTRO");
				chamado.setUsuario(FacadeUsuario.getById(codUsuReg));
				
				//int codPF = rs.getInt("CODIGO_PF");
				int codPJ = rs.getInt("CODIGO_PJ");
				chamado.setPj(FacadePessoaJuridica.getById(codPJ));

				chamado.atualizaPrioridade();
				chamados.add(chamado);
			}	
			rs.close();
			stmt.close();
			return chamados;
			
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return null;
		} finally {
			Conexao.getInstance().fecharConexao(con);
		}
	}
}
