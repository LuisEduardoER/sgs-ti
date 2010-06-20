package common.exception;

public class RemoteException extends BusinessException 
{
	private static final long serialVersionUID = 1L;

	public RemoteException(String msg) {
		super(msg);
	}
}
