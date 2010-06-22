package service.base;

import java.util.LinkedList;
import java.util.List;

import common.remote.ObservadorFila;

public class RepositorioObsChamado {
	private static RepositorioObsChamado instance;
	private List<ObservadorFila> listaObsFila;
	private Object mutex;
	
	
	private RepositorioObsChamado(){
		listaObsFila = new LinkedList<ObservadorFila>();
	}
	
	public static RepositorioObsChamado getInstance(){
		if(instance == null)
			instance = new RepositorioObsChamado();
		
		return instance;
	}
	
	public void addObserver(ObservadorFila obs){
		synchronized (mutex) {
			listaObsFila.add(obs);
		}
	}
	
	public void removeObserver(ObservadorFila obs){
		synchronized (mutex) {
			if(listaObsFila.contains(obs))
				listaObsFila.remove(obs);
		}
	}
	
	public List<ObservadorFila> getObservers(){
		return listaObsFila;
	}
}
