package persistencia.sql;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import persistencia.util.Conexao;

public class FabricaSql {
	private static final String ARQUIVO_COMANDOS = "resources/properties/sql.properties";
	private static final boolean DEBUG = false;
	private static Properties sqlProperties;
	private static HashMap<String, String> cache;
	
	/**
	 * Carrega o arquivo de properties.
	 */
	static{
		try{
			sqlProperties = new Properties();
			cache = new HashMap<String, String>();
			
			InputStream fis = Conexao.class.getClassLoader().getResourceAsStream(ARQUIVO_COMANDOS);
			sqlProperties.load(fis);

		} catch (IOException e) {
			throw new RuntimeException("Erro ao ler arquivo de comandos, " + e.getMessage());
		}
	}
	
	
	/**
	 * Retorna o sql associado a ação
	 * 
	 * @param acao ação desejada
	 * @return sql associado
	 */	
	public static String getSql(String acao){
		if(DEBUG)
			System.out.println("Solicitado sql: " + acao);
		
		String sql = "";
		sql = cache.get(acao);
		
		if(sql == null || sql.trim().equals(""))
			sql = sqlProperties.getProperty(acao);
		
		if(sql == null || sql.trim().equals(""))
			throw new IllegalArgumentException("Acao de sql invalida: " + acao);
		
		cache.put(acao, sql);	
		
		return sql;
	}
}
