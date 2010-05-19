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

	/**
	* TODO - Documentar
	*/
	public UsuarioAutenticado() {
	}

	/**
	* TODO - Descrever melhor os campos
	*/
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
	
	/**
	* TODO - Documentar
	*/
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
