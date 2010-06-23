package persistencia.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import common.exception.BusinessException;
import common.util.SystemConstant;
import common.util.Utils;

/**
 * 
 * Classe que é responsável por realizar e fechar a conexão com um banco de
 * dados configurado em um arquivo de propriedades (texto).
 * 
 */
public class Conexao {

	private static final String ARQUIVO_CONF = "META-INF/resources/properties/conexao.properties";
	private static Connection con;
	private Properties configuracoes;
	private static Conexao instance;
	
	private Conexao() throws BusinessException {
		try {
			InputStream is = Conexao.class.getClassLoader().getResourceAsStream(ARQUIVO_CONF);
			
			if(is==null)
				throw new BusinessException("Arquivo de configurações da conexão inválido.");
			
			configuracoes = new Properties();
			configuracoes.load(is);
			
			con = criarConexao();
			System.out.println("conexao criada");
			
			
		} catch (IOException e) {
			Utils.printErro(Conexao.class.getName(), e);
			throw new BusinessException("Erro ao ler o arquivo de propriedades da conexão.");
		}
	}
	
	public static Conexao getInstance() throws BusinessException {
		if(instance == null)
			instance = new Conexao();
		return instance;
	}
	
	/**
	 * Recupera uma nova conexão
	 * @return Connection
	 * @throws BusinessException 
	 */
	public Connection obterConexao() throws BusinessException {
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
			Utils.printErro(Conexao.class.getName(), e);
			throw new BusinessException(SystemConstant.MSG_AM_ERRO_CONEXAO);
		}
}
	/**
	 * Fecha uma conexão.
	 * @param con conexão.
	 */
	public void fecharConexao(Connection con) {

		/*try {
			System.out.println("fechando conexao..");
			if(con != null && !con.isClosed())
				con.close();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao fechar conexão",e);
		}*/
		
	}
	
	public String obterOrigem() {
		return configuracoes.getProperty("jdbc.origem");
	}
}
