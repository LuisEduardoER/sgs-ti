package persistencia.facade;

import java.util.List;

import common.entity.PessoaJuridica;
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
	public static boolean criarPessoaJuridica(PessoaJuridica pessoaJuridica)
	{	
		return false;
	}
	
	public static List<PessoaJuridica> pesquisarPessoaJuridicaPorDesc(String descricao)
	{	
		DAOPessoaJuridica dao = new SQLPessoaJuridica();
		dao.pesquisarPorDescricao(descricao);
		return null;
	}
	
	/**
	 * Metodo que procura um objeto pelo ID
	 * @param codigo
	 * @return
	 */
	public static PessoaJuridica getById(int codigo){
		DAOPessoaJuridica dao = new SQLPessoaJuridica();
		return dao.getById(codigo);
	}
	
}
