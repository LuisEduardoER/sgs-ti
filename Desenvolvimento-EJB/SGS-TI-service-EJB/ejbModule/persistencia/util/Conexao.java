package persistencia.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import common.exception.BusinessException;
import common.util.SystemConstant;

/**
 * 
 * Classe que é responsável por realizar e fechar a conexão com um banco de
 * dados configurado em um arquivo de propriedades (texto).
 * 
 */
public class Conexao {

	private static final String ARQUIVO_CONF = "META-INF/conexao.properties";
	private static Connection con;
	private static final Properties configuracoes;
	private static Conexao instance;

	/*
	 * código-fonte estático que é executado assim que o ClassLoader carrega a
	 * classe na memória. Assim o atributo de configurações é carregado apenas
	 * uma vez, não precisando ser carregado diversas vezes desnecessariamente.
	 */
	
	static 
	{	
		try {
			InputStream is = Conexao.class.getClassLoader().getResourceAsStream(ARQUIVO_CONF);
			configuracoes = new Properties();
			configuracoes.load(is);
		
			/**
			 * TODO - Arrumar Exceptio
			 */
		} catch (IOException e) {
			throw new RuntimeException("Erro de I/O", e);
		}
	}
	
	private Conexao() {
		try {
			con = criarConexao();
			System.out.println("conexao criada");
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public static Conexao getInstance(){
		if(instance == null)
			instance = new Conexao();
		return instance;
	}
	
	/**
	 * Recupera uma nova conexão
	 * @return Connection
	 * @throws BusinessException 
	 */
	public static Connection obterConexao() throws BusinessException {
		if(instance == null)
			instance = new Conexao();
		return con;
	}

	public Connection criarConexao() throws BusinessException {

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
			System.out.println("abrindo conexao...");
			Connection con = DriverManager.getConnection(url,usuario,senha);
			
			return con;

		} catch (Exception e) {
			throw new BusinessException(SystemConstant.MSG_AM_ERRO_CONEXAO);
		}
}
	/**
	 * Fecha uma conexão.
	 * @param con conexão.
	 */
	public static void fecharConexao(Connection con) {

		/*try {
			System.out.println("fechando conexao..");
			if(con != null && !con.isClosed())
				con.close();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao fechar conexão",e);
		}*/
		
	}
	
	public static String obterOrigem() {
		return configuracoes.getProperty("jdbc.origem");
	}
}
