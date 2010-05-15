package common.entity.cliente;

import java.io.Serializable;

public class Pessoa implements Serializable  {

	private static final long serialVersionUID = 1L;
	private String nome;
	private Porte porte;

	public Pessoa(String nome, Porte porte){
		setNome(nome);
		setPorte(porte);
	}
	
	// TODO: criar o resto;
	
	/*
	 * GETTERs AND SETTERs 
	 */
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Porte getPorte() {
		return porte;
	}
	public void setPorte(Porte porte) {
		this.porte = porte;
	}
}
