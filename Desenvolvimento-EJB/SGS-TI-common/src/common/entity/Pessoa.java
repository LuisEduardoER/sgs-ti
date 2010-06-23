package common.entity;

import java.io.Serializable;

public abstract class Pessoa implements Serializable  
{
	private static final long serialVersionUID = 1L;
	protected String endereco;
	
	/**
	 * Construtor
	 * @param endereco
	 * 		Endereço da Pessoa.
	 */
	public Pessoa(String endereco) {
		setEndereco(endereco);
	}

	/*
	 * GETTERs AND SETTERs
	 */
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endereco == null) ? 0 : endereco.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		return true;
	}
	
}
