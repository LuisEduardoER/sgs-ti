package common.entity;

import java.io.Serializable;

public class TipoFalha implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String HARDWARE = "HARDWARE";
	public static final String SOFTWARE = "SOFTWARE";
	public static final String DUVIDA = "DUVIDA";
	private String nome;

	/**
	* TODO - Descrever melhor os campos
	*/
	/**
	 * Construdor
	 * 
	 * @param nome
	 */
	public TipoFalha(String nome)
	{
		setNome(nome);
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

	/**
	* TODO - Documentar
	*/
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	/**
	* TODO - Documentar
	*/
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoFalha other = (TipoFalha) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}
