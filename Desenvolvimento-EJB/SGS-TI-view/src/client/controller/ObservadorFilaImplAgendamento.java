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
import common.util.SystemConstant;
import common.util.Utils;

public class ObservadorFilaImplAgendamento extends Observable implements ObservadorAgendamento{
	private ServiceChamado serviceChamado;
	private ObservadorAgendamento myStub;
	private boolean atualizou;
	
	public ObservadorFilaImplAgendamento(Observer obs) throws BusinessException{
		try {
			addObserver(obs);
			serviceChamado = Utils.obterServiceChamado();

			// Criar um stub
			myStub = (ObservadorAgendamento) UnicastRemoteObject
					.exportObject(this, 0);

			serviceChamado.adicionarObservadorAgendamento(myStub);

			/*
			 * Thread que fica monitorando o serviço de chamados
			 * caso ocorra algum problema tenta re-estabelecer o
			 * serviço.
			 */
			new Thread(){
				public void run() {

					while(!Thread.interrupted()){
						try {
							if(!atualizou){
								serviceChamado.verificarStatusAgendamento(myStub);
							}

							Utils.printMsg(this.getClass().getName(), "Verificando status do ServiceChamado. Status: online");
							
							atualizou = false;
							Thread.sleep(SystemConstant.TEMPO_ESPERA_MONITOR_CONEXAO);
						
						} catch (InterruptedException e) {
							Utils.printMsg(this.getClass().getName(), "Thread do ObservadorFila finalizando.");
						} catch (BusinessException e) {
							Utils.printErro(this.getClass().getName(), e);
						} catch (Exception e) {
							// ops algum problema com o service
							// tenta novamente
						}	
					}
					
				};
			}.start();
			
			
		} catch (Exception e) {
			throw new BusinessException(SystemConstant.MSG_AM_SEM_CONEXAO_REMOTA);
		}
	}
	
	@Override
	public void atualizarFila(List<Chamado> chamados) throws RemoteException {
		setChanged();
		notifyObservers(chamados);
	}

	@Override
	public void removerObservador() throws RemoteException, BusinessException{	
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
