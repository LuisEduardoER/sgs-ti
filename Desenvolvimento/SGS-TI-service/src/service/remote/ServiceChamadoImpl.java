package service.remote;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import service.base.FilaChamado;
import common.entity.Chamado;
import common.remote.ObservadorFila;
import common.remote.ServiceChamado;

public class ServiceChamadoImpl implements ServiceChamado {

	List<ObservadorFila> observadoresFila;
	FilaChamado filaChamados;
	
	/**
	 * Construtor
	 */
	public ServiceChamadoImpl(){
		observadoresFila = new ArrayList<ObservadorFila>();
	}
	
	@Override
	public void adicionarObservadorFila(ObservadorFila obs)
			throws RemoteException {

		observadoresFila.add(obs);
	}
	
	@Override
	public void removerObservadorFila(ObservadorFila obs)
			throws RemoteException {
		
		observadoresFila.remove(obs);
	}
	
	@Override
	public void notificarObservadorFila() throws RemoteException {
		for(ObservadorFila obs : observadoresFila){
			obs.atualizarFila(FilaChamado.getInstance().getFila());
		}
	}

	@Override
	public void cadastrarChamado(Chamado chamado) throws RemoteException {

		// TODO: adicionar no banco.
		
		// adicionar o chamado na fila e notificar.
		FilaChamado.getInstance().adicionaChamado(chamado);
		notificarObservadorFila();
	}

	@Override
	public void atualizarChamado(Chamado chamado) throws RemoteException {
		// TODO: atualizar no banco.
		
		// atualiza o chamado na fila e notificar.
		FilaChamado.getInstance().atualizarChamado(chamado);
		notificarObservadorFila();
		
	}
	
	/*
	 * GETTERs AND SETTERs
	 */
	public FilaChamado getFilaChamados() {
		return filaChamados;
	}
	public void setFilaChamados(FilaChamado filaChamados) {
		this.filaChamados = filaChamados;
	}
}
