package cliente.task;

import java.rmi.RemoteException;
import common.util.SystemConstant;
import client.controller.ClientController;
import client.view.MainView;

public class ThreadCheckServer extends Thread {
	
	
	@Override
	public void run() {

		while(true){
			try {
				try {
					System.out.println("Verificando status sistema...");
					if(ClientController.getInstance().checkServerStatus())
						MainView.getInstance().alterarStatusSistema(true);
				} catch (RemoteException e) {
					System.out.println("alterar para falso");
					MainView.getInstance().alterarStatusSistema(false);
					boolean restart = false;
					while(!restart){
						try {
							restart = ClientController.getInstance().reinicializarServidorRemoto();
							
						} catch (RemoteException e1) {
							// ignora exception e tenta novamente
						}
						sleep(SystemConstant.SERVER_CHECK_RECONECT_TIME);
					}
				}
				sleep(SystemConstant.SERVER_CHECK_STATUS_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
