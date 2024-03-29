package common.entity;

import java.util.List;

public class PessoaJuridica extends Cliente 
{
	private static final long serialVersionUID = 1L;
	private int codigo;
	private String razaoSocial;
	private String nomeFantasia;
	private long CNPJ;
	
	
	
	
	
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
	
	public PessoaJuridica(int codigo,String endereco, Porte porte, List<Usuario> usuarios,
			String razaoSocial, String nomeFantasia, long CNPJ) {
		
		super(endereco, porte, null, nomeFantasia);
		this.codigo = codigo;
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

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
}
