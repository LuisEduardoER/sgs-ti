package persistencia.facade;

import common.entity.Porte;
import common.exception.BusinessException;
import persistencia.dao.DAOPorte;
import persistencia.sql.SQLPorte;

public class FacadePorte 
{	
	/**
	 * TODO - Descrever melhor os campos
	 */
	/**
	 * 
	 * @param tipoFalha
	 * @return
	 */
	public static boolean criarPorte(Porte porte) throws BusinessException
	{		
		return false;
	}
	
	/**
	 * Metodo que procura um objeto pelo ID
	 * @param codigo
	 * @return
	 */
	public static Porte getById(int codigo) throws BusinessException{
		DAOPorte dao = new SQLPorte();
		return dao.getById(codigo);
	}
	
}
