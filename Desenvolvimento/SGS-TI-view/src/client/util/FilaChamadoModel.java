package client.util;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import common.entity.Chamado;

public class FilaChamadoModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String [] colunas = new String[]{"Codigo", "Cliente", "Prioridade", "Data Abertura", "Status"};
	private List<Chamado> linhas;
	
	/**
	 * Construtor
	 * @param colunas
	 * @param linhas
	 */
	public FilaChamadoModel(List<Chamado> linhas){
		setLinhas(linhas);		
	}
	
	@Override
	public int getColumnCount() {
		return getColunas().length;
	}

	@Override
	public int getRowCount() {
		return getLinhas().size();
	}

	@Override
	public Object getValueAt(int indexLinha, int indexColuna) {
		Chamado chamado = getLinhas().get(indexLinha);
		
		//"Codigo", "Cliente", "Prioridade", "Data Abertura", "Status"
		switch(indexColuna){
			case 0:
				return chamado.getNumeroChamado();
			case 1:
				return chamado.getReclamante().getNome();
			case 2:
				return chamado.getPrioridade().getValorPrioridade();
			case 3:
				return chamado.getDataHoraAbertura();
			case 4:
				return chamado.getStatus().getNome();
			
			default:
				return null;
		}
	}

	/*
	 * GETTERs AND SETTERs
	 */
	public String[] getColunas() {
		return colunas;
	}
	public void setColunas(String[] colunas) {
		this.colunas = colunas;
	}
	public List<Chamado> getLinhas() {
		return linhas;
	}
	public void setLinhas(List<Chamado> linhas) {
		this.linhas = linhas;
	}
}
