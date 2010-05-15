package common.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import common.entity.Chamado;

public interface ObservadorFila extends Remote{
	
	public void atualizarFila(List<Chamado> chamados) throws RemoteException;
	public void removerObservador() throws RemoteException;
}
