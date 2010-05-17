package client.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import common.entity.Chamado;
import common.exception.BusinessException;
import common.remote.ObservadorFila;
import common.remote.ServiceChamado;
import common.util.Utils;

public class ObservadorFilaImpl extends Observable implements ObservadorFila{
	private ServiceChamado serviceChamado;
	private ObservadorFila myStub;
	
	public ObservadorFilaImpl(Observer obs) throws BusinessException{
		try {
			addObserver(obs);
			serviceChamado = Utils.obterServiceChamado();

			// Criar um stub
			myStub = (ObservadorFila) UnicastRemoteObject
					.exportObject(this, 0);

			serviceChamado.adicionarObservadorFila(myStub);

		} catch (RemoteException e) {
			// TODO: criar exception de infra.
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void atualizarFila(List<Chamado> chamados) throws RemoteException {
		setChanged();
		notifyObservers(chamados);
	}

	@Override
	public void removerObservador() throws RemoteException {	
		serviceChamado.removerObservadorFila(myStub);
	}

}
