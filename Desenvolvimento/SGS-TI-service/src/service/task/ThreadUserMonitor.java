package service.task;

import java.rmi.RemoteException;
import java.util.Iterator;
import service.remote.ServiceUsuarioImpl;
import common.entity.UsuarioAutenticado;
import common.util.SystemConstant;
import common.util.Utils;

public class ThreadUserMonitor extends Thread{

	private ServiceUsuarioImpl servicoUsuario;
	
	public ThreadUserMonitor(ServiceUsuarioImpl servicoUsuario) {
		// Pega a instancia do ServicoUsuario para enviar as tarefas e pegar usuários autenticados
		this.servicoUsuario = servicoUsuario;
	}
	
	@Override
	public void run() {
		Utils.printMsg(this.getClass().getName(),"Inicializando ThreadUserMonitor...");

		while(true)
		{
			try{
				// TODO : DENIS - VER EXCEPTIONS
				// Se tiver algum usuario, verifica o tempo de atividade
				Utils.printMsg(this.getClass().getSimpleName(), "Qtde Usuários:" + servicoUsuario.getUsuarioAutenticado().size());
				if(servicoUsuario.getUsuarioAutenticado().size()>0){
					// Se o tempo de atividade for maior que o máximo, manda extermina-lo
					Iterator<UsuarioAutenticado> ua = servicoUsuario.getUsuarioAutenticado().iterator();
					while(ua.hasNext()){
						UsuarioAutenticado user = ua.next();
						user.getObservador();
						long ultimaAtividade = user.getUltimaAtualizacao().getTime();
						double difTime = (System.currentTimeMillis() - ultimaAtividade);
						double tempo = Utils.milisegundosParaMin(difTime);
						ThreadNotificadorUsuario thread;
						if(tempo > SystemConstant.MAX_SESSION_TIME_MIN){
							// Kill user
							thread = new ThreadNotificadorUsuario(user,
									UsuarioAutenticado.ENCERRAR_CLIENTE);
							thread.start();
							break;
						}else if(tempo > SystemConstant.SESSION_TIME_ALERT_MIN){
							// Alertar o usuário que o tempo para encerrar esta proximo
							thread = new ThreadNotificadorUsuario(user,
									UsuarioAutenticado.TEMPO_MAX_EXPIRANDO);
							thread.start();
							break;
						}
						
					}
				}
				sleep(SystemConstant.USER_MONITOR_SLEEP_TIME);
			}catch (InterruptedException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
}
