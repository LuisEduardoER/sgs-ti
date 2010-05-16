package service.task;

import java.util.HashSet;
import java.util.Iterator;

import service.remote.ServiceUsuarioImpl;
import common.entity.UsuarioAutenticado;
import common.util.SystemConstant;

public class UserMonitor extends Thread{

	private ServiceUsuarioImpl servicoUsuario;
	private HashSet<UsuarioAutenticado> usuariosAutenticados;
	
	public UserMonitor(ServiceUsuarioImpl servicoUsuario) {
		// Pega a instancia do ServicoUsuario para enviar as tarefas e pegar usuários autenticados
		this.servicoUsuario = servicoUsuario;
		atualizaUsuariosAutenticados();
	}
	
	@Override
	public void run() {
		printMsg("Inicializando task...");
		while(true)
		{
			try{
				printMsg("Procurando users...");
				// Atualiza a lista que esta no servico
				atualizaUsuariosAutenticados();
				// Se tiver algum usuario, verifica o tempo de atividade
				if(usuariosAutenticados.size()>0){
					// Se o tempo de atividade for maior que o máximo, manda extermina-lo
					Iterator<UsuarioAutenticado> ua = usuariosAutenticados.iterator();
					while(ua.hasNext()){
						UsuarioAutenticado user = ua.next();
						long ultimaAtividade = user.getUltimaAtualizacao().getTime();
						long difTime = (System.currentTimeMillis() - ultimaAtividade);
						printMsg("Diferença tempo:" + difTime);
					}
				}
				sleep(2000);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void atualizaUsuariosAutenticados(){
		// Atualiza os usuarios que estao logados no momento
		this.usuariosAutenticados = servicoUsuario.getUsuarioAutenticado();
	}
	
	public void printMsg(String msg){
		if(SystemConstant.DEBUG_MODE)
			System.out.println("[USER MONITOR]: " + msg);
	}
}
