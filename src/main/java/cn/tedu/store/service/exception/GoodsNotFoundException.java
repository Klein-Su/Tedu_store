package cn.tedu.store.service.exception;

/**
 * 商品資料不存在時的異常類
 * @author Klein
 */
public class GoodsNotFoundException extends ServiceException {

	private static final long serialVersionUID = 2480972495279626291L;

	public GoodsNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GoodsNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public GoodsNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public GoodsNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public GoodsNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
