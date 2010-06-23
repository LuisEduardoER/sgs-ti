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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (CNPJ ^ (CNPJ >>> 32));
		result = prime * result + codigo;
		result = prime * result
				+ ((nomeFantasia == null) ? 0 : nomeFantasia.hashCode());
		result = prime * result
				+ ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PessoaJuridica other = (PessoaJuridica) obj;
		if (CNPJ != other.CNPJ)
			return false;
		if (codigo != other.codigo)
			return false;
		if (nomeFantasia == null) {
			if (other.nomeFantasia != null)
				return false;
		} else if (!nomeFantasia.equals(other.nomeFantasia))
			return false;
		if (razaoSocial == null) {
			if (other.razaoSocial != null)
				return false;
		} else if (!razaoSocial.equals(other.razaoSocial))
			return false;
		return true;
	}
	
}
