package cn.tedu.store.controller.exception;

/**
 * 上傳的文件超出了限制的異常
 * @author Klein
 */
public class FileSizeOutOfLimitException extends FileUploadException {

	private static final long serialVersionUID = -8449983418301878109L;

	public FileSizeOutOfLimitException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileSizeOutOfLimitException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public FileSizeOutOfLimitException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public FileSizeOutOfLimitException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public FileSizeOutOfLimitException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
}
