package service.base;

import java.util.LinkedList;
import java.util.List;
import common.entity.UsuarioAutenticado;

public class RepositorioObsUsuario {
	private static RepositorioObsUsuario instance;
	private List<UsuarioAutenticado> listaUserAutenticado;
	private Object mutex;
	
	public static RepositorioObsUsuario getInstance(){
		if(instance == null)
			instance = new RepositorioObsUsuario();
		
		return instance;
	}
	
	private RepositorioObsUsuario(){
		listaUserAutenticado = new LinkedList<UsuarioAutenticado>();
	}
	
	public void addObserver(UsuarioAutenticado obs){
		synchronized (mutex) {
			listaUserAutenticado.add(obs);
		}
	}
	
	public void removeObserver(UsuarioAutenticado obs){
		synchronized (mutex) {
			if(listaUserAutenticado.contains(obs))
				listaUserAutenticado.remove(obs);
		}
	}
	
	public List<UsuarioAutenticado> getObservers(){
		return listaUserAutenticado;
	}
}
