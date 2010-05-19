package persistencia.facade;

import common.entity.TipoChamado;

import persistencia.dao.DAOTipoChamado;
import persistencia.sql.SQLTipoChamado;

public class FacadeTipoChamado {
	
	public static boolean criarTipoChamado(TipoChamado tipoChamado){
		
		DAOTipoChamado dao = new SQLTipoChamado();
		
		boolean inserido = dao.adicionaTipoChamado(tipoChamado);
		return inserido;
	}

	public static int procurarTipoChamado(TipoChamado tipoChamado){
		
		DAOTipoChamado dao = new SQLTipoChamado();
		
		int codigo = dao.procurarTipoChamado(tipoChamado);	
		return codigo;
	}
}
