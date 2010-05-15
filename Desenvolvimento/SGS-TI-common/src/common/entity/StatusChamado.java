package common.entity;

import java.io.Serializable;

public class StatusChamado implements Serializable{
	
	//constantes
	private static final long serialVersionUID = 1L;
	public static final String ABERTO = "ABERTO";
	public static final String PENDENTE = "PENDENTE";
	public static final String AGUARDANDO_CLIENTE = "AGUARDANDO_CLIENTE";
	public static final String AGENDADO = "AGENDADO";
	public static final String FECHADO = "FECHADO";
	
	private String nome;

	/**
	 * Construtor
	 * @param nome
	 * 		Nome dos status, escolhidos a partir das contantes da classe.
	 */
	public StatusChamado(String nome) {
		setNome(nome);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
