package common.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import common.entity.UsuarioAutenticado;

public interface ServiceUsuario extends Remote{
	
	public boolean autenticar(ObserverUsuario observador) throws RemoteException;
	
	public void atualizarClient(ObserverUsuario observador) throws RemoteException;
	
	public void adicionarObservador(ObserverUsuario observador) throws RemoteException;
	
	public void removerObservador(ObserverUsuario observador) throws RemoteException;
	
	public void notificarTempoExcedido(UsuarioAutenticado usuarioAutenticado) throws RemoteException;

}
