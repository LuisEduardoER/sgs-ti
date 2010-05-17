package common.util;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collection;

import common.exception.BusinessException;
import common.remote.ServiceChamado;
import common.remote.ServiceUsuario;

public class Utils {

	public static ServiceChamado obterServiceChamado() throws BusinessException{
		try{	
			return (ServiceChamado) Naming.lookup("rmi://localhost/serviceChamado");
			
		}catch(RemoteException e){
			throw new BusinessException("N�o consegui conectar ao servi�o remoto.");
		}catch(MalformedURLException e ){
			
		}catch(NotBoundException e){
			
		}
		return null;
	}
	
	public static ServiceUsuario obterServiceUsuario() throws BusinessException{
		try{	
			return (ServiceUsuario) Naming.lookup("rmi://localhost/serviceUsuario");
			
		}catch(RemoteException e){
			throw new BusinessException("N�o consegui conectar ao servi�o remoto.");
		}catch(MalformedURLException e ){
			
		}catch(NotBoundException e){
		}
		return null;
	}
	
	public static double milisegundosParaMin(double tempo){
		double min = ((tempo/1000)/60);
		return min;
	}
	
	public static void printMsg(String className, String msg){
		if(SystemConstant.DEBUG_MODE)
			System.out.println("["+ className + "]: " + msg);
	}
	
	/**
	 * Verifica se um valor � nulo ou vazio
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean isNullOrEmpty(Object value) {
		if("".equals(value)) {
			return true;
		} else {
			if (value == null) {
				return true;
			} else if (value.toString().trim().isEmpty()) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Verifica se a cole��o informada � nulo ou vazia.
	 * 
	 * @param value
	 *            O objeto a ser verificado.
	 * @return A indica��o se o par�metro � nulo.
	 */
	public static Boolean isEmptyCollection(Collection<?> value) {
		if (value == null || value.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
}
