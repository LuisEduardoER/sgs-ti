package common.entity;

import java.io.Serializable;
import java.util.Date;
import common.remote.ObserverUsuario;

public class UsuarioAutenticado implements Serializable, Cloneable
{

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private Date ultimaAtualizacao;
	private ObserverUsuario observador;
	public static final int ENCERRAR_CLIENTE 		= -1;
	public static final int TEMPO_MAX_EXPIRANDO 	=  0;

	public UsuarioAutenticado() {
	}

	/**
	 * 
	 * @param ultimaAtualizacao
	 * @param observador
	 * @param usuario
	 */
	public UsuarioAutenticado(Date ultimaAtualizacao, ObserverUsuario observador, Usuario usuario) {
		this.ultimaAtualizacao = ultimaAtualizacao;
		this.observador = observador;
		this.usuario = usuario;
	}
	
	/*
	 * GETTERs AND SETTERs
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((ultimaAtualizacao == null) ? 0 : ultimaAtualizacao
						.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		UsuarioAutenticado other = (UsuarioAutenticado) obj;
		if (ultimaAtualizacao == null) {
			if (other.ultimaAtualizacao != null)
				return false;
		} else if (!ultimaAtualizacao.equals(other.ultimaAtualizacao))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
}
