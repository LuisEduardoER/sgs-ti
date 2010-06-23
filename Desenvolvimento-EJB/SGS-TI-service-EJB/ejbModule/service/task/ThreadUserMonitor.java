package service.task;

import java.util.LinkedList;

import service.base.RepositorioObsUsuario;
import service.remote.ServiceUsuarioImpl;
import common.entity.UsuarioAutenticado;
import common.util.SystemConstant;
import common.util.Utils;

public class ThreadUserMonitor extends Thread{


	public ThreadUserMonitor(ServiceUsuarioImpl servicoUsuario) {
	}

	@Override
	public void run() {
		Utils.printMsg(this.getClass().getName(),"Inicializando ThreadUserMonitor...");

		while(true)
		{
			try{
				// TODO : DENIS - VER EXCEPTIONS
				// Se tiver algum usuario, verifica o tempo de atividade
				//Utils.printMsg(this.getClass().getSimpleName(), "Qtde Usuários:" + servicoUsuario.getUsuarioAutenticado().size());
				if(RepositorioObsUsuario.getInstance().getObservers().size()>0){
					// Se o tempo de atividade for maior que o máximo, manda extermina-lo
					LinkedList<UsuarioAutenticado> lista = (LinkedList<UsuarioAutenticado>) RepositorioObsUsuario.getInstance().getObservers();
					for(UsuarioAutenticado user : lista){
						/*UsuarioAutenticado ua = uas;
						if(ua.getUsuario().equals(usuario)){
							Utils.printMsg(this.getClass().getName(),"User encontrado, atualizando horario atual:"+ua.getUltimaAtualizacao().getTime());
							ua.setUltimaAtualizacao(new Date());
							Utils.printMsg(this.getClass().getName(),"Novo horário do usuario                   :"+ua.getUltimaAtualizacao().getTime());
							break;
						}*/
						
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
				// ignora excecoes
			}catch (InterruptedException e) {
			}
		}
	}
}
