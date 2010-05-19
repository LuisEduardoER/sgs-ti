package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import common.util.SystemConstant;
import common.util.Utils;

import client.controller.ClientController;
import client.util.SpringUtilities;
import client.util.ViewUtils;

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
	
	public ModalCliente() {
		setTitle("Pesquisar Cliente");
		setLayout(new SpringLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(325, 300);
		setResizable(false);
		setLocation(ViewUtils.getScreenCenter(this));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				String cliente = campoPesquisa.getText();
				if(Utils.isNullOrEmpty(cliente)){
					
				}
				if(JOptionPane.showConfirmDialog(null,"Nenhum cliente foi selecionado, deseja cancelar a busca?","ATENÇÃO ",javax.swing.JOptionPane.YES_NO_OPTION)==0){
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
		
		areaTEMP = new JTextArea();
		areaTEMP.setRows(10);
		JScrollPane scroll = new JScrollPane(areaTEMP);
		
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
		
		form.add(new JLabel(""));
		form.add(scroll);
		form.add(new JLabel(""));
		
		SpringUtilities.makeCompactGrid(form,
				3, 3,		//rows, cols
				6, 6,     	//initX, initY
				6, 6);      //xPad, yPad
		
		add(form);
		
	}

	public String getCliente(){
		// TODO: Denis - Pegar um cliente qualquer do banco
		String cliente = campoPesquisa.getText();
		return cliente;
	};
	
	class ListenerPesquisar implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent evt) {
			if (evt.getActionCommand().equals("Pesquisar")) {
				System.out.println("TESTE PESQ MODAL");
			}

		}
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
}
