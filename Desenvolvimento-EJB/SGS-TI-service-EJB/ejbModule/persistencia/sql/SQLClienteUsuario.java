package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.entity.PessoaFisica;
import common.entity.PessoaJuridica;
import common.entity.Usuario;
import common.exception.BusinessException;
import common.util.Utils;

import persistencia.dao.DAOClienteUsuario;
import persistencia.facade.FacadePessoaJuridica;
import persistencia.util.Conexao;
import persistencia.util.SQLExceptionHandler;

public class SQLClienteUsuario implements DAOClienteUsuario{
	private static final boolean DEBUG = true;
	private static String PEGAR_CLIENTE_PJ = ".jdbc.PEGAR_CLIENTE_PJ";
	private static String PEGAR_CLIENTE_PF = ".jdbc.PEGAR_CLIENTE_PF";

	public SQLClienteUsuario() {
	}
	
	@Override
	public PessoaJuridica getPessoaJuridicaByUserId(Usuario codigoUser) throws BusinessException {
		Connection con = null;
		String sql = null;		
		
		try {
			// Obtem a conexão
			con = Conexao.getInstance().obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.getInstance().obterOrigem();
			sql = FabricaSql.getSql(origem + PEGAR_CLIENTE_PJ);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, codigoUser.getCodigo());
			
			ResultSet rs = stmt.executeQuery();
			int codCli = -1;
			PessoaJuridica pj = null;
			while(rs.next()){
				String cpj = rs.getString("CODIGO_PJ");
				if(!Utils.isNullOrEmpty(cpj))
				{
					codCli = Integer.parseInt(cpj);
					pj = FacadePessoaJuridica.getById(codCli);
				}
			}				
			rs.close();
			stmt.close();
			
			if(Utils.isNullOrEmpty(pj)){
				// n tem empresa vinculada
				throw new BusinessException("Este usuário não possui Empresa vinculada");
			}
			return pj;
			
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return null;
		} finally {
			Conexao.getInstance().fecharConexao(con);
		}
	}
	
	@Override
	public PessoaFisica getPessoaFisicaByUserId(Usuario codigoUser) throws BusinessException {
		Connection con = null;
		String sql = null;		
		//TODO tem q arrumar, igual PJ
		try {
			// Obtem a conexão
			con = Conexao.getInstance().obterConexao();
			con.setAutoCommit(false);
			
			String origem = Conexao.getInstance().obterOrigem();
			sql = FabricaSql.getSql(origem + PEGAR_CLIENTE_PF);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, codigoUser.getCodigo());
			
			ResultSet rs = stmt.executeQuery();
			int codCli = -1;
			while(rs.next()){
				String pf = rs.getString("CODIGO_PF");
				if(!Utils.isNullOrEmpty(pf))
				{
					codCli = Integer.parseInt(pf);
				}
			}	
			rs.close();
			stmt.close();
			
			if(codCli==-1){
				// n tem empresa vinculada
				throw new BusinessException("Este usuário não possui Empresa vinculada");
			}
			return null;
			
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return null;
		} finally {
			Conexao.getInstance().fecharConexao(con);
		}
	}
}
