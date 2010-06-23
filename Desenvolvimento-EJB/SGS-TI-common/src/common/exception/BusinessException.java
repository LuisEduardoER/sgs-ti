package common.exception;

public class BusinessException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	/**
	* TODO - Documentar
	*/
	/**
	 * 
	 * @param msg
	 */
	public BusinessException(String msg) {
		super(msg);
	}
}
