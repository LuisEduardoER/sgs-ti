package persistencia.facade;

import java.util.List;

import common.entity.PessoaJuridica;
import common.exception.BusinessException;
import persistencia.dao.DAOPessoaJuridica;
import persistencia.sql.SQLPessoaJuridica;

public class FacadePessoaJuridica 
{	
	/**
	 * TODO - Descrever melhor os campos
	 */
	/**
	 * 
	 * @param tipoFalha
	 * @return
	 */
	public static boolean criarPessoaJuridica(PessoaJuridica pessoaJuridica) throws BusinessException
	{	
		return false;
	}
	
	public static List<PessoaJuridica> pesquisarPessoaJuridicaPorDesc(String descricao) throws BusinessException
	{	
		DAOPessoaJuridica dao = new SQLPessoaJuridica();
		return dao.pesquisarPorDescricao(descricao);
	}
	
	/**
	 * Metodo que procura um objeto pelo ID
	 * @param codigo
	 * @return
	 */
	public static PessoaJuridica getById(int codigo) throws BusinessException{
		DAOPessoaJuridica dao = new SQLPessoaJuridica();
		return dao.getById(codigo);
	}
	
}
