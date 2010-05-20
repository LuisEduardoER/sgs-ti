package persistencia.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 
 * Classe que � respons�vel por realizar e fechar a conex�o com um banco de
 * dados configurado em um arquivo de propriedades (texto).
 * 
 */
public class Conexao {

	private static final String ARQUIVO_CONF = "resources/properties/conexao.properties";

	private static final Properties configuracoes;

	/*
	 * c�digo-fonte est�tico que � executado assim que o ClassLoader carrega a
	 * classe na mem�ria. Assim o atributo de configura��es � carregado apenas
	 * uma vez, n�o precisando ser carregado diversas vezes desnecessariamente.
	 */

	static 
	{	
		File f = new File(ARQUIVO_CONF);

		if (!f.exists())
			throw new IllegalArgumentException("Arquivo de configura��es n�o "
					+ "encontrado " + ARQUIVO_CONF);

		try {
			FileInputStream fis = new FileInputStream(f);
			configuracoes = new Properties();
			configuracoes.load(fis);
			fis.close();
		} catch (IOException e) {
			throw new RuntimeException("Erro de I/O", e);
		}

	}
	
	/**
	 * Recupera uma nova conex�o
	 * @return Connection
	 */
	public static Connection obterConexao() {

		String origem  = configuracoes.getProperty("jdbc.origem");
		
		String driver = configuracoes.getProperty(
							origem+".jdbc.driver");
		String url = configuracoes.getProperty(
							origem+".jdbc.url");
		String usuario = configuracoes.getProperty(
							origem+".jdbc.usuario");
		String senha = configuracoes.getProperty(
							origem+".jdbc.senha");
		
		try {
			Class.forName(driver);
			
			Connection con = DriverManager.getConnection(url,usuario,senha);
			
			return con;
			
		} catch (Exception e) {
			throw new RuntimeException("Erro ao obter conex�o",e);
		}

	}

	/**
	 * Fecha uma conex�o.
	 * @param con conex�o.
	 */
	public static void fecharConexao(Connection con) {

		try {
			if(con != null && !con.isClosed())
				con.close();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao fechar conex�o",e);
		}
		
	}
	
	public static String obterOrigem() {
		return configuracoes.getProperty("jdbc.origem");
	}
}
