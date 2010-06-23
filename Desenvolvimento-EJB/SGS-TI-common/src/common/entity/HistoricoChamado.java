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
	
	public HistoricoChamado(){
	}
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cod_chamado;
		result = prime * result + cod_status;
		result = prime * result + cod_usuario_registro;
		result = prime * result
				+ ((dataAgentamento == null) ? 0 : dataAgentamento.hashCode());
		result = prime * result
				+ ((dataAtualizacao == null) ? 0 : dataAtualizacao.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ (int) (numeroChamado ^ (numeroChamado >>> 32));
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
		HistoricoChamado other = (HistoricoChamado) obj;
		if (cod_chamado != other.cod_chamado)
			return false;
		if (cod_status != other.cod_status)
			return false;
		if (cod_usuario_registro != other.cod_usuario_registro)
			return false;
		if (dataAgentamento == null) {
			if (other.dataAgentamento != null)
				return false;
		} else if (!dataAgentamento.equals(other.dataAgentamento))
			return false;
		if (dataAtualizacao == null) {
			if (other.dataAtualizacao != null)
				return false;
		} else if (!dataAtualizacao.equals(other.dataAtualizacao))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (numeroChamado != other.numeroChamado)
			return false;
		return true;
	}
	
}
