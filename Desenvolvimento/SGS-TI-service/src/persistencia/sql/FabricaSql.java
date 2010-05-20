package persistencia.sql;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class FabricaSql {
	private static final String ARQUIVO_COMANDOS = "resources/properties/sql.properties";
	private static final boolean DEBUG = true;
	private static Properties sqlProperties;
	private static HashMap<String, String> cache;
	
	/**
	 * Carrega o arquivo de properties.
	 */
	static
	{	
		cache = new HashMap<String, String>();
		
		File f = new File(ARQUIVO_COMANDOS);

		if (!f.exists())
			throw new IllegalArgumentException("Arquivo de configurações não "
					+ "encontrado " + ARQUIVO_COMANDOS);

		try {
			FileInputStream fis = new FileInputStream(f);
			sqlProperties = new Properties();
			sqlProperties.load(fis);
			fis.close();
		} catch (IOException e) {
			throw new RuntimeException("Erro de I/O", e);
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
