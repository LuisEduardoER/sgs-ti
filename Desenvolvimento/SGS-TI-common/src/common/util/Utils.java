package common.util;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import common.exception.BusinessException;
import common.remote.ServiceChamado;
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
	
	public static ServiceUsuario obterServiceUsuario() throws BusinessException{
		try{	
			return (ServiceUsuario) Naming.lookup("rmi://localhost/serviceUsuario");
			
		}catch(RemoteException e){
			throw new BusinessException("Não consegui conectar ao serviço remoto.");
		}catch(MalformedURLException e ){
			
		}catch(NotBoundException e){
			
		}
		return null;
	}
	
	public static void printMsg(String className, String msg){
		if(SystemConstant.DEBUG_MODE)
			System.out.println("["+ className + "]: " + msg);
	}
	
}
