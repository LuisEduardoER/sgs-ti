package common.entity;

import java.util.Date;

public class Funcionario extends Pessoa {

	private static final long serialVersionUID = 1L;
	private String matricula;
	private String nome;
	private String sexo;
	private Date dataNascimento;
	private Date dataAdmissao;
	private Date dataDemissao;
	private long CPF;
	
	public Funcionario(String matricula, String nome, String sexo,
			Date dataNascimento, Date dataAdmissao, String endereco, long CPF ) {
		
		super(endereco);
		this.matricula = matricula;
		this.nome = nome;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.dataAdmissao = dataAdmissao;
		this.dataDemissao = null;
		this.CPF = CPF;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
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
	public Date getDataAdmissao() {
		return dataAdmissao;
	}
	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}
	public Date getDataDemissao() {
		return dataDemissao;
	}
	public void setDataDemissao(Date dataDemissao) {
		this.dataDemissao = dataDemissao;
	}
	public long getCPF() {
		return CPF;
	}
	public void setCPF(long cPF) {
		CPF = cPF;
	}
}
