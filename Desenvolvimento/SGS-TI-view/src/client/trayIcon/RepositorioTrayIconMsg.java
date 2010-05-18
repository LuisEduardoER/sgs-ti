package client.trayIcon;

import java.util.LinkedList;
import java.util.List;

public class RepositorioTrayIconMsg {
	private List<TrayIconMessage> menssagens;
	private static RepositorioTrayIconMsg instance;
	
	private RepositorioTrayIconMsg(){
		menssagens = new LinkedList<TrayIconMessage>();
	}
	
	public static RepositorioTrayIconMsg getInstance(){
		if(instance == null)
			instance = new RepositorioTrayIconMsg();
		
		return instance;
	}
	
	public synchronized TrayIconMessage getMsg(){
		try {
			if(menssagens.isEmpty())
				wait();
			
		} catch (InterruptedException e) {
			/*Não faça nada*/
		}
		
		return menssagens.remove(0);
	}
	public synchronized void addMsg(TrayIconMessage msg){
		menssagens.add(msg);
		notifyAll();
	}
}
