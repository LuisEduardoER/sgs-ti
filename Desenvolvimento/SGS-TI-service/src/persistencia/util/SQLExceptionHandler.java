package persistencia.util;

import java.sql.SQLException;
import common.exception.BusinessException;
import common.util.Utils;

public class SQLExceptionHandler {
	/**
	 * Metodo que trata as exce��es de SQL
	 * @param source
	 * 		Classe que lan�ou a exce�ao.
	 * @param e
	 * 		SQL Exception
	 * @throws BusinessException
	 * 		Lan�a a melhor exce��o
	 */
	public static void tratarSQLException(String source, SQLException e) throws BusinessException{

		// printa o erro no console
		Utils.printErro(source, e);
		
		switch(e.getErrorCode()){
			case 1: throw new BusinessException("O registro j� existe.");
			case 1400: throw new BusinessException("N�o � permitido inserir valores nulos.");
			default: throw new BusinessException("Erro no banco de dados. Por favor, contate o administrador.");
		}

	}
}
