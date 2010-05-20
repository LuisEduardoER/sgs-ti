package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
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

import common.entity.Chamado;
import common.entity.Cliente;
import common.entity.PessoaFisica;
import common.entity.PessoaJuridica;
import common.entity.Porte;
import common.entity.Usuario;
import common.util.Utils;

import client.controller.ClientController;
import client.model.internalContent.CriarChamados;
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
	private JTextArea areaTEMP;
	private JPanel form;
	private ListenerPesquisar lp;
	private JXTable tabela;
	private String nome;
	private List<PessoaJuridica> lista;
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ModalCliente() {
		setTitle("Pesquisar Cliente");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setSize(600, 460);
		setResizable(false);
		setLocation(ViewUtils.getScreenCenter(this));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				String cliente = campoPesquisa.getText();
				if(Utils.isNullOrEmpty(cliente)){
					
				}
				if(JOptionPane.showConfirmDialog(null,"Nenhum cliente foi selecionado, deseja cancelar a busca?","ATENÇÃO ",javax.swing.JOptionPane.YES_NO_OPTION)==0){
					if(!(tabela.getSelectedRowCount()==0))
					{
						int linha = tabela.getSelectedRow();
						nome = tabela.getValueAt(linha, 0).toString();
					}else
						nome = "";
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
		Cliente cliente = new PessoaFisica("Rua x", new Porte(Porte.PF), null, "PUC-PR", "Masculino",
				new Date(), new Long("0123456789"));
		
		List<Cliente> listaCli = new ArrayList<Cliente>();
		listaCli.add(cliente);
		
		JXTableModel tm = new JXTableModel(converterListEmMatriz(listaCli),header);
		tabela = new JXTable(tm);
		JScrollPane scroll = new JScrollPane(tabela);
		scroll.setSize(270,150);
		btPesquisar = new JButton("Buscar");
		btPesquisar.addActionListener(lp);
		btPesquisar.setActionCommand("PESQUISAR");
		
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
		form.add(new JLabel(""));
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

	public String getCliente(){
		// TODO: Denis - Pegar um cliente qualquer do banco
		return nome;
	};
	
	class ListenerPesquisar implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent evt) {
			if (evt.getActionCommand().equals("Pesquisar")) {
				System.out.println("OIIOOIOIO");
				String desc = campoPesquisa.getText();
				if(!Utils.isNullOrEmpty(desc))
					try {
						lista = ClientController.getInstance().pesquisarPJ(desc);
					} catch (RemoteException e) {
						MainView.getInstance().mostrarMensagemErroRemoto();
					}
			}

		}
	}
	
	public String[][] converterListEmMatriz(List<Cliente> clientes){
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
}
