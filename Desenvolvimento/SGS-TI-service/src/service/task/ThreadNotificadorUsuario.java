package service.task;

import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import common.entity.UsuarioAutenticado;

public class ThreadNotificadorUsuario extends Thread{

	private UsuarioAutenticado usuario;
	private int tarefa;
	private String mensagem;
	private String titulo;
	private int tipoAlerta;
	private int acao;
	
	public static final int MSG_INFO = JOptionPane.INFORMATION_MESSAGE;
	public static final int MSG_ERRO = JOptionPane.ERROR_MESSAGE;
	public static final int MSG_ATENCAO = JOptionPane.WARNING_MESSAGE;

	/**
	 * Notifica um user
	 * @param usuario Que sera notificado
	 * @param tarefa Tarefa que ira realizar, presentes em UsuarioAutenticado.tarefa
	 * @param mensagem Opcional
	 * @param titulo Opcional
	 * @param tipoAlert ThreadNotificadorUsuario.tipoMsg
	 * @param acao Acao que usuario sofrera, presentes em UsuarioAutenticado.tarefa
	 */
	public ThreadNotificadorUsuario(UsuarioAutenticado usuario, int tarefa, String mensagem, String titulo, int tipoAlerta, int acao) {
		this.usuario = usuario;
		this.tarefa = tarefa;
		this.mensagem = mensagem;
		this.titulo = titulo;
		this.tipoAlerta = tipoAlerta;
		this.acao = acao;
	}
	
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
			case UsuarioAutenticado.NOTIFICACAO_REMOTA:
				usuario.getObservador().notificaoRemota(mensagem, titulo, tipoAlerta, acao);
			default:
				break;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			// Caso de problema com a comunicação, ignora
		}
	}
}
