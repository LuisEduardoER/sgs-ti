package common.entity;

import java.util.List;

public class PessoaJuridica extends Cliente 
{
	private static final long serialVersionUID = 1L;
	private String razaoSocial;
	private String nomeFantasia;
	private long CNPJ;

	/**
	* TODO - Descrever melhor os campos
	*/
	/**
	 * Construtor
	 * @param endereco
	 * @param porte
	 * @param usuarios
	 * @param razaoSocial
	 * @param nomeFantasia
	 * @param CNPJ
	 */
	public PessoaJuridica(String endereco, Porte porte, List<Usuario> usuarios,
			String razaoSocial, String nomeFantasia, long CNPJ) {
		
		super(endereco, porte, null, nomeFantasia);
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.CNPJ = CNPJ;
	}
	
	/*
	 * GETTERs AND SETTERs
	 */
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	public long getCNPJ() {
		return CNPJ;
	}
	public void setCNPJ(long cNPJ) {
		CNPJ = cNPJ;
	}
}
