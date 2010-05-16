package common.entity;

import java.io.Serializable;
import java.util.Date;
import common.remote.ObserverUsuario;

public class UsuarioAutenticado implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Date ultimaAtualizacao;
	private ObserverUsuario observador;

	public UsuarioAutenticado() {
	}

	public UsuarioAutenticado(Date ultimaAtualizacao, ObserverUsuario observador) {
		this.ultimaAtualizacao = ultimaAtualizacao;
		this.observador = observador;
	}
	
	public Date getUltimaAtualizacao() {
		return ultimaAtualizacao;
	}
	public void setUltimaAtualizacao(Date ultimaAtualizacao) {
		this.ultimaAtualizacao = ultimaAtualizacao;
	}
	public ObserverUsuario getObservador() {
		return observador;
	}
	public void setObservador(ObserverUsuario observador) {
		this.observador = observador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((observador == null) ? 0 : observador.hashCode());
		result = prime
				* result
				+ ((ultimaAtualizacao == null) ? 0 : ultimaAtualizacao
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		UsuarioAutenticado other = (UsuarioAutenticado) obj;
		if (observador == null) {
			if (other.observador != null)
				return false;
		} else if (!observador.equals(other.observador))
			return false;
		return true;
	}
	
	
	
}
