package common.entity;

import java.io.Serializable;
import java.util.Date;

public class Prioridade implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private int valorTipoChamado;
	private int valorPorte;
	private int valorEspera;
	private Date horaAtualizacao;
	private int valorPrioridade;
	
	
	/**
	 * Construtor.
	 * @param valorTipoChamado
	 * @param valorPorte
	 * @param horaAtualizacao
	 */
	public Prioridade(int valorTipoChamado, int valorPorte, Date horaAtualizacao) {
		this.valorTipoChamado = valorTipoChamado;
		this.valorPorte = valorPorte;
		this.horaAtualizacao = horaAtualizacao;
		atualizarPrioridade();
	}
	
	
	/**
	 * Atualiza o valor da prioridade, sendo que esta é valor
	 * compostos pelos indicadores abaixo:
	 * 
	 * valor da centena: valor do porte.
	 * valor da dezena: valor do tipo de chamado.
	 * valor da unidade: valor atribuido ao tempo de espera.
	 * 
	 */
	private void atualizarPrioridade() {
		String valor = String.valueOf(getValorPorte()) + String.valueOf(getValorTipoChamado()) + String.valueOf(getValorEspera());
		setValorPrioridade(Integer.parseInt(valor) );
	}


	/*
	 * GETTERs AND SETTERs
	 */
	public int getValorTipoChamado() {
		return valorTipoChamado;
	}
	public void setValorTipoChamado(int valorTipoChamado) {
		this.valorTipoChamado = valorTipoChamado;
	}
	public int getValorPorte() {
		return valorPorte;
	}
	public void setValorPorte(int valorPorte) {
		this.valorPorte = valorPorte;
	}
	public Date getHoraAtualizacao() {
		return horaAtualizacao;
	}
	public void setHoraAtualizacao(Date horaAtualizacao) {
		this.horaAtualizacao = horaAtualizacao;
	}
	public int getValorPrioridade() {
		return valorPrioridade;
	}
	public void setValorPrioridade(int valorPrioridade) {
		this.valorPrioridade = valorPrioridade;
	}
	public int getValorEspera() {
		return valorEspera;
	}
	public void setValorEspera(int valorEspera) {
		this.valorEspera = valorEspera;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((horaAtualizacao == null) ? 0 : horaAtualizacao.hashCode());
		result = prime * result + valorEspera;
		result = prime * result + valorPorte;
		result = prime * result + valorPrioridade;
		result = prime * result + valorTipoChamado;
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
		Prioridade other = (Prioridade) obj;
		if (horaAtualizacao == null) {
			if (other.horaAtualizacao != null)
				return false;
		} else if (!horaAtualizacao.equals(other.horaAtualizacao))
			return false;
		if (valorEspera != other.valorEspera)
			return false;
		if (valorPorte != other.valorPorte)
			return false;
		if (valorPrioridade != other.valorPrioridade)
			return false;
		if (valorTipoChamado != other.valorTipoChamado)
			return false;
		return true;
	}	
	
}
