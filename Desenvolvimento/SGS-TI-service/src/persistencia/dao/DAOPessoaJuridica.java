package persistencia.dao;

import java.util.List;

import common.entity.PessoaJuridica;
import common.exception.BusinessException;

public interface DAOPessoaJuridica 
{
	/**
	 * Método que adiciona no banco de dados um novo StatusChamado.
	 * 
	 */
	abstract boolean adicionaPessoaJuridica(PessoaJuridica pessoaJuridica) throws BusinessException;

	/**
	 * Metodo que procura um objeto pelo ID
	 * @param codigo
	 * @return
	 */
	abstract PessoaJuridica getById(int codigo) throws BusinessException;

	abstract List<PessoaJuridica> pesquisarPorDescricao(String descricao) throws BusinessException;
}
