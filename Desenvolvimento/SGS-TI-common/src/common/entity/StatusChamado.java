package common.entity;

import java.io.Serializable;

public class StatusChamado implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String ABERTO = "ABERTO";
	public static final String PENDENTE = "PENDENTE";
	public static final String AGUARDANDO_CLIENTE = "AGUARDANDO_CLIENTE";
	public static final String AGENDADO = "AGENDADO";
	public static final String FINALIZADO = "FINALIZADO";
	
	private String nome;
	private int codigo;

	
	public StatusChamado(){
	}
	/**
	 * Construtor
	 * @param nome
	 * 		Nome dos status, escolhidos a partir das contantes da classe.
	 */
	public StatusChamado(String nome) {
		setNome(nome);
	}
	
	public StatusChamado(int codigo, String nome) {
		this.nome = nome;
		this.codigo = codigo;
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

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
}
