package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.entity.TipoChamado;

import persistencia.dao.DAOTipoChamado;
import persistencia.util.Conexao;

public class SQLTipoChamado implements DAOTipoChamado{
	private static final boolean DEBUG = true;
	private static String INSERIR_TIPO_CHAMADO = ".jdbc.INSERIR_TIPO_CHAMADO";
	private static String PROCURAR_TIPO_CHAMADO = ".jdbc.PROCURAR_TIPO_CHAMADO";

	
	/**
	 create table Tipochamado(
	 codigoTipoChamado integer not null constraint CODIGO_TIPOCHAMADO PRIMARY KEY,
	 nomeTipoChamado varchar(255), 
	 valorPrioridade integer);
	 */
	
	@Override
	public boolean adicionaTipoChamado(TipoChamado tipoChamado) {
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
				throw new RuntimeException("Quantidade de linhas afetadas inv�lida: " + qtd);
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
	 create table Tipochamado(
	 codigoTipoChamado integer not null constraint CODIGO_TIPOCHAMADO PRIMARY KEY,
	 nomeTipoChamado varchar(255), 
	 valorPrioridade integer);
	 */
	@Override
	public int procurarTipoChamado(TipoChamado tipoChamado) {
		Connection con = null;
		String sql = null;
			
		try {
			// Obtem a conex�o
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
}