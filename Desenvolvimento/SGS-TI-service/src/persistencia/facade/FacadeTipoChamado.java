package persistencia.facade;

import common.entity.TipoChamado;

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
	public static boolean criarTipoChamado(TipoChamado tipoChamado)
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
	public static int procurarTipoChamado(TipoChamado tipoChamado)
	{
		DAOTipoChamado dao = new SQLTipoChamado();
		int codigo = dao.procurarTipoChamado(tipoChamado);	
		return codigo;
	}
}
