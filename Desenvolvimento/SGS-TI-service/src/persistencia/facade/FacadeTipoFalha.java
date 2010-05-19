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
}
