package common.entity;

import java.util.Date;
import java.util.List;

public class PessoaFisica extends Cliente 
{
	private static final long serialVersionUID = 1L;
	private String nome;
	private String sexo;
	private Date dataNascimento;
	private long CPF;
	private String contato;

	/**
	* TODO - Descrever melhor os campos
	*/
	/**
	 * Construtor.
	 * 
	 * @param endereco
	 * @param porte
	 * @param usuarios
	 * @param nome
	 * @param sexo
	 * @param dataNascimento
	 * @param CPF
	 */
	public PessoaFisica(String endereco, Porte porte, List<Usuario> usuarios,
			String nome, String sexo, Date dataNascimento, long CPF, String contato) {
		
		super(endereco, porte, null, nome, contato);
		this.nome = nome;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.CPF = CPF;
		this.contato = contato;
	}
	
	/*
	 * GETTERs AND SETTERs
	 */
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public long getCPF() {
		return CPF;
	}
	public void setCPF(long cPF) {
		CPF = cPF;
	}
	public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
	}
}
