package common.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceUsuario extends Remote{
	
	public void autenticar() throws RemoteException;

}
