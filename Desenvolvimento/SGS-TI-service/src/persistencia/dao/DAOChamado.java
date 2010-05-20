package persistencia.dao;

import java.util.List;

import common.entity.Chamado;
import common.entity.HistoricoChamado;
import common.exception.BusinessException;

public interface DAOChamado {
	
	/**
	 * Método que adiciona no banco de dados um novo Chamado.
	 * 
	 * @param user
	 * 		Objeto Chamado.
	 * @param chamado
	 * 		chamado.
	 * @return
	 * 		Boolean
	 */
	abstract boolean adicionaChamado(Chamado chamado) throws BusinessException;


	/**
	 * Método que atualiza o chamado.
	 * 
	 * @param user
	 * 		Objeto Chamado.
	 * @return
	 * 		Boolean
	 */
	abstract boolean atualizarChamado(Chamado chamado) throws BusinessException;

	/**
	 * Método que busca o chamado.
	 * 
	 * @param user
	 * 		Objeto Chamado.
	 * @return
	 * 		Chamado
	 */
	abstract HistoricoChamado buscarChamado(Chamado chamado) throws BusinessException;

	
	/**
	 * Método que busca o chamado.
	 * 
	 * @param user
	 * 		Objeto Chamado.
	 * @return
	 * 		Chamado
	 */
	abstract List<Chamado> buscarChamadosAbertos() throws BusinessException;

	/**
	 * Método que busca o chamado agendado.
	 * 
	 * @param user
	 * 		Objeto Chamado.
	 * @return
	 * 		Chamado
	 */
	abstract List<Chamado> buscarChamadosAgendado();
}
