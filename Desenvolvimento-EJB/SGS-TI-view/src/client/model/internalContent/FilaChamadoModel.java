package client.model.internalContent;

import javax.swing.table.AbstractTableModel;


public class FilaChamadoModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String [] colunas;
	private String[][] linhas;
	
	/**
	 * Construtor
	 * @param colunas
	 * @param linhas
	 */
	public FilaChamadoModel(String[][] linhas, String [] colunas){
		setLinhas(linhas);
		setColunas(colunas);
	}
	
	@Override
	public int getColumnCount() {
		return getColunas().length;
	}

	@Override
	public int getRowCount() {
		return getLinhas().length;
	}

	@Override
	public String getColumnName(int column) {
		return getColunas()[column];
	}
	
	@Override
	public Object getValueAt(int indexLinha, int indexColuna) {
		return getLinhas()[indexLinha][indexColuna];
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
	public String[][] getLinhas() {
		return linhas;
	}
	public void setLinhas(String[][] linhas) {
		this.linhas = linhas;
	}
}
