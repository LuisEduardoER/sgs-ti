package persistencia.facade;

import common.entity.StatusChamado;

import persistencia.dao.DAOStatus;
import persistencia.sql.SQLStatus;

public class FacadeStatus 
{	
	/**
	 * TODO - Descrever melhor os campos
	 */
	/**
	 * 
	 * @param tipoFalha
	 * @return
	 */
	public static boolean criarStatus(StatusChamado status)
	{		
		DAOStatus dao = new SQLStatus();
		boolean inserido = dao.adicionaStatus(status);
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
	public static int procurarStatus(StatusChamado status)
	{
		DAOStatus dao = new SQLStatus();	
		int codigo = dao.procurarStatus(status);	
		return codigo;
	}
}
