package client.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import common.entity.Chamado;
import common.exception.BusinessException;
import common.remote.ObservadorAgendamento;
import common.remote.ServiceChamado;
import common.util.Utils;

public class ObservadorFilaImplAgendamento extends Observable implements ObservadorAgendamento{
	private ServiceChamado serviceChamado;
	private ObservadorAgendamento myStub;
	
	public ObservadorFilaImplAgendamento(Observer obs) throws BusinessException{
		try {
			addObserver(obs);
			serviceChamado = Utils.obterServiceChamado();

			// Criar um stub
			myStub = (ObservadorAgendamento) UnicastRemoteObject
					.exportObject(this, 0);

			serviceChamado.adicionarObservadorAgendamento(myStub);

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
		serviceChamado.removerObservadorAgendamento(myStub);
	}

	@Override
	public void addObserverNotificacoesAgendamento(Observer obs)
			throws RemoteException {
		this.addObserver(obs);
	}

	@Override
	public void removeObserverNotificacoesAgendamento(Observer obs)
			throws RemoteException {
		this.deleteObserver(obs);
	}
}
