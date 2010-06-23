package common.remote;

import java.util.List;

import javax.ejb.Remote;

import common.entity.TipoChamado;
import common.entity.TipoFalha;
import common.entity.StatusChamado;
import common.exception.BusinessException;

@Remote
public interface ServiceChamadoItens{
	
	/**
	 * Metodo que retorna todos os tipo de falha
	 * @return List<Falha>
	 */
	public List<TipoFalha> procurarFalha();
	
	/**
	 * Metodo que retorna todos os tipo de status
	 * @return List<StatusChamado>
	 */
	public List<StatusChamado> procurarStatus();
	
	/**
	 * procura chamados
	 * @return
	 * @throws BusinessException 
	 */
	public List<TipoChamado> procurarTipoChamado() throws BusinessException;
	
	public List<TipoChamado> tipoChamadoListarTodos() throws BusinessException;
	
	public List<TipoFalha> tipoFalhaListarTodos() throws BusinessException;

}
