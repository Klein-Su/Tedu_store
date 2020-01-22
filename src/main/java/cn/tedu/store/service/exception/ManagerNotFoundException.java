package cn.tedu.store.service.exception;

/**
 * 後台人員帳號不存在的異常
 * @author Klein
 */
public class ManagerNotFoundException extends ServiceException {

	private static final long serialVersionUID = 5924870642848403773L;

	public ManagerNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ManagerNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ManagerNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ManagerNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ManagerNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
