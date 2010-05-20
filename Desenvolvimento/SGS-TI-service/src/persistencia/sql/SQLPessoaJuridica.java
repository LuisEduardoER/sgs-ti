package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.entity.PessoaJuridica;
import common.entity.Porte;
import persistencia.dao.DAOPessoaJuridica;
import persistencia.facade.FacadePorte;
import persistencia.util.Conexao;

public class SQLPessoaJuridica implements DAOPessoaJuridica{
	private static final boolean DEBUG = true;
	//private static String INSERIR_PJ= ".jdbc.INSERIR_PJ";
	private static String PROCURAR_PJ_BY_ID = ".jdbc.PROCURAR_PJ_BY_ID";
	private static String PROCURAR_PJ_BY_DESC = ".jdbc.PROCURAR_PJ_BY_DESC";

	@Override
	public boolean adicionaPessoaJuridica(PessoaJuridica pessoaJuridica) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public PessoaJuridica getById(int codigo) {
		Connection con = null;
		String sql = null;
			
		try {
			// Obtem a conexão
			con = Conexao.obterConexao();
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + PROCURAR_PJ_BY_ID);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, codigo);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				
				String razaoSocial = rs.getString("RAZAO_SOCIAL");
				String nomeFantasia = rs.getString("NOME_FANTASIA");
				long cnpj = rs.getLong("CNPJ");
				String endereco = rs.getString("ENDERECO");
				
				int codPorte = rs.getInt("CODIGO_PORTE");
				Porte porte = FacadePorte.getById(codPorte);
				
				PessoaJuridica pj = new PessoaJuridica(endereco, porte, null, razaoSocial, nomeFantasia, cnpj);
				return pj;		
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
	public List<PessoaJuridica> pesquisarPorDescricao(String descricao) {
		Connection con = null;
		String sql = null;
		List<PessoaJuridica> pjs = new ArrayList<PessoaJuridica>();
		try {
			// Obtem a conexão
			con = Conexao.obterConexao();
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + PROCURAR_PJ_BY_DESC);
			
			if(DEBUG)
				System.out.println("SQL - " + sql);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, descricao);
			stmt.setString(2, descricao);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				
				String razaoSocial = rs.getString("RAZAO_SOCIAL");
				String nomeFantasia = rs.getString("NOME_FANTASIA");
				long cnpj = rs.getLong("CNPJ");
				String contato = rs.getString("CONTATO");
				String endereco = rs.getString("ENDERECO");
				
				int codPorte = rs.getInt("CODIGO_PORTE");
				Porte porte = FacadePorte.getById(codPorte);
				
				PessoaJuridica pj = new PessoaJuridica(endereco, porte, null, razaoSocial, nomeFantasia, cnpj);
				pjs.add(pj);
			}
			stmt.close();
			return null;
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro SQL", e);
			
		} finally {
			Conexao.fecharConexao(con);
		}
		
	}
}
