package service.base;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import common.entity.Chamado;

public class FilaChamado extends Observable{
	private List<Chamado> fila;
	public static FilaChamado instance;
	
	/**
	 * Construtor
	 */
	private FilaChamado(){
		fila = new LinkedList<Chamado>();
	}
	
	/**
	 * Obtem a instancia da classe.
	 * @return
	 * 		Instancia da FilaChamado.
	 */
	public static FilaChamado getInstance(){
		if(instance ==null)
			instance = new FilaChamado();
		
		return instance;
	}

	public synchronized void removeChamado(Chamado chamado){
		fila.remove(chamado);
		setChanged();
		notifyAll();
	}
	public synchronized void adicionaChamado(Chamado chamado){
		fila.add(chamado);
		setChanged();
		notifyAll();
	}
	
	/**
	 * Obter a fila de chamados.
	 * @return
	 * 		Retorna um lista de chamados somente leitura.
	 */
	public List<Chamado> getFila(){
		List<Chamado> lista = Collections.unmodifiableList(fila);
		return lista;
	}
}
