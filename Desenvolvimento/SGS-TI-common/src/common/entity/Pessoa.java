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
}
