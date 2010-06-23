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
		StatusChamado other = (StatusChamado) obj;
		if (codigo != other.codigo)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
}
