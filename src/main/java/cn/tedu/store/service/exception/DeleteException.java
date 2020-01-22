package cn.tedu.store.service.exception;

/**
 * 刪除收貨地址數據時的異常類
 * @author Klein
 */
public class DeleteException extends ServiceException {

	private static final long serialVersionUID = -7707024503946856639L;

	public DeleteException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public DeleteException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DeleteException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DeleteException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
