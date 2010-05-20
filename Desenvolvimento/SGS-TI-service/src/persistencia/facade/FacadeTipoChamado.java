package persistencia.facade;

import common.entity.TipoChamado;
import common.exception.BusinessException;

import persistencia.dao.DAOTipoChamado;
import persistencia.sql.SQLTipoChamado;

public class FacadeTipoChamado 
{
	
	/**
	 * TODO - Descrever melhor os campos
	 */
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
	 * TODO - Descrever melhor os campos
	 */
	/**
	 * 
	 * @param tipoChamado
	 * @return
	 */
	public static int procurarTipoChamado(TipoChamado tipoChamado) throws BusinessException
	{
		DAOTipoChamado dao = new SQLTipoChamado();
		int codigo = dao.procurarTipoChamado(tipoChamado);	
		return codigo;
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
}
