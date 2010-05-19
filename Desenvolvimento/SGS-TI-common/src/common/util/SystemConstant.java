package common.util;

public class SystemConstant {

	// Constantes do Sistema
	public static final boolean DEBUG_MODE 					= true;
	public static final double 	MAX_SESSION_TIME_MIN		= 2d; 
	public static final double 	SESSION_TIME_ALERT_MIN		= 1.5d; 
	public static final String	TEMPO_PARA_DESLIGAR  		= "1 minuto"; 
	public static final int		USER_MONITOR_SLEEP_TIME 	= 2000;
	public static final int 	SERVER_CHECK_STATUS_TIME	= 1000;
	public static final int 	SERVER_CHECK_RECONECT_TIME	= 1000;
	
	
	
	// Constantes
	public static final String SERVER_STATUS = "Server status:";
	public static final String STATUS_ONLINE = "ONLINE";
	public static final String STATUS_OFFLINE = "OFFLINE";
	public static final String USUARIO_LOGADO = "Usuário logado: ";
	public static final String SYSTEM_TITLE  = "SGS-TI";
	public static final String LOGIN_TITLE = "Login";
	
	
	// Constantes de erro
	public static final String MSG_ERRO_OBTER_SERVICO_REMOTO = "Ocorreu um erro ao subir o serviço remoto.";
	public static final String MSG_ERRO_CAMINHO_CONEXAO_REMOTO = "A string de conexão é inválida.";
	
	
	// Constantes de erro amigaveis - view
	public static final String MSG_AM_USUARIO_INVALIDO = "Usuário ou senha inválido.";
	
	
	// Constantes de erro amigaveis - CRITICOS
	public static final String MSG_AM_SEM_CONEXAO_REMOTA = "Ocorreu um erro ao conectar com o servidor!\r\nAlgumas funcionalidades podem estar indisponíveis.";
	
	public static final void printMsg(String msg){
		if(DEBUG_MODE)
			System.out.println();
		
	}
	
}
