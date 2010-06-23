package common.entity;

import java.io.Serializable;

public class Porte implements Serializable  
{
	private static final long serialVersionUID = 1L;
	public static final String GRANDE = "GRANDE";
	public static final String MEDIA = "MEDIA";
	public static final String PEQUENA = "PEQUENA";
	public static final String PF = "PF";
	
	private String nome;
	private int valor;
	
	/**
	 * Construtor
	 * @param nome
	 * 		Nome do porte.
	 */
	public Porte(String nome) {
		this.nome = nome;
		
		// TODO: ver de onde vai pegar o valor
		this.valor = 1;
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
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + valor;
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
		Porte other = (Porte) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (valor != other.valor)
			return false;
		return true;
	}
	
}
