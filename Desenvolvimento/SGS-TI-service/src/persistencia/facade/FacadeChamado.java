package persistencia.facade;

import java.util.List;

import common.entity.Chamado;
import common.entity.HistoricoChamado;
import common.exception.BusinessException;

import persistencia.dao.DAOChamado;
import persistencia.sql.SQLChamado;

public class FacadeChamado 
{
	/**
	 * 
	 * @param chamado
	 * @return
	 */
	public static boolean criarChamado(Chamado chamado) throws BusinessException
	{	
		DAOChamado dao = new SQLChamado();	
		boolean inserido = dao.adicionaChamado(chamado);
		return inserido;
	}

	/**
	 * 
	 * @param chamado
	 * @return
	 */
	public static boolean atualizarChamado(Chamado chamado) throws BusinessException
	{
		DAOChamado dao = new SQLChamado();		
		boolean atualizado = dao.atualizarChamado(chamado);	
		return atualizado;
	}

	/**
	 * 
	 * @param chamado
	 * @return
	 */
	public static HistoricoChamado buscarChamado(Chamado chamado) throws BusinessException 
	{
		DAOChamado dao = new SQLChamado();		
		HistoricoChamado chamadoNovo = dao.buscarChamado(chamado);	
		return chamadoNovo;
	}
	
	/**
	 * M�todo que busca todos os chamados abertos.
	 * @return
	 */
	public static List<Chamado> buscarChamadosAbertos() throws BusinessException{
		DAOChamado dao = new SQLChamado();
		return dao.buscarChamadosAbertos();
	}
	
	/**
	 * M�todo que busca todos os chamados agendados.
	 * @return
	 * @throws BusinessException 
	 */
	public static List<Chamado> buscarChamadosAgendados() throws BusinessException{
		DAOChamado dao = new SQLChamado();
		return dao.buscarChamadosAgendado();
	}
}
