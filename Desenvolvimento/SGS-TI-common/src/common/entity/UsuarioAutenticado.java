package common.entity;

import java.io.Serializable;
import java.util.Date;
import common.remote.ObserverUsuario;

public class UsuarioAutenticado implements Serializable, Cloneable{

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private Date ultimaAtualizacao;
	private ObserverUsuario observador;
	public static final int ENCERRAR_CLIENTE 		= -1;
	public static final int TEMPO_MAX_EXPIRANDO 	=  0;

	public UsuarioAutenticado() {
	}

	public UsuarioAutenticado(Date ultimaAtualizacao, ObserverUsuario observador, Usuario usuario) {
		this.ultimaAtualizacao = ultimaAtualizacao;
		this.observador = observador;
		this.usuario = usuario;
	}
	
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
		// TODO Auto-generated method stub
		return super.clone();
	}
	
}
