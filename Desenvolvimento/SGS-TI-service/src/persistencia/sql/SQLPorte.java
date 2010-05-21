package persistencia.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import common.entity.Porte;
import common.exception.BusinessException;
import persistencia.dao.DAOPorte;
import persistencia.util.Conexao;
import persistencia.util.SQLExceptionHandler;

public class SQLPorte implements DAOPorte{
	private static final boolean DEBUG = true;
	//private static String INSERIR_PORTE= ".jdbc.INSERIR_PORTE";
	private static String PROCURAR_PORTE_BY_ID = ".jdbc.PROCURAR_PORTE_BY_ID";

	@Override
	public boolean adicionaPorte(Porte porte) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Porte getById(int codigo) throws BusinessException{
		Connection con = null;
		String sql = null;
			
		try {
			// Obtem a conexão
			con = Conexao.obterConexao();
			
			String origem = Conexao.obterOrigem();
			sql = FabricaSql.getSql(origem + PROCURAR_PORTE_BY_ID);
			
			if(DEBUG)
				System.out.println("SQL - " + sql + ", " + codigo);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, codigo);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				String nome = rs.getString("NOME");
				
				Porte porte = new Porte(nome);
				return porte;
			}					
			stmt.close();
			return null;
			
		} catch (SQLException e) {
			SQLExceptionHandler.tratarSQLException(this.getClass().getName(), e);
			return null;
			
		} finally {
			Conexao.fecharConexao(con);
		}
	}

}
