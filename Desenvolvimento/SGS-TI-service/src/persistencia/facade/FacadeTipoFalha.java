package persistencia.facade;

import common.entity.TipoFalha;

import persistencia.dao.DAOTipoFalha;
import persistencia.sql.SQLTipoFalha;

public class FacadeTipoFalha 
{	
	/**
	 * TODO - Descrever melhor os campos
	 */
	/**
	 * 
	 * @param tipoFalha
	 * @return
	 */
	public static boolean criarTipoFalha(TipoFalha tipoFalha)
	{		
		DAOTipoFalha dao = new SQLTipoFalha();
		boolean inserido = dao.adicionaTipoFalha(tipoFalha);
		return inserido;
	}
	
	/**
	 * TODO - Descrever melhor os campos
	 */
	/**
	 * 
	 * @param tipoFalha
	 * @return
	 */
	public static int procurarTipoFalha(TipoFalha tipoFalha)
	{
		DAOTipoFalha dao = new SQLTipoFalha();	
		int codigo = dao.procurarTipoFalha(tipoFalha);	
		return codigo;
	}
	
	/**
	 * Procura um objeto pelo id.
	 * @param codigo
	 * @return
	 */
	public static TipoFalha getById(int codigo){
		DAOTipoFalha dao = new SQLTipoFalha();
		return dao.getById(codigo);
	}
}
