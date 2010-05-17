package common.util;

public class SystemConstant {

	// Constantes do Sistema
	public static final boolean DEBUG_MODE 				= true;
	public static final double 	MAX_SESSION_TIME_MIN	= 0.3d; 
	public static final double 	SESSION_TIME_ALERT_MIN	= 0.2d; 
	public static final int		USER_MONITOR_SLEEP_TIME = 2000;
	
	
	public static final void printMsg(String msg){
		if(DEBUG_MODE)
			System.out.println();
		
	}
	
}
