package service.base;

import java.util.Collections;
import java.util.List;
import java.util.Observable;

import persistencia.facade.FacadeChamado;

import common.entity.Chamado;
import common.exception.BusinessException;

public class FilaChamado extends Observable
{
	private List<Chamado> fila;
	private List<Chamado> filaAgendamento;
	public static FilaChamado instance;
	
	/**
	 * Construtor
	 */
	private FilaChamado() throws BusinessException{
		fila = FacadeChamado.buscarChamadosAbertos();
		//fila = new LinkedList<Chamado>();
		filaAgendamento = FacadeChamado.buscarChamadosAgendados();
		//filaAgendamento = new LinkedList<Chamado>();
	}
	
	/**
	 * Busca os chamados do banco
	 * @throws BusinessException
	 */
	public void atualizarChamados() throws BusinessException{
		fila = FacadeChamado.buscarChamadosAbertos();
	}
	
	/**
	 * Obtem a instancia da classe.
	 * @return
	 * 		Instancia da FilaChamado.
	 */
	public static FilaChamado getInstance() throws BusinessException{
		if(instance ==null)
			instance = new FilaChamado();
		
		return instance;
	}

	public synchronized void removeChamado(Chamado chamado){
		if(fila.contains(chamado)){
			fila.remove(chamado);
			setChanged();
			notifyAll();
		}
	}
	public synchronized void adicionaChamado(Chamado chamado){
		fila.add(chamado);
		setChanged();
		notifyAll();
	}
	public synchronized void atualizarChamado(Chamado chamado){
		/*
		boolean achou = false;
		for(Chamado c : fila){
			if(c.equals(chamado)){
				achou = true;
				c = chamado;
			}
		}
		if(!achou)
			adicionaChamado(chamado);
		*/
		
		try {
			fila = FacadeChamado.buscarChamadosAbertos();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		
	}
	
	public synchronized void removeAgendamento(Chamado chamado){
		filaAgendamento.remove(chamado);
		setChanged();
		notifyAll();
	}
	public synchronized void adicionaAgendamento(Chamado chamado){
		filaAgendamento.add(chamado);
		setChanged();
		notifyAll();
	}
	public synchronized void atualizarAgendamento(Chamado chamado){
		boolean achou = false;
		for(Chamado c : fila){
			if(c.equals(chamado)){
				c = chamado;
				achou =  true;
			}
		}
		if(!achou)
			adicionaAgendamento(chamado);
	}
	
	/**
	 * Obter a fila de chamados.
	 * @return
	 * 		Retorna um lista de chamados somente leitura.
	 */
	public List<Chamado> getFila(){
		List<Chamado> lista = Collections.unmodifiableList(fila);
		
		// pegando do banco // List<Chamado> lista = FacadeChamado.buscarChamadosAbertos();
		return lista;
	}
	
	public List<Chamado> getFilaAgendamento(){
		List<Chamado> lista = Collections.unmodifiableList(filaAgendamento);
		return lista;
	}
}
