package service.remote;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JOptionPane;

import common.remote.ServiceChamado;
import common.remote.ServiceUsuario;

public class ServidorChamados {
	/**
	 * @param args
	 */
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
			
			System.out.println("Servidor de chamados ativo.");
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null,
						"Servidor não iniciado: " +
						e.getMessage());
			e.printStackTrace();
		}

	}
}
