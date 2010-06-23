package common.entity;

import java.util.List;

public abstract class Cliente extends Pessoa 
{
	private static final long serialVersionUID = 1L;
	protected Porte porte;
	private List<Usuario> usuarios;
	private String nome;
	
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
			String nome) {
		super(endereco);
		setPorte(porte);
		setUsuarios(usuarios);
		setNome(nome);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((porte == null) ? 0 : porte.hashCode());
		result = prime * result
				+ ((usuarios == null) ? 0 : usuarios.hashCode());
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
		Cliente other = (Cliente) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (porte == null) {
			if (other.porte != null)
				return false;
		} else if (!porte.equals(other.porte))
			return false;
		if (usuarios == null) {
			if (other.usuarios != null)
				return false;
		} else if (!usuarios.equals(other.usuarios))
			return false;
		return true;
	}

}
