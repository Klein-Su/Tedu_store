package cn.tedu.store.service;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.exception.DuplicateKeyException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.PasswordNotMatchException;
import cn.tedu.store.service.exception.UpdateException;
import cn.tedu.store.service.exception.UserNotFoundException;

/**
 * 處理用戶資料的業務層接口
 * @author Klein
 */
public interface IUserService {
	
	/**
	 * 用戶註冊
	 * @param user 用戶的註冊信息
	 * @return 成功註冊的用戶數據
	 * @throws DuplicateKeyException 違返Unique約束的異常
	 * @throws InsertException 插入數據出現未知錯誤的異常
	 */
	User reg(User user) throws 
		DuplicateKeyException, InsertException;
	
	/**
	 * 用戶登入
	 * @param username 用戶帳號
	 * @param password 用戶密碼
	 * @return 成功登入的用戶數據
	 * @throws UserNotFoundException 用戶帳號不存在的異常
	 * @throws PasswordNotMatchException 用戶帳號與用戶密碼不匹配的異常
	 */
	User login(String username, String password) throws
		UserNotFoundException, PasswordNotMatchException;

	/**
	 * 根據id獲取用戶數據
	 * @param id 用戶id
	 * @return 匹配的用戶數據，如果沒有匹配的數據，則返回null
	 */
	User getById(Integer id);
	
	/**
	 * 修改密碼
	 * @param uid 用戶id
	 * @param oldPassword 舊密碼
	 * @param newPassword 新密碼
	 * @throws UserNotFoundException 用戶帳號不存在的異常
	 * @throws PasswordNotMatchException 用戶帳號與用戶密碼不匹配的異常
	 * @throws UpdateException 修改密碼出現未知錯誤的異常
	 */
	void changePassword(
		Integer uid, String oldPassword, String newPassword) 
			throws UserNotFoundException, 
				PasswordNotMatchException,
				UpdateException;
	
	/**
	 * 修改頭像
	 * @param uid 用戶的id
	 * @param avatar 頭像的路徑
	 * @throws UserNotFoundException 用戶帳號不存在的異常
	 * @throws UpdateException 修改頭像出現未知錯誤的異常
	 */
	void changeAvatar(
			Integer uid, String avatar) 
				throws UserNotFoundException, 
					UpdateException;
	
	/**
	 * 修改用戶個人資料
	 * @param user 用戶資料
	 * @throws UserNotFoundException 用戶帳號不存在的異常
	 * @throws UpdateException 修改個人資料出現未知錯誤的異常
	 */
	void changeInfo(User user) 
		throws UserNotFoundException, UpdateException;
}

