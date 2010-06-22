/*package service.remote;

import java.awt.BorderLayout;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import common.remote.ServiceChamado;
import common.remote.ServiceChamadoItens;
import common.remote.ServiceUsuario;

public class ServidorChamados {
	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 1L;

	*//**
	 * @param args
	 *//*
	public static void main(String[] args) {

		try {
			
			// TODO : MELHORAR ESTA MERDA
			// Criar o objeto
			ServiceChamadoImpl servico = new ServiceChamadoImpl();

			// Criar Stub
			ServiceChamado stub = (ServiceChamado) UnicastRemoteObject
					.exportObject(servico, 0);

			// Localiza o Registry
			Registry registry = LocateRegistry.getRegistry();

			// Registra o objeto
			registry.rebind("serviceChamado", stub);
			
			ServiceUsuarioImpl servicoUsuario = new ServiceUsuarioImpl();
			ServiceUsuario stubUser = (ServiceUsuario) UnicastRemoteObject.exportObject(servicoUsuario,0);
			registry.rebind("serviceUsuario", stubUser);
			
			// Criar o objeto
			ServiceChamadoProperts servicoChamadoI = new ServiceChamadoProperts();
			// Criar Stub
			ServiceChamadoItens stubChamado = (ServiceChamadoItens) UnicastRemoteObject.exportObject(servicoChamadoI, 0);
			// Registra o objeto
			registry.rebind("serviceChamadoItens", stubChamado);
	
			inicializarTela();
			
			
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null,
						"Servidor não iniciado: " +
						e.getMessage());
			e.printStackTrace();
		}

	}

	private static void inicializarTela() {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout(1,1)); 
		frame.setSize(200,75);
		frame.setResizable(false); 
		frame.setVisible(true);
		frame.setTitle("Servidor da aplicação.");
		JLabel lblUsuario = new JLabel("Servidor Usuario: ok");
		JLabel lblChamado = new JLabel("Servidor Chamados: ok");
		
		frame.add(lblUsuario,BorderLayout.NORTH);
		frame.add(lblChamado,BorderLayout.CENTER);
	}
}
*/