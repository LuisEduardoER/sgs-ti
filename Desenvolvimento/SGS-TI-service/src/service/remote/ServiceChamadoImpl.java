package service.remote;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import persistencia.facade.FacadeChamado;
import persistencia.facade.FacadeHistoricoChamado;
import service.base.FilaChamado;
import common.entity.Chamado;
import common.entity.HistoricoChamado;
import common.exception.BusinessException;
import common.remote.ObservadorAgendamento;
import common.remote.ObservadorFila;
import common.remote.ServiceChamado;
import common.util.Utils;

public class ServiceChamadoImpl implements ServiceChamado
{
	List<ObservadorFila> observadoresFila;
	List<ObservadorAgendamento> observadoresAgendamento;
	FilaChamado filaChamados;
	
	/**
	 * Construtor
	 */
	public ServiceChamadoImpl(){
		observadoresFila = new ArrayList<ObservadorFila>();
		observadoresAgendamento = new ArrayList<ObservadorAgendamento>();
	}
	
	@Override
	public void adicionarObservadorFila(ObservadorFila obs)
			throws RemoteException, BusinessException
	{

		observadoresFila.add(obs);
		obs.atualizarFila(FilaChamado.getInstance().getFila());
	}
	
	@Override
	public void removerObservadorFila(ObservadorFila obs)
			throws RemoteException {
		if(observadoresFila.contains(obs))
			observadoresFila.remove(obs);
	}
	
	@Override
	public void notificarObservadorFila() throws BusinessException {
		for(int i=0; i<observadoresFila.size();i++){
			ObservadorFila obs = observadoresFila.get(i);
			try{
				obs.atualizarFila(FilaChamado.getInstance().getFila());
				
			}catch(RemoteException e){
				// se o observador fila está indisponivel, remove
				observadoresFila.remove(i);
				i--;
			}	
		}
	}

	@Override
	public void adicionarObservadorAgendamento(ObservadorAgendamento obs)
			throws RemoteException, BusinessException {
		observadoresAgendamento.add(obs);	
		obs.atualizarFila(FilaChamado.getInstance().getFilaAgendamento());
	}
	
	@Override
	public void notificarObservadorAgendamento() throws RemoteException, BusinessException {
		for(int i = 0;  i <observadoresAgendamento.size(); i++)
		{
			ObservadorAgendamento obs = observadoresAgendamento.get(i);
			try{
				obs.atualizarFila(FilaChamado.getInstance().getFilaAgendamento());
				
			}catch(RemoteException e){
				// se o observador fila está indisponivel, remove
				observadoresAgendamento.remove(i);
				i--;
			}	
		}	
	}

	@Override
	public void removerObservadorAgendamento(ObservadorAgendamento obs)
			throws RemoteException {
		observadoresAgendamento.remove(obs);
		
	}
	
	@Override
	public void cadastrarChamado(Chamado chamado) throws RemoteException, BusinessException {

		// TODO: adicionar no banco.
		
		boolean inseriu = FacadeChamado.criarChamado(chamado);
		FilaChamado.getInstance().atualizarChamados();
		Utils.printMsg(this.getClass().getName(), "Cadastrando chamado: " + inseriu);

		if(inseriu){
			// adicionar o chamado na fila e notificar.
			//FilaChamado.getInstance().adicionaChamado(chamado);
			notificarObservadorFila();
		}
	}

	@Override
	public void atualizarChamado(Chamado chamado) throws RemoteException, BusinessException {
		// TODO - Esperar Denis fazer cadastrar se não dá pau
		HistoricoChamado historicoChamado = FacadeChamado.buscarChamado(chamado);
		boolean criou = FacadeHistoricoChamado.criarHistoricoChamado(historicoChamado);
		if(criou)
		{
			boolean salvou = FacadeChamado.atualizarChamado(chamado);
			if(salvou)
			{
				//TODO - Tentar colocar constraint
				if(chamado.getStatus().getNome().toUpperCase().equals("AGENDADO"))
				{
					// TODO - Notificar ListarAgenda
					FilaChamado.getInstance().adicionaAgendamento(chamado);
					notificarObservadorAgendamento();
				}
			}
			
		}
		
		
		// atualiza o chamado na fila e notificar.
		FilaChamado.getInstance().atualizarChamado(chamado);
		notificarObservadorFila();
		
	}
	
	@Override
	public void verificarStatus(ObservadorFila obs) throws RemoteException, BusinessException{
		if(!observadoresFila.contains(obs)){
			observadoresFila.add(obs);
			obs.atualizarFila(FilaChamado.getInstance().getFila());
		}
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
