package persistencia.facade;

import java.util.List;

import common.entity.TipoChamado;
import common.exception.BusinessException;

import persistencia.dao.DAOTipoChamado;
import persistencia.sql.SQLTipoChamado;

public class FacadeTipoChamado 
{
	
	/**
	 * 
	 * @param tipoChamado
	 * @return
	 */
	public static boolean criarTipoChamado(TipoChamado tipoChamado) throws BusinessException
	{
		DAOTipoChamado dao = new SQLTipoChamado();
		boolean inserido = dao.adicionaTipoChamado(tipoChamado);
		return inserido;
	}

	/**
	 * 
	 * @param tipoChamado
	 * @return
	 * @throws BusinessException 
	 */
	public static List<TipoChamado> listarTodos() throws BusinessException
	{
		DAOTipoChamado dao = new SQLTipoChamado();
		return dao.listarTodos();	
	}
	
	/**
	 * Procura um tipo de chamado pelo id.
	 * @param codigo
	 * @return
	 */
	public static TipoChamado getById(int codigo) throws BusinessException{
		DAOTipoChamado dao = new SQLTipoChamado();
		return dao.getById(codigo);
	}
	
	/**
	 * 
	 * @param tipoChamado
	 * @return
	 * @throws BusinessException
	 */
	public static int procurarTipoChamado(TipoChamado tipoChamado) throws BusinessException{
		DAOTipoChamado dao = new SQLTipoChamado();
		return dao.procurarTipoChamado(tipoChamado);
	}
}
