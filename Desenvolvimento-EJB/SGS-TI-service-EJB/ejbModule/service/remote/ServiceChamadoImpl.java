package service.remote;

import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.Stateless;
import org.jboss.ejb3.annotation.RemoteBinding;
import persistencia.facade.FacadeChamado;
import persistencia.facade.FacadeHistoricoChamado;
import service.base.FilaChamado;
import service.base.RepositorioObsAgendamento;
import service.base.RepositorioObsChamado;
import common.entity.Chamado;
import common.entity.HistoricoChamado;
import common.exception.BusinessException;
import common.remote.ObservadorAgendamento;
import common.remote.ObservadorFila;
import common.remote.ServiceChamado;
import common.util.SystemConstant;
import common.util.Utils;

@Stateless
@RemoteBinding(jndiBinding = "serviceChamado")
public class ServiceChamadoImpl implements ServiceChamado
{
	/**
	 * Construtor
	 */	
	public ServiceChamadoImpl(){}
	
	@Override
	public void adicionarObservadorFila(ObservadorFila obs) throws BusinessException
	{
		try {
			RepositorioObsChamado.getInstance().addObserver(obs);
			obs.atualizarFila(FilaChamado.getInstance().getFila());
		} catch (RemoteException e) {
			Utils.printErro(ServiceChamadoImpl.class.getName(), e);
			throw new BusinessException(SystemConstant.MSG_ERRO_CALLBACK);
		}
	}
	
	@Override
	public void removerObservadorFila(ObservadorFila obs){
		RepositorioObsChamado.getInstance().removeObserver(obs);
	}
	
	@Override
	public void notificarObservadorFila() throws BusinessException {
		List<ObservadorFila> listaObsFila = RepositorioObsChamado.getInstance().getObservers();
		for(int i=0; i<listaObsFila.size();i++){
			ObservadorFila obs = listaObsFila.get(i);
			try{
				if(obs!= null)
					obs.atualizarFila(FilaChamado.getInstance().getFila());
				
			}catch(RemoteException e){
				// se o observador fila está indisponivel, remove
				RepositorioObsChamado.getInstance().removeObserver(listaObsFila.get(i));
				i--;
			}	
		}
	}

	@Override
	public void adicionarObservadorAgendamento(ObservadorAgendamento obs) throws BusinessException {
		try{
			RepositorioObsAgendamento.getInstance().addObserver(obs);
			obs.atualizarFila(FilaChamado.getInstance().getFilaAgendamento());
			
		} catch (RemoteException e) {
			Utils.printErro(ServiceChamadoImpl.class.getName(), e);
			throw new BusinessException(SystemConstant.MSG_ERRO_CALLBACK);
		}
	}
	
	@Override
	public void notificarObservadorAgendamento() throws BusinessException {
		List<ObservadorAgendamento> listaObsAgendamentos = RepositorioObsAgendamento.getInstance().getObservers();
		for(int i = 0;  i <listaObsAgendamentos.size(); i++)
		{
			ObservadorAgendamento obs = listaObsAgendamentos.get(i);
			try{
				obs.atualizarFila(FilaChamado.getInstance().getFilaAgendamento());
				
			}catch(RemoteException e){
				// se o observador fila está indisponivel, remove
				RepositorioObsAgendamento.getInstance().removeObserver(listaObsAgendamentos.get(i));
				i--;
			}	
		}	
	}

	@Override
	public void removerObservadorAgendamento(ObservadorAgendamento obs) {
		RepositorioObsAgendamento.getInstance().removeObserver(obs);
	}
	
	@Override
	public void cadastrarChamado(Chamado chamado) throws BusinessException {

		// TODO: adicionar no banco.
		boolean inseriu = FacadeChamado.criarChamado(chamado);
		FilaChamado.getInstance().atualizarChamados();
		Utils.printMsg(this.getClass().getName(), "Cadastrando chamado: " + inseriu);
		if(inseriu){
			notificarObservadorFila();
		}
	}

	@Override
	public void atualizarChamado(Chamado chamado) throws BusinessException {
		HistoricoChamado historicoChamado = FacadeChamado.buscarChamado(chamado);
		historicoChamado.setDataAgentamento(chamado.getDataAgendamento());
		
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
					FilaChamado.getInstance().atualizarAgendamento(chamado);
				}
				else
					FilaChamado.getInstance().removeAgendamento(chamado);
			}
		}
		// atualiza o chamado na fila e notificar.
		FilaChamado.getInstance().atualizarChamado(chamado);
		
		notificarObservadorAgendamento();
		notificarObservadorFila();
	}
	
	@Override
	public void verificarStatus(ObservadorFila obs) throws BusinessException, RemoteException{
		
		if(!RepositorioObsChamado.getInstance().getObservers().contains(obs)){
			RepositorioObsChamado.getInstance().addObserver(obs);
			obs.atualizarFila(FilaChamado.getInstance().getFila());
		}
	}
	
	@Override
	public void verificarStatusAgendamento(ObservadorAgendamento obs) throws BusinessException, RemoteException{
		
		if(!RepositorioObsAgendamento.getInstance().getObservers().contains(obs)){
			RepositorioObsAgendamento.getInstance().addObserver(obs);
			obs.atualizarFila(FilaChamado.getInstance().getFilaAgendamento());
		}
	}
}
