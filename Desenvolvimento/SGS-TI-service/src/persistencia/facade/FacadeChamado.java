package persistencia.facade;

import common.entity.Chamado;

import persistencia.dao.DAOChamado;
import persistencia.sql.SQLChamado;

public class FacadeChamado {
	
	public static boolean criarChamado(Chamado chamado){
		
		DAOChamado dao = new SQLChamado();
		
		boolean inserido = dao.adicionaChamado(chamado);
		return inserido;
	}

	public static boolean atualizarChamado(Chamado chamado) {
		
		DAOChamado dao = new SQLChamado();
		
		boolean atualizado = dao.atualizarChamado(chamado);	
		return atualizado;
	}
}
