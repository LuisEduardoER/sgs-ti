package persistencia.facade;

import java.util.List;

import common.entity.TipoFalha;
import common.exception.BusinessException;
import persistencia.dao.DAOTipoFalha;
import persistencia.sql.SQLTipoFalha;

public class FacadeTipoFalha 
{	
	/**
	 * 
	 * @param tipoFalha
	 * @return
	 */
	public static boolean criarTipoFalha(TipoFalha tipoFalha) throws BusinessException
	{		
		DAOTipoFalha dao = new SQLTipoFalha();
		boolean inserido = dao.adicionaTipoFalha(tipoFalha);
		return inserido;
	}

	/**
	 * 
	 * @param tipoFalha
	 * @return
	 */
	public static int procurarTipoFalha(TipoFalha tipoFalha) throws BusinessException
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
	public static TipoFalha getById(int codigo) throws BusinessException{
		DAOTipoFalha dao = new SQLTipoFalha();
		return dao.getById(codigo);
	}
	
	/**
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public static List<TipoFalha> listarTodos() throws BusinessException
	{
		DAOTipoFalha dao = new SQLTipoFalha();
		return dao.listarTodos();	
	}
}
