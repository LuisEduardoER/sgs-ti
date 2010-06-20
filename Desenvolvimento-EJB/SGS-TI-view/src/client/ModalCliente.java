package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.jdesktop.swingx.JXTable;

import common.entity.Cliente;
import common.entity.PessoaFisica;
import common.entity.PessoaJuridica;
import common.entity.Porte;
import common.entity.Usuario;
import common.util.Utils;

import client.controller.ClientController;
import client.model.internalContent.JXTableModel;
import client.util.SpringUtilities;
import client.util.ViewUtils;
import client.view.MainView;

public class ModalCliente extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField campoPesquisa;
	private JButton btPesquisar;
	private JButton btOk;
	private JTextArea areaTEMP;
	private JPanel form;
	private ListenerPesquisar lp;
	private JXTable tabela;
	private String nome;
	private List<PessoaJuridica> lista;
	private JXTableModel tm;
	private PessoaJuridica pessoaJuridica;

	private boolean cancelado;
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ModalCliente() {
		cancelado = false;
		setTitle("Pesquisar Cliente");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setSize(600, 470);
		setResizable(false);
		setLocation(ViewUtils.getScreenCenter(this));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				String cliente = campoPesquisa.getText();
				if(Utils.isNullOrEmpty(cliente)){
					
				}
				if(JOptionPane.showConfirmDialog(null,"Nenhum cliente foi selecionado, deseja cancelar a busca?","ATENÇÃO ",javax.swing.JOptionPane.YES_NO_OPTION)==0){
					nome="";
					cancelado = true;
					fecharModal();
				}
			}
		});
		montarModal();
	}
	
	public void montarModal(){
		
		JLabel lblDescricao = new JLabel("Descrição: ");
		JLabel lblResultado = new JLabel("Resultado: ");
		
		campoPesquisa = new JTextField();
		campoPesquisa.setColumns(15);
		
		String[] header = {"Nome","Porte","Endereço"};
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(new Usuario("Marcio Fuckner","wokdow"));
		//Cliente novo = new Cliente("Rua da cidade de Curitiba", porte,usuarios,"");
		Cliente cliente = new PessoaFisica("", new Porte(""), null, "", "",
				new Date(), new Long("0123456789"));
		
		List<Cliente> listaCli = new ArrayList<Cliente>();
		listaCli.add(cliente);
		
		tm = new JXTableModel(converterListEmMatriz(new ArrayList<PessoaJuridica>()),header);
		tabela = new JXTable(tm);
		JScrollPane scroll = new JScrollPane(tabela);
		scroll.setSize(270,150);
		lp = new ListenerPesquisar();
		btPesquisar = new JButton("Buscar");
		btPesquisar.addActionListener(lp);
		btPesquisar.setActionCommand("PESQUISAR");
		
		btOk = new JButton("Enviar");
		btOk.addActionListener(lp);
		btOk.setActionCommand("ENVIAR");
		
		
		form = new JPanel();
		form.setLayout(new SpringLayout());
		form.setSize(300,300);
		form.setOpaque(true);
		// des, text, bt
		// resultado, lb, lb
		// textArea, lb, lb
		
		form.add(lblDescricao);
		form.add(campoPesquisa);
		form.add(btPesquisar);
		
		form.add(lblResultado);
		form.add(btOk);
		form.add(new JLabel(""));
		
		
		

		SpringUtilities.makeCompactGrid(form,
				2, 3,		//rows, cols
				6, 6,     	//initX, initY
				6, 6);      //xPad, yPad

		JPanel painelTabela = new JPanel();
		painelTabela.setLayout(new BorderLayout());
		painelTabela.add(scroll);
		add(form,BorderLayout.NORTH);
		add(painelTabela,BorderLayout.SOUTH);
	}

	public PessoaJuridica getCliente(){
		// TODO: Denis - Pegar um cliente qualquer do banco
		return pessoaJuridica;
	};
	
	class ListenerPesquisar implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent evt) {
			if (evt.getActionCommand().equals("PESQUISAR")) {
				String desc = campoPesquisa.getText();
				if(!Utils.isNullOrEmpty(desc))
					try {
						List<PessoaJuridica> pjs = ClientController.getInstance().pesquisarPJ(desc);
						if(!Utils.isEmptyCollection(pjs)){
							lista = pjs;
						}else
							lista = new ArrayList<PessoaJuridica>();
						atualizarTabela();
					} catch (RemoteException e) {
						MainView.getInstance().mostrarMensagemErroRemoto();
					}
			}else if (evt.getActionCommand().equals("ENVIAR")) {
				int linha = tabela.getSelectedRow();
				if(linha>-1){
					nome = tabela.getValueAt(linha, 0).toString();
					for(PessoaJuridica pj : lista){
						if(pj.getNome().equals(nome)){
							pessoaJuridica = pj;
						}
					}
					fecharModal();
				}
				if(Utils.isNullOrEmpty(nome)){
					nome = "";
					if(JOptionPane.showConfirmDialog(null,"Nenhum cliente foi selecionado, deseja cancelar a busca?","ATENÇÃO ",javax.swing.JOptionPane.YES_NO_OPTION)==0){
						cancelado = true;
						fecharModal();
					}
				}
				
			}

		}
	}
	
	public String[][] converterListEmMatriz(List<PessoaJuridica> clientes){
		String [][]matriz = new String [clientes.size()][3];
		// nome, porte e endereco
		for(int linha=0; linha<clientes.size(); linha++){
			Cliente cliente = clientes.get(linha);
			matriz[linha][0] = cliente.getNome();
			matriz[linha][1] = cliente.getPorte().getNome();
			matriz[linha][2] = cliente.getEndereco();
		}

		return matriz;
	}
	
	public void atualizarTabela(){
		if(!Utils.isEmptyCollection(lista)){
			tm.setLinhas(converterListEmMatriz(lista));
		}else
		{
			String [][] matriz = new String[0][0];
			tm.setLinhas(matriz);
		}
		
		tm.fireTableDataChanged();
		
		Utils.printMsg(this.getClass().getName(), new Date() + " - Lista Cli atualizada. Size: " + lista.size());
	}
	
	public void fecharModal(){
		
		this.dispose();
	}
	
	public JTextField getCampoPesquisa() {
		return campoPesquisa;
	}

	public void setCampoPesquisa(JTextField campoPesquisa) {
		this.campoPesquisa = campoPesquisa;
	}

	public JButton getBtPesquisar() {
		return btPesquisar;
	}

	public void setBtPesquisar(JButton btPesquisar) {
		this.btPesquisar = btPesquisar;
	}

	public JTextArea getAreaTEMP() {
		return areaTEMP;
	}

	public void setAreaTEMP(JTextArea areaTEMP) {
		this.areaTEMP = areaTEMP;
	}
	
	public JXTable getTabela() {
		return tabela;
	}

	public void setTabela(JXTable tabela) {
		this.tabela = tabela;
	}

	public boolean isCancelado() {
		return cancelado;
	}

	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}

	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}
	
	
}
