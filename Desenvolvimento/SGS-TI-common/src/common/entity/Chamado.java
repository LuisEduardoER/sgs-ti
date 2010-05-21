package common.entity;

import java.io.Serializable;

import java.util.Date;

public class Chamado implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	private int codigo;
	private Date dataAbertura;
	private Date dataFechamento;
	private String detalhes;
	private String responsavel;
	private String contato;
	private Date dataAgendamento;
	private StatusChamado status;
	private TipoChamado tipoChamado;
	private TipoFalha tipoFalha;
	private Usuario usuario;
	private PessoaJuridica pj;
	
	//outros
	private Prioridade prioridade;

	public Chamado(){
	}
	
	// Construtor para criar chamado
	public Chamado(Date dataAbertura, Date dataFechamento, String descricao,
			Usuario usuarioLogado, Date dataAgenda, TipoChamado tc, TipoFalha tf,
			PessoaJuridica pj, StatusChamado status){
		this.dataAbertura = dataAbertura;
		this.dataFechamento = dataFechamento;
		this.detalhes = descricao;
		this.dataAgendamento = dataAgenda;
		this.tipoChamado= tc;
		this.tipoFalha = tf;
		this.pj = pj;
		this.status = status;
		this.usuario= usuarioLogado;
	}

	
	public void atualizaPrioridade(){
		this.prioridade = new Prioridade(getTipoChamado().getValor(), getPj().getPorte().getValor(), getDataAbertura());
	}
	
	public int getCodigo() {
		return codigo;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Date getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public Date getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(Date dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public StatusChamado getStatus() {
		return status;
	}

	public void setStatus(StatusChamado status) {
		this.status = status;
	}

	public TipoChamado getTipoChamado() {
		return tipoChamado;
	}

	public void setTipoChamado(TipoChamado tipoChamado) {
		this.tipoChamado = tipoChamado;
	}

	public TipoFalha getTipoFalha() {
		return tipoFalha;
	}

	public void setTipoFalha(TipoFalha tipoFalha) {
		this.tipoFalha = tipoFalha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public PessoaJuridica getPj() {
		return pj;
	}

	public void setPj(PessoaJuridica pj) {
		this.pj = pj;
	}
	
	public void pritChamado(){
		System.out.println("\n*********************************");
		System.out.println("\t codigo: " + codigo);
		System.out.println("\t data abertura: " + dataAbertura);
		System.out.println("\t data fechamento: " + dataFechamento);
		System.out.println("\t detalhes: " + detalhes);
		System.out.println("\t responsavel: " + responsavel);
		System.out.println("\t contato: " + contato);
		System.out.println("\t data agendamento: " + dataAgendamento);
		System.out.println("\t status: " + status.getNome());
		System.out.println("\t tipo chamado: " + tipoChamado);
		System.out.println("\t tipo falha: " + tipoFalha);
		System.out.println("\t usuario: " + usuario.getNome());
		System.out.println("\t pj: " + pj.getNomeFantasia());		
		System.out.println("*********************************\n");
	}
	
}
