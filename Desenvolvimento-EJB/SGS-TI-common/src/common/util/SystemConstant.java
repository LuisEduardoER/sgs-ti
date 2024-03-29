package common.util;

public class SystemConstant {

	//TODO EH  CACHE
	// Constantes do Sistema
	public static final boolean DEBUG_MODE 					= true;
	public static final double 	MAX_SESSION_TIME_MIN		= 78785d; 
	public static final double 	SESSION_TIME_ALERT_MIN		= 4334d; 
	public static final String	TEMPO_PARA_DESLIGAR  		= "1 minuto"; 
	public static final int		USER_MONITOR_SLEEP_TIME 	= 2000;
	public static final int 	SERVER_CHECK_STATUS_TIME	= 3000;
	public static final int 	SERVER_CHECK_RECONECT_TIME	= 3000;
	public static final int		MAX_TENTATIVAS_RECONEXAO	= 5;
	public static final long	TEMPO_ESPERA_MONITOR_CONEXAO	= 10000;
	public static final long 	TIME_TO_LIVE_MSG_TRAY		= 4000;
	
	// Constantes
	public static final String SERVER_STATUS = "Server status:";
	public static final String STATUS_ONLINE = "ONLINE";
	public static final String STATUS_OFFLINE = "OFFLINE";
	public static final String USUARIO_LOGADO = "Usu�rio logado: ";
	public static final String SYSTEM_TITLE  = "SGS-TI";
	public static final String LOGIN_TITLE = "Login";
		
	// Constantes de erro
	public static final String MSG_ERRO_OBTER_SERVICO_REMOTO = "Ocorreu um erro ao subir o servi�o remoto.";
	public static final String MSG_ERRO_CAMINHO_CONEXAO_REMOTO = "A string de conex�o � inv�lida.";	
	public static final String MSG_ERRO_CALLBACK = "Ocorreu um erro ao notificar um cliente (callback).";
	
	// Constantes de erro amigaveis - view
	public static final String MSG_AM_USUARIO_INVALIDO = "Usu�rio ou senha inv�lido.";
	public static final String MSG_AM_ATUALIZAR_STATUS = "Erro ao atualizar o status do usuario.";
		
	// Constantes de erro amigaveis - CRITICOS
	public static final String MSG_AM_SEM_CONEXAO_REMOTA = "Ocorreu um erro ao conectar com o servidor!\r\nAlgumas funcionalidades podem estar indispon�veis.";
	public static final String MSG_AM_ERRO_CONEXAO = "Ocorreu um erro ao conectar com o banco de dados.";
	
	/**
	 * TODO - Descrever melhor os campos
	 */
	/**
	 * 
	 * @param msg
	 */
	public static final void printMsg(String msg){
		if(DEBUG_MODE)
			System.out.println();	
	}	
}
