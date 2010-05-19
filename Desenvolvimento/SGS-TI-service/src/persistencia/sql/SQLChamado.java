package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.entity.Chamado;

import persistencia.dao.DAOChamado;
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
		String sql= "UPDATE chamado SET status=?,detalhes=?,prioridade=?,tipoChamado=?,cliente=? WHERE codigo=?";

		try {
			con = Conexao.obterConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			//stmt.setString(1, chamado.getSenha());
			//stmt.setString(2, chamado.getUsername());		
			
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
