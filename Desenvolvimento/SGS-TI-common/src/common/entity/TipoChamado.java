package common.entity;

import java.io.Serializable;

public class TipoChamado implements Serializable  
{
	private static final long serialVersionUID = 1L;
	public static final String URGENTE = "URGENTE";
	public static final String PROGRAMADO = "PROGRAMADO";
	public static final String NORMAL = "NORMAL";
	public static final String INFORMATIVO = "INFORMATIVO";
	
	private int codigo;
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

	public TipoChamado(String nome, int prioridade) {
		setNome(nome);
		setValor(prioridade);
	}
	
	public TipoChamado(int codigo,String nome, int prioridade) {
		this.codigo = codigo;
		this.nome = nome;
		this.valor = prioridade;
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
		TipoChamado other = (TipoChamado) obj;
		if (codigo != other.codigo)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (valor != other.valor)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.getNome();
	}
}
