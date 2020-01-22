package cn.tedu.store.service.exception;

/**
 * 業務異常，是當前項目中所有業務異常的基類
 * @author Klein
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 5631578415492734367L;

	public ServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	public ServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	public ServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}


