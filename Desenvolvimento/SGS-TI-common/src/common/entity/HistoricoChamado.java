package common.entity;

import java.io.Serializable;
import java.util.Date;

public class HistoricoChamado implements Serializable
{

	private static final long serialVersionUID = 1L;
	private long numeroChamado;
	private Date dataAtualizacao;
	private String descricao;
	private Date dataAgentamento;
	private int cod_status;
	private int cod_usuario_registro;
	private int cod_chamado;
	
	public HistoricoChamado(Date dataAtualizacao, String descricao, Date dataAgentamento,
			int cod_status,int cod_usuario_registro, int cod_chamado)
	{
		this.cod_chamado = cod_chamado;
		this.cod_status = cod_status;
		this.cod_usuario_registro = cod_usuario_registro;
		this.dataAgentamento = dataAgentamento;
		this.dataAtualizacao = dataAtualizacao;
		this.descricao = descricao;
	}
	
	public long getNumeroChamado() {
		return numeroChamado;
	}
	public void setNumeroChamado(long numeroChamado) {
		this.numeroChamado = numeroChamado;
	}
	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}
	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataAgentamento() {
		return dataAgentamento;
	}
	public void setDataAgentamento(Date dataAgentamento) {
		this.dataAgentamento = dataAgentamento;
	}
	public int getCod_status() {
		return cod_status;
	}
	public void setCod_status(int codStatus) {
		cod_status = codStatus;
	}
	public int getCod_usuario_registro() {
		return cod_usuario_registro;
	}
	public void setCod_usuario_registro(int codUsuarioRegistro) {
		cod_usuario_registro = codUsuarioRegistro;
	}
	public int getCod_chamado() {
		return cod_chamado;
	}
	public void setCod_chamado(int codChamado) {
		cod_chamado = codChamado;
	}
}
