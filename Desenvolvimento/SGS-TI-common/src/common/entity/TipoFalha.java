package common.entity;

import java.io.Serializable;

public class TipoFalha implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String HARDWARE = "HARDWARE";
	public static final String SOFTWARE = "SOFTWARE";
	public static final String DUVIDA = "DUVIDA";
	
	private int codigo;
	private String nome;

	/**
	* TODO - Descrever melhor os campos
	*/
	/**
	 * Construdor
	 * 
	 * @param nome
	 */
	public TipoFalha(int codigo, String nome)
	{
		this.nome=nome;
		this.codigo = codigo;
	}
	
	public TipoFalha(String nome)
	{
		this.nome=nome;
	}
	
	public TipoFalha()
	{
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		TipoFalha other = (TipoFalha) obj;
		if (codigo != other.codigo)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.getNome();
	}	
}
