package persistencia.dao;

import common.entity.PessoaJuridica;

public interface DAOPessoaJuridica 
{
	/**
	 * Método que adiciona no banco de dados um novo StatusChamado.
	 * 
	 */
	abstract boolean adicionaPessoaJuridica(PessoaJuridica pessoaJuridica);

	/**
	 * Metodo que procura um objeto pelo ID
	 * @param codigo
	 * @return
	 */
	abstract PessoaJuridica getById(int codigo);
}
