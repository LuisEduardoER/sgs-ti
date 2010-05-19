package common.entity;

import java.util.List;

public abstract class Cliente extends Pessoa 
{
	private static final long serialVersionUID = 1L;
	protected Porte porte;
	private List<Usuario> usuarios;
	private String nome;
	private String contato;
	
	/**
	 * Construtor
	 * @param endereco
	 * 		Endereço da classe Pessoa
	 * @param porte
	 * 		Porte do Cliente
	 */
	public Cliente(String endereco, 
			Porte porte, 
			List<Usuario> usuarios, 
			String nome,
			String contato) {
		super(endereco);
		setPorte(porte);
		setUsuarios(usuarios);
		setNome(nome);
		setContato(contato);
	}
	
	/*
	 * GETTERs AND SETTERs
	 */
	public Porte getPorte() {
		return porte;
	}
	public void setPorte(Porte porte) {
		this.porte = porte;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}
}
