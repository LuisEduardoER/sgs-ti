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
import common.util.SystemConstant;
import common.util.Utils;

public class ObservadorFilaImpl extends Observable implements ObservadorFila{
	private ServiceChamado serviceChamado;
	private ObservadorFila myStub; 
	private boolean atualizou;
	
	public ObservadorFilaImpl(Observer obs) throws BusinessException{
		try {
			addObserver(obs);
			serviceChamado = Utils.obterServiceChamado();

			// Criar um stub
			myStub = (ObservadorFila) UnicastRemoteObject
					.exportObject(this, 0);

			serviceChamado.adicionarObservadorFila(myStub);
			atualizou = true;

			new Thread(){
				public void run() {
					int contadorTentativas = 0;
					try {
						while(!Thread.interrupted()){
							if(!atualizou){
								serviceChamado.verificarStatus(myStub);
							}
							
							Utils.printMsg(this.getClass().getName(), "Verificando status do ServiceChamado. Status: online");
							atualizou = false;
							contadorTentativas = 0;
							Thread.sleep(SystemConstant.TEMPO_ESPERA_MONITOR_CONEXAO);
						}
					} catch (RemoteException e) {
						// ops algum problema com o service
						// tenta novamente
						Utils.printMsg(this.getClass().getName(), "Verificando status do ServiceChamado. Status: offline");
						try {
							if(contadorTentativas <= SystemConstant.MAX_TENTATIVAS_RECONEXAO){
								Utils.printMsg(this.getClass().getName(), "Tentativa #" +contadorTentativas+ " de reconexão.");
								contadorTentativas++;
								serviceChamado.verificarStatus(myStub);
							}else{
								// mandar msg pro user
							}
						} catch (RemoteException e1) {
							// não faz nada
						}
					} catch (InterruptedException e) {
						Utils.printMsg(this.getClass().getName(), "Thread do ObservadorFila finalizando.");
					}
				};
			}.start();
			
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
	
	public void addObserverNotificacoesFila(Observer obs){
		this.addObserver(obs);
	}
	public void removeObserverNotificacoesFila(Observer obs){
		this.deleteObserver(obs);
	}
}
