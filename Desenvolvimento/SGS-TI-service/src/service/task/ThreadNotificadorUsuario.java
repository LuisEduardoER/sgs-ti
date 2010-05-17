package service.task;

import java.rmi.RemoteException;

import common.entity.UsuarioAutenticado;

public class ThreadNotificadorUsuario extends Thread{

	private UsuarioAutenticado usuario;
	private int tarefa;

	public ThreadNotificadorUsuario(UsuarioAutenticado usuario, int tarefa) {
		this.usuario = usuario;
		this.tarefa = tarefa;
	}

	@Override
	public void run() {
		try {
			switch (tarefa) {
			case UsuarioAutenticado.ENCERRAR_CLIENTE:
				usuario.getObservador().encerrarClient();
				break;
			case UsuarioAutenticado.TEMPO_MAX_EXPIRANDO:
				usuario.getObservador().notificarTempoExcedido();
				break;
			default:
				break;
			}
		} catch (RemoteException e) {
			// Caso de problema com a comunicação, ignora
		}
	}
}
