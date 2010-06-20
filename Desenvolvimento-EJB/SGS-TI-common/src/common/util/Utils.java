package common.util;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collection;

import common.exception.BusinessException;
import common.remote.ServiceChamado;
import common.remote.ServiceChamadoItens;
import common.remote.ServiceUsuario;

public class Utils {

	public static ServiceChamado obterServiceChamado() throws BusinessException{
		try{	
			return (ServiceChamado) Naming.lookup("rmi://localhost/serviceChamado");
			
		}catch(RemoteException e){
			throw new BusinessException("Não consegui conectar ao serviço remoto.");
		}catch(MalformedURLException e ){
			
		}catch(NotBoundException e){
			
		}
		return null;
	}
	
	public static ServiceUsuario obterServiceUsuario() throws RemoteException, BusinessException{
		try{	
			return (ServiceUsuario) Naming.lookup("rmi://localhost/serviceUsuario");
			
		}catch(RemoteException e){
			throw new BusinessException(SystemConstant.MSG_ERRO_OBTER_SERVICO_REMOTO);
		}catch(MalformedURLException e ){
			throw new BusinessException(SystemConstant.MSG_ERRO_CAMINHO_CONEXAO_REMOTO);
		}catch(NotBoundException e){
			throw new BusinessException(SystemConstant.MSG_ERRO_OBTER_SERVICO_REMOTO);
		}
	}
	
	public static ServiceChamadoItens obterServiceChamadoItens() throws BusinessException{
		try{	
			return (ServiceChamadoItens) Naming.lookup("rmi://localhost/serviceChamadoItens");
			
		}catch(RemoteException e){
			throw new BusinessException("Não consegui conectar ao serviço remoto.");
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
	public static void printErro(String className, Exception e){
		if(SystemConstant.DEBUG_MODE){
			System.err.println("************ ERROR ************");
			System.err.println("Classe que lançou: " + className);
			System.err.println("Erro: " + e.getMessage());
			System.err.println("Classe da exception: " + e.getClass().getName());
			System.err.println("Stack:\n");
			e.printStackTrace();
			System.err.println("\n***************************************************************");
		}
	}
	
	/**
	 * Verifica se um valor é nulo ou vazio
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
	 * Verifica se a coleção informada é nulo ou vazia.
	 * 
	 * @param value
	 *            O objeto a ser verificado.
	 * @return A indicação se o parâmetro é nulo.
	 */
	public static Boolean isEmptyCollection(Collection<?> value) {
		if (value == null || value.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
}
