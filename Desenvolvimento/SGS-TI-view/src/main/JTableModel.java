
package main;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.table.AbstractTableModel;

/**


 * Classe Respons�vel por definir o modelo da JTable
 * Foi utilizado o tipo ArrayList para o atributo linhas
 * pois n�o � definido qual tamanho total de linhas podendo
 * ser alterada em tempo de execu��o(remover ou incluir).
 * Obs.: Este c�digo foi implementado juntamente com os coment�rios
 * do site www.guj.com.br pelo autor Bruno Rios Lima
 * @author Marcelo Alves
 */

public class JTableModel extends AbstractTableModel {

	private ArrayList linhas;
	private String[] colunas;

	public JTableModel(ArrayList dados,String[] colunas){
		setLinhas(dados);
		setColunas(colunas);

	}

	//Retorna o numero de colunas
	public int getColumnCount(){
		return getColunas().length;

	}

	//Retorna o numero de linhas
	public int getRowCount(){
		return getLinhas().size();
	}

	//Obtem o valor da linhas e coluna
	public Object getValueAt(int rowIndex, int columnIndex){

		//Recebe a linha, passando por cast para String[]
		String[] linha = (String[])getLinhas().get(rowIndex);

		//Retorna o objeto na posi��o de columnIndex
		return linha[columnIndex];

	}

	//Seta valor da linha e coluna
	public void setValueAt(Object value,int rowIndex, int columnIndex){
		String[] linha = (String[])getLinhas().get(rowIndex);
		//Altera o conte�do da linha na posi��o do indice columnIndex

		linha[columnIndex] = (String)value;
		//Dispara o evento da celula alterada
		fireTableCellUpdated(rowIndex, columnIndex);

	}

	//Adiciona nova linha
	public void addRow(String[] dadosLinha){
		getLinhas().add(dadosLinha);
		//Informa ao JTable que ouve linhas incluidas no modelo
		//Como os dados s�o adicionados no final pega o tamanho total das linhas
		//menos 1 para obter a linha incluida.
		int linha = getLinhas().size() -1;
		fireTableRowsInserted(linha,linha);

	}

	//Remove linha
	public boolean removeRow(String val, int col){

		//obtem o iterator
		Iterator i = getLinhas().iterator();

		//Guarda as linhas percorridas
		int linha = 0;

		//Faz um loop enquando obtiver dados
		while(i.hasNext()){
			//Obtem as colunas da linha atual
			String[] linhaCorrente = (String[])i.next();
			linha++;
			//Compara o valor da linha atual na coluna e valor
			//informado por parametro
			if(linhaCorrente[col].equals(val)){
				getLinhas().remove(linha);
				//informa a JTable que houve dados deletados
				//passando a linha removida
				fireTableRowsDeleted(linha,linha);
				return true;

			}

		}

		//Caso n�o encontre o valor
		return false;
	}

	//Retorna o nome da coluna
	//getColumnName[col] retorna na posi��o do indice!
	public String getColumnName(int col){
		return getColunas()[col];
	}

	public ArrayList getLinhas(){
		return linhas;
	}

	public String[] getColunas(){
		return colunas;
	}

	public void setLinhas(ArrayList dados){
		linhas = dados;
	}

	public void setColunas(String[] dados){
		colunas = dados;
	}

}