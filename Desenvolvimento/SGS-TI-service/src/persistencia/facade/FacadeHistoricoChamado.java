package persistencia.facade;

import common.entity.HistoricoChamado;
import common.exception.BusinessException;

import persistencia.dao.DAOHistoricoChamado;
import persistencia.sql.SQLHistoricoChamado;

public class FacadeHistoricoChamado 
{
	/**
	 * TODO - Descrever melhor os campos
	 */
	/**
	 * 
	 * @param chamado
	 * @return
	 */
	public static boolean criarHistoricoChamado(HistoricoChamado chamado)  throws BusinessException
	{	
		DAOHistoricoChamado dao = new SQLHistoricoChamado();	
		boolean inserido = dao.adicionaHistoricoChamado(chamado);
		return inserido;
	}
}
