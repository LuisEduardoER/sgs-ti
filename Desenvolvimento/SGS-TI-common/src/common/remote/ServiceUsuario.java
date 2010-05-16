package common.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceUsuario extends Remote{
	
	public boolean autenticar(ObserverUsuario observador) throws RemoteException;
	
	public void adicionarObservador(ObserverUsuario observador) throws RemoteException;
	
	public void removerObservador(ObserverUsuario observador) throws RemoteException;
	
	public void notificarClient() throws RemoteException;

}
