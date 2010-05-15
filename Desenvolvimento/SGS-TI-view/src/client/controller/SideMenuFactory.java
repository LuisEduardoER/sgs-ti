package client.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import client.model.sideMenu.SideMenu;

/**
 * Fabrica de SideMenu.
 *
 */
public class SideMenuFactory {
	
	// Arquivo de propriedades
	private static Properties comando;
	// Cache de SideMenu já carregados
	private static Map<String, SideMenu> cache;
	
	
	/**
	 * Carrega as propriedades e inicializa o cache.
	 */
	static {
		try {
			comando = new Properties();
			cache = new HashMap<String, SideMenu>();

			FileInputStream fis = new FileInputStream(
					"resources/properties/sideMenu.properties");
			comando.load(fis);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Recupera um side menu a partir de uma String.
	 * 
	 * @param qual
	 *            o identificador do conteudo.
	 * @return Um objeto que implementa a interface SideMenu.
	 */
	public static SideMenu getSideMenu(String qual){
		String classeString = comando.getProperty(qual);

		if (classeString == null || classeString.trim().equals(""))
			throw new IllegalArgumentException("Comando inválido: " + qual);

		try {

			// Antes de devolver a ação, verifica se ela está no cache.
			SideMenu c = cache.get(qual);

			// Se não existir, cria e adiciona no cache
			if (c == null) {

				Class<?> classe = Class.forName(classeString);

				Object obj = classe.newInstance();

				c = (SideMenu) obj;
				
				cache.put(qual,c);
			}

			return c;

		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Classe inexistente: "
					+ classeString, e);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException("Construtor vazio inexistente: "
					+ classeString, e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Construtor não é público: "
					+ classeString, e);
		}
	}
}
