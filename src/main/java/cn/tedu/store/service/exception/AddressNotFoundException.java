package cn.tedu.store.service.exception;

/**
 * 收貨地址數據不存在的異常類
 * @author Klein
 */
public class AddressNotFoundException extends ServiceException {

	private static final long serialVersionUID = -6743411944054424816L;

	public AddressNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddressNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public AddressNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AddressNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AddressNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
