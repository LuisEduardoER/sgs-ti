package common.entity;

import java.util.Date;
import java.util.List;

public class PessoaFisica extends Cliente 
{
	private static final long serialVersionUID = 1L;
	private String nome;
	private String sexo;
	private Date dataNascimento;
	private long CPF;
	
	/**
	 * Construtor.
	 * 
	 * @param endereco
	 * @param porte
	 * @param usuarios
	 * @param nome
	 * @param sexo
	 * @param dataNascimento
	 * @param CPF
	 */
	public PessoaFisica(String endereco, Porte porte, List<Usuario> usuarios,
			String nome, String sexo, Date dataNascimento, long CPF) {
		
		super(endereco, porte, null, nome);
		this.nome = nome;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.CPF = CPF;
	}
	
	/*
	 * GETTERs AND SETTERs
	 */
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public long getCPF() {
		return CPF;
	}
	public void setCPF(long cPF) {
		CPF = cPF;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (CPF ^ (CPF >>> 32));
		result = prime * result
				+ ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
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
		PessoaFisica other = (PessoaFisica) obj;
		if (CPF != other.CPF)
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		return true;
	}
	
}
