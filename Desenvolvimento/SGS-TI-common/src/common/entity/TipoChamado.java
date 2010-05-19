package common.entity;

import java.io.Serializable;

public class TipoChamado implements Serializable  
{
	private static final long serialVersionUID = 1L;
	public static final String URGENTE = "URGENTE";
	public static final String PROGRAMADO = "PROGRAMADO";
	public static final String NORMAL = "NORMAL";
	public static final String INFORMATIVO = "INFORMATIVO";
	
	private String nome;
	private int valor;
	
	/**
	 * Construtor
	 * @param nome
	 * 		Nome do tipo de chamado.
	 */
	public TipoChamado(String nome) {
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
}
