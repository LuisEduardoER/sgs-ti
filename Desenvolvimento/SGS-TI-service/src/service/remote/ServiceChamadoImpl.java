package service.remote;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import persistencia.facade.FacadeChamado;
import service.base.FilaChamado;
import common.entity.Chamado;
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
			throws RemoteException {

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
	public void notificarObservadorFila() {
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
			throws RemoteException {
		observadoresAgendamento.add(obs);	
		obs.atualizarFila(FilaChamado.getInstance().getFilaAgendamento());
	}
	
	@Override
	public void notificarObservadorAgendamento() throws RemoteException {
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
	public void cadastrarChamado(Chamado chamado) throws RemoteException {

		// TODO: adicionar no banco.
		Utils.printMsg(this.getClass().getName(), "Cadastrando chamado");
		boolean inseriu = FacadeChamado.criarChamado(chamado);
		System.out.println(inseriu);
		// adicionar o chamado na fila e notificar.
		FilaChamado.getInstance().adicionaChamado(chamado);
		notificarObservadorFila();
	}

	@Override
	public void atualizarChamado(Chamado chamado) throws RemoteException {
		// TODO - Esperar Denis fazer cadastrar se não dá pau
		/*HistoricoChamado historicoChamado = FacadeChamado.buscarChamado(chamado);
		boolean criou = FacadeHistoricoChamado.criarHistoricoChamado(historicoChamado);
		if(criou)
		{
			boolean salvou = FacadeChamado.atualizarChamado(chamado);
			if(salvou)
			{*/	
				//TODO - Tentar colocar constraint
				if(chamado.getStatus().getNome().toUpperCase().equals("AGENDADO"))
				{
					// TODO - Notificar ListarAgenda
					FilaChamado.getInstance().adicionaAgendamento(chamado);
					notificarObservadorAgendamento();
				}
			/*}
		}*/
		
		
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
