package common.entity;

import java.io.Serializable;

import java.util.Date;
import java.util.Random;

public class Chamado implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private long numeroChamado;
	private Date dataHoraAbertura;
	private Date dataHoraFechamento;
	private Date dataHoraAgendamento;
	private String detalhes;
	private StatusChamado status;
	private Prioridade prioridade;
	private TipoChamado tipoChamado;
	private TipoFalha tipoFalha;
	private String responsavel;
	private String contato;
	private Cliente reclamante;
	private Usuario usuarioResgistro;

	/**
	 * Construtor vazio
	 */
	public Chamado(){};
	
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
	public Chamado(Date dataHoraAbertura,TipoChamado tipoChamado, Cliente reclamante) 
	{
		setNumeroChamado(new Random().nextInt(999999999));
		this.dataHoraAbertura = dataHoraAbertura;
		this.dataHoraFechamento = null;
		this.detalhes = "";
		this.status = new StatusChamado(StatusChamado.ABERTO);
		this.tipoChamado = tipoChamado;
		this.reclamante = reclamante;
		this.tipoFalha = null;
		this.prioridade = new Prioridade(getTipoChamado().getValor(), getReclamante().getPorte().getValor(), new Date());
	}

	/**
	 * 
	 * Construtor
	 * 
	 * @param numeroChamado
	 * 		Identificação do chamado.
	 * @param dataHoraAbertura
	 * 		Data e Hora da abertura do chamado.
	 * @param dataHoraFechamento
	 * 		Data e Hora do fechamento do chamado.
	 * @param detalhes
	 * 		Informações extras do problema do chamado.
	 * @param status
	 * 		Status do chamado.
	 * @param prioridade
	 * 		Prioridade do chamado.
	 * @param tipoChamado
	 * 		Tipo do chamado.
	 * @param reclamante
	 * 		Pessoa ou empresa que abriu a reclamação.
	 * @param tipoFalha
	 * 		Tipo de falha
	 * @param responsavel
	 * 		Pessoa que solicitou a abertura do chamado.
	 * @param usuarioRegistro
	 * 		Pessoa que registrou o chamado.
	 */
	public Chamado(long numeroChamado, Date dataHoraAbertura, Date dataHoraFechamento,
			String detalhes, StatusChamado status, Prioridade prioridade,
			TipoChamado tipoChamado, Cliente reclamante, TipoFalha tipoFalha,
			String responsavel, Usuario usuarioRegistro, Date dataHoraAgendamento,String contato)
	{
		this.numeroChamado = numeroChamado;
		this.dataHoraAbertura = dataHoraAbertura;
		this.dataHoraFechamento = dataHoraFechamento;
		this.detalhes = detalhes;
		this.status = status;
		this.tipoChamado = tipoChamado;
		this.reclamante = reclamante;
		this.tipoFalha = tipoFalha;
		this.responsavel = responsavel;
		this.usuarioResgistro = usuarioRegistro;
		this.dataHoraAgendamento = dataHoraAgendamento;
		this.prioridade = new Prioridade(getTipoChamado().getValor(), getReclamante().getPorte().getValor(), new Date());
		this.contato = contato;
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
	public TipoFalha getTipoFalha() {
		return tipoFalha;
	}
	public void setTipoFalha(TipoFalha tipoFalha) {
		this.tipoFalha = tipoFalha;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public Usuario getUsuarioResgistro() {
		return usuarioResgistro;
	}
	public void setUsuarioResgistro(Usuario usuarioResgistro) {
		this.usuarioResgistro = usuarioResgistro;
	}
	public Date getDataHoraAgendamento() {
		return dataHoraAgendamento;
	}
	public void setDataHoraAgendamento(Date dataHoraAgendamento) {
		this.dataHoraAgendamento = dataHoraAgendamento;
	}
	public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
	}

	/**
	 * TODO - Documentar
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
		+ (int) (numeroChamado ^ (numeroChamado >>> 32));
		return result;
	}

	/**
	 * TODO - Documentar
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chamado other = (Chamado) obj;
		if (numeroChamado != other.numeroChamado)
			return false;
		return true;
	}
}
