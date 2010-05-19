package common.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import common.entity.Usuario;

public interface ObserverUsuario extends Remote{
	
	public void notificarTempoExcedido() throws RemoteException;
	
	public void encerrarClient() throws RemoteException;
	
	public boolean autenticar(Usuario usuario) throws RemoteException;
	
	public void atualizarCliente() throws RemoteException;
	
	public void encerrarSessao() throws RemoteException;
	
	public void mostrarMensagem(String mensagem) throws RemoteException;
	
	public boolean checkServerStatus() throws RemoteException;

}
