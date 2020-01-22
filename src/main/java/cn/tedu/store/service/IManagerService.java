package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Manager;
import cn.tedu.store.service.exception.AccessDeniedException;
import cn.tedu.store.service.exception.DuplicateKeyException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.ManagerNotFoundException;
import cn.tedu.store.service.exception.PasswordNotMatchException;
import cn.tedu.store.service.exception.UpdateException;
import cn.tedu.store.vo.ManagerVO;

/**
 * 處理後台人員資料的業務層接口
 * @author Klein
 */
public interface IManagerService {
	
	/**
	 * 根據要搜尋的inputValue資料，找出符合的人員數
	 * @param inputValue
	 * @return 符合搜尋inputValue值的人員數
	 */
	Integer getAlls(String inputValue);
	
	/**
	 * 停權(軟刪除)後台人員資料
	 * @param id 後台人員的id
	 * @param mid 目前登入的後台人員的id
	 * @throws ManagerNotFoundException 找不到人員數據異常
	 * @throws AccessDeniedException 權限異常
	 * @throws UpdateException 更新(軟刪除)異常
	 */
	void changeById(Integer id, Integer mid) 
		throws ManagerNotFoundException, 
			AccessDeniedException, 
				UpdateException;
	
	/**
	 * 後台人員註冊
	 * @param manager 後台人員的註冊信息
	 * @return 成功註冊的後台人員數據
	 * @throws DuplicateKeyException 違返Unique約束的異常
	 * @throws InsertException 插入數據出現未知錯誤的異常
	 */
	Manager reg(Manager manager) throws 
		DuplicateKeyException, InsertException;
	
	/**
	 * 後台人員登入
	 * @param username 後台人員帳號
	 * @param password 後台人員密碼
	 * @return 成功登入的後台人員數據
	 * @throws ManagerNotFoundException 後台人員帳號不存在的異常
	 * @throws PasswordNotMatchException 後台人員帳號與後台人員密碼不匹配的異常
	 */
	Manager login(String username, String password) throws
		ManagerNotFoundException, PasswordNotMatchException;

	/**
	 * 根據id獲取後台人員數據
	 * @param id 後台人員id
	 * @return 匹配的後台人員數據，如果沒有匹配的數據，則返回null
	 */
	Manager getById(Integer id);
	
	/**
	 * 根據要搜尋的inputValue資料及目前頁數跟每頁筆數，找出符合的人員清單列表
	 * @param page 第幾頁
	 * @param pageSize 每頁顯示幾筆資料
	 * @param inputValue 要搜尋的inputValue值
	 * @return 符合inputVlaue值的人員列表數據
	 */
	List<ManagerVO> getList(
		Integer page,
		Integer pageSize,
		String inputValue);
}
