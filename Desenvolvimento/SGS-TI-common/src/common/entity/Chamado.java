package common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;


public class Chamado implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long numeroChamado;
	private Date dataHoraAbertura;
	private Date dataHoraFechamento;
	private String detalhes;
	private StatusChamado status;
	private Prioridade prioridade;
	private TipoChamado tipoChamado;
	private Cliente reclamante;
	
	/**
	 * Construtor.
	 * 
	 * @param dataHoraAbertura
	 * 		Data e Hora da abertura do chamado. 
	 * @param tipoChamado
	 * 		Tipo do chamado.
	 * @param reclamante
	 * 		Pessoa ou empresa que abriu a reclamação.
	 */
	public Chamado(Date dataHoraAbertura,TipoChamado tipoChamado, Cliente reclamante) {
		setNumeroChamado(new Random().nextInt(999999999));
		this.dataHoraAbertura = dataHoraAbertura;
		this.dataHoraFechamento = null;
		this.detalhes = "";
		this.status = new StatusChamado(StatusChamado.ABERTO);
		this.tipoChamado = tipoChamado;
		this.reclamante = reclamante;
		this.prioridade = new Prioridade(getTipoChamado().getValor(), getReclamante().getPorte().getValor(), new Date());
	}

	/*
	 * GETTERs AND SETTERs
	 */
	public long getNumeroChamado() {
		return numeroChamado;
	}
	public void setNumeroChamado(long numeroChamado) {
		this.numeroChamado = numeroChamado;
	}
	public Date getDataHoraAbertura() {
		return dataHoraAbertura;
	}
	public void setDataHoraAbertura(Date dataHoraAbertura) {
		this.dataHoraAbertura = dataHoraAbertura;
	}
	public Date getDataHoraFechamento() {
		return dataHoraFechamento;
	}
	public void setDataHoraFechamento(Date dataHoraFechamento) {
		this.dataHoraFechamento = dataHoraFechamento;
	}
	public String getDetalhes() {
		return detalhes;
	}
	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}
	public StatusChamado getStatus() {
		return status;
	}
	public void setStatus(StatusChamado status) {
		this.status = status;
	}
	public Prioridade getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}
	public TipoChamado getTipoChamado() {
		return tipoChamado;
	}
	public void setTipoChamado(TipoChamado tipoChamado) {
		this.tipoChamado = tipoChamado;
	}
	public Cliente getReclamante() {
		return reclamante;
	}
	public void setReclamante(Cliente reclamante) {
		this.reclamante = reclamante;
	}
}
