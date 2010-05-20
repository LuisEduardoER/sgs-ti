package persistencia.facade;

import common.entity.StatusChamado;
import common.exception.BusinessException;
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
	public static boolean criarStatus(StatusChamado status) throws BusinessException
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
	public static int procurarStatus(StatusChamado status) throws BusinessException
	{
		DAOStatus dao = new SQLStatus();	
		int codigo = dao.procurarStatus(status);	
		return codigo;
	}
	
	/**
	 * Metodo que procura um status pelo ID
	 * @param codigo
	 * @return
	 */
	public static StatusChamado getById(int codigo) throws BusinessException{
		DAOStatus dao = new SQLStatus();
		StatusChamado status = dao.getById(codigo);
		return status;
	}
	
}
