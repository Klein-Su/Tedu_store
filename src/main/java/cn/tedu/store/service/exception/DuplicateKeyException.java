package cn.tedu.store.service.exception;

/**
 * 違反了Unique約束的異常
 * @author Klein
 */
public class DuplicateKeyException extends ServiceException {

	private static final long serialVersionUID = 1575222253909645563L;

	public DuplicateKeyException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DuplicateKeyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
	public DuplicateKeyException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	public DuplicateKeyException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	public DuplicateKeyException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}


