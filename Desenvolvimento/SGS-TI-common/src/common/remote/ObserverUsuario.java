package common.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObserverUsuario extends Remote{
	
	public void update(String evento) throws RemoteException;
	
	public void suicide() throws RemoteException;

}
