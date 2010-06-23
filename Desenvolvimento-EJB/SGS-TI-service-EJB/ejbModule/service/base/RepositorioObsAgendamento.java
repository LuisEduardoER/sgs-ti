package service.base;

import java.util.LinkedList;
import java.util.List;
import common.remote.ObservadorAgendamento;

public class RepositorioObsAgendamento {
	private static RepositorioObsAgendamento instance;
	private List<ObservadorAgendamento> listaObsAgendamento;
	private Object mutex;
	
	
	private RepositorioObsAgendamento(){
		mutex = new Object();
		listaObsAgendamento = new LinkedList<ObservadorAgendamento>();
	}
	
	public static RepositorioObsAgendamento getInstance(){
		if(instance == null)
			instance = new RepositorioObsAgendamento();
		
		return instance;
	}
	
	public void addObserver(ObservadorAgendamento obs){
		synchronized (mutex) {
			listaObsAgendamento.add(obs);
		}
	}
	
	public void removeObserver(ObservadorAgendamento obs){
		synchronized (mutex) {
			if(listaObsAgendamento.contains(obs))
				listaObsAgendamento.remove(obs);
		}
	}
	
	public List<ObservadorAgendamento> getObservers(){
		return listaObsAgendamento;
	}
}
