package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.controller.exception.FileEmptyException;
import cn.tedu.store.controller.exception.FileSizeOutOfLimitException;
import cn.tedu.store.controller.exception.FileTypeNotSupportException;
import cn.tedu.store.controller.exception.FileUploadException;
import cn.tedu.store.controller.exception.RequestException;
import cn.tedu.store.service.exception.AccessDeniedException;
import cn.tedu.store.service.exception.AddressNotFoundException;
import cn.tedu.store.service.exception.DeleteException;
import cn.tedu.store.service.exception.DuplicateKeyException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.PasswordNotMatchException;
import cn.tedu.store.service.exception.ServiceException;
import cn.tedu.store.service.exception.UpdateException;
import cn.tedu.store.service.exception.UserNotFoundException;
import cn.tedu.store.util.ResponseResult;

/**
 * 當前項目中所有控制器類的基類
 * @author Klein
 */
public abstract class BaseController {
	
	public static final Integer SUCCESS = 200;
	
	@ExceptionHandler({
		ServiceException.class, 
		RequestException.class
	})
	@ResponseBody
	public ResponseResult<Void> handleException(Exception e){
		Integer state = null;
		if (e instanceof DuplicateKeyException) {
			//400 - 違反了Unique約束的異常
			state = 400;
		} else if (e instanceof UserNotFoundException) {
			//401 - 用戶名不存在的異常
			state = 401;
		} else if (e instanceof PasswordNotMatchException) {
			//402 - 用戶密碼與用戶名不匹配的異常
			state = 402;
		} else if (e instanceof AddressNotFoundException) {
			//403 - 收貨地址數據不存在的異常
			state = 403;
		} else if (e instanceof AccessDeniedException) {
			//404 - 訪問收貨地址被拒絕的異常
			state = 404;
		} else if (e instanceof InsertException) {
			//500 - 插入數據異常
			state = 500;
		} else if (e instanceof UpdateException) {
			//501 - 更新數據異常
			state = 501;
		} else if (e instanceof DeleteException) {
			//502 - 刪除數據異常
			state = 502;
		} else if (e instanceof FileEmptyException) { //異常順序，先寫子級再寫父級
			//600 - 上傳的文件為空的異常
			state = 600;
		} else if (e instanceof FileSizeOutOfLimitException) { 
			//601 - 上傳的文件超出了限制的異常
			state = 601;
		} else if (e instanceof FileTypeNotSupportException) { 
			//602 - 上傳的文件類型不支持的異常
			state = 602;
		} else if (e instanceof FileUploadException) { 
			//610 - 文件上傳異常
			state = 610;
		}
		return new ResponseResult<>(state,e);
	}
	
	/**
	 * 從Session中獲取uid - 封裝獲取Session的當中的uid
	 * @param session HttpSession物件
	 * @return 當前登入的用戶的id
	 */
	protected Integer getUidFromSession(HttpSession session) {
	    return Integer.valueOf(session.getAttribute("uid").toString());
	}
	
	/**
	 * 從Session中獲取mid - 封裝獲取Session的當中的mid
	 * @param session HttpSession物件
	 * @return 當前登入的用戶的id
	 */
	protected Integer getMidFromSession(HttpSession session) {
	    return Integer.valueOf(session.getAttribute("mid").toString());
	}
}



