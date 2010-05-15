package common.entity.cliente;

import java.io.Serializable;

public class Porte implements Serializable  {

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
}
