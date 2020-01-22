package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.exception.DuplicateKeyException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.PasswordNotMatchException;
import cn.tedu.store.service.exception.UpdateException;
import cn.tedu.store.service.exception.UserNotFoundException;

/**
 * 處理用戶資料的業務層實現類
 * @author Klein
 */
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override //用戶註冊
	public User reg(User user) throws InsertException {
		//根據嘗試註冊的用戶名查詢用戶數據
		User data = findByUsername(user.getUsername());
		//判斷查詢到的數據是否為null
		if(data == null) {
			//是：用戶名不存在，允許註冊，則處理密碼加密
			
			//【補充非用戶提交的數據】
			user.setIsDelete(0); //是否已經刪除：否
			//4項日誌
			Date now = new Date();
			user.setCreatedUser(user.getUsername());
			user.setCreatedTime(now);
			user.setModifiedUser(user.getUsername());
			user.setModifiedTime(now);
			
			//加密-1：獲取隨機的UUID作為鹽值
			String salt = UUID.randomUUID().toString().toUpperCase();
			//加密-2：獲取用戶提交的原始密碼
			String srcPassword = user.getPassword();
			//加密-3：基於原始密碼和鹽值執行加密，獲取通過MD5加密的密碼
			String md5Password = getMd5Password(srcPassword, salt);
			//加密-4：將加密後的密碼封裝在user物件中
			user.setPassword(md5Password);
			//加密-5：將鹽值封裝在user物件中
			user.setSalt(salt);
			//執行註冊
			addnew(user);
			//-- 返回註冊的用戶對象
			return user;
		} else {
			//否：用戶名已被佔用，拋出DuplicateKeyException異常
			throw new DuplicateKeyException(
					"註冊失敗！嘗試註冊的用戶名("+user.getUsername()+")已經被佔用！");
		}
	}
	
	/*
	 * 處理異常的方式：
	 * 1. try...catch	處理異常
	 * 2. throw/throws	拋出異常
	 */
	
	@Override //用戶登入
	public User login(String username, String password) throws 
		UserNotFoundException, PasswordNotMatchException {
		//根據參數username查詢用戶數據
		User data = findByUsername(username);
		//判斷用戶數據是否為null
		if (data == null) {
			//是：為null，用戶名不存在，則拋出UserNotFoundException
			throw new UserNotFoundException("登入失敗！您嘗試登入的用戶名不存在！");
		}
		//否：非null，根據用戶名找到了數據，取出鹽值
		String salt = data.getSalt();
		//對參數password執行加密
		String md5Password = getMd5Password(password, salt);
		//判斷密碼是否匹配
		if(data.getPassword().equals(md5Password)) {
			//是：匹配，密碼正確，則判斷是否被刪除
			if(data.getIsDelete() == 1) {
				//是：已被刪除，則拋出UserNotFoundException
				throw new UserNotFoundException("登入失敗！您嘗試登入的用戶信息已被刪除");
			}
			//否：沒被刪除，則登入成功，將第1步查詢的用戶數據中的鹽值和密碼設置為null，再返回用戶數據
			data.setPassword(null);
			data.setSalt(null);
			//返回用戶數據
			return data;
		} else {
			//否：不匹配，密碼錯誤，則拋出PasswordNotMatchException
			throw new PasswordNotMatchException("登入失敗！密碼錯誤！");
		}
	}
	
	@Override
	public void changePassword( Integer uid, String oldPassword, String newPassword) 
			throws UserNotFoundException, PasswordNotMatchException, UpdateException {
		//根據uid查詢用戶數據
		User data = findById(uid);
		//判斷查詢結果是否為null
		if (data == null) {
			//是：拋出異常：UserNotFoundException
			throw new UserNotFoundException("修改密碼失敗！您嘗試訪問的用戶數據不存在！");
		}
		
		//判斷查詢結果中的isDelete是否為1
		if (data.getIsDelete() == 1) {
			//是：拋出異常：UserNotFoundException
			throw new UserNotFoundException("修改密碼失敗！您嘗試訪問的用戶數據已經被刪除！");
		}
		
		//取出查詢結果中的鹽值
		String salt = data.getSalt();
		//對參數oldPassword執行MD5加密
		String oldMd5Password = getMd5Password(oldPassword, salt);
		//將加密結果與查詢結果中的password對比是否匹配
		if (data.getPassword().equals(oldMd5Password)) {
			//是：原密碼正確，對參數newPassword執行MD5加密
			String newMd5Password = getMd5Password(newPassword, salt);
			//獲取當前時間
			Date now = new Date();
			//更新密碼
			updatePassword(uid, newMd5Password, data.getUsername(), now);
		} else {
			//否：原密碼錯誤，拋出異常：PasswordNotMatchException
			throw new PasswordNotMatchException("修改密碼失敗！原密碼錯誤");
		}
	}
	
	@Override
	public void changeAvatar(Integer uid, String avatar) 
			throws UserNotFoundException, UpdateException {
		//根據user.getId()查詢用戶數據
		User data = findById(uid);
		//判斷數據是否為null
		if (data == null) {
			//是：拋出：UserNotFoundException
			throw new UserNotFoundException("修改頭像失敗！您嘗試訪問的用戶數據不存在！");
		}
				
		//判斷is_delete是否為1
		if (data.getIsDelete() == 1) {
			//是：拋出：UserNotFoundException
			throw new UserNotFoundException("修改頭像失敗！您嘗試訪問的用戶數據已經被刪除！");
		}
		
		//執行更新頭像
		String modifiedUser = data.getUsername();
		Date modifiedTime = new Date();
		updateAvatar(uid, avatar, modifiedUser, modifiedTime);
	}
	
	@Override
	public void changeInfo(User user) 
			throws UserNotFoundException, UpdateException {
		//根據user.getId()查詢用戶數據
		User data = findById(user.getId());
		//判斷數據是否為null
		if (data == null) {
			//是：拋出：UserNotFoundException
			throw new UserNotFoundException("修改個人資料失敗！您嘗試訪問的用戶數據不存在！");
		}
		
		//判斷is_delete是否為1
		if (data.getIsDelete() == 1) {
			//是：拋出：UserNotFoundException
			throw new UserNotFoundException("修改個人資料失敗！您嘗試訪問的用戶數據已經被刪除！");
			
		}
		
		//向參數物件中封裝：
		// - modified_user > data.getUsername()
		user.setModifiedUser(data.getUsername());
		// - modified_time > new Date()
		user.setModifiedTime(new Date());
		//執行修改：gender,phone,email,modified_user,modified_time
		updateInfo(user);
	}
	
	@Override
	public User getById(Integer id) {
	    User data = findById(id);
	    data.setPassword(null);
	    data.setSalt(null);
	    data.setIsDelete(null);
	    return data;
	}
	
	/**
	 * 獲取根據MD5加密的密碼
	 * @param srcPassword 原密碼
	 * @param salt 鹽值
	 * @return 加密後的密碼
	 */
	private String getMd5Password(String srcPassword, String salt) {
		//【注意】以下加密規則是自由設計的
		//---------------------------
		//鹽值 +(拼接) 原密碼 +(拼接) 鹽值
		String str = salt + srcPassword + salt;
		//循環執行10次摘要運算
		for(int i=0; i<10; i++) {
			str = DigestUtils.md5DigestAsHex(str.getBytes()).toUpperCase();
		}
		//返回摘要結果
		return str;
	}

	/**
	 * 新增用戶資料
	 * @param user 用戶資料
	 * @throws InsertException 新增用戶資料異常
	 */
	private void addnew(User user) {
		Integer rows = userMapper.addnew(user);
		if(rows != 1) {
			throw new InsertException("新增用戶資料時，出現未知錯誤！");
		}
	}
	
	/**
	 * 用戶更新密碼
	 * @param uid 用戶的id
	 * @param password 用戶的新密碼
	 * @param modifiedUser 修改執行人
	 * @param modifiedTime 修改時間
	 */
	private void updatePassword(
		Integer uid, String password,
		String modifiedUser, Date modifiedTime) {
		Integer rows = 
				userMapper.updatePassword(uid, password, modifiedUser, modifiedTime);
		if (rows != 1) {
			throw new UpdateException("修改密碼失敗！出現未知錯誤！");
		}
	}
	
	/**
	 * 用戶更新頭像
	 * @param uid 用戶的id
	 * @param avatar 新頭像的路徑
	 * @param modifiedUser 修改執行人
	 * @param modifiedTime 修改時間
	 */
	private void updateAvatar(
		Integer uid, String avatar,
		String modifiedUser, Date modifiedTime) {
		Integer rows = 
				userMapper.updateAvatar(uid, avatar, modifiedUser, modifiedTime);
		if (rows != 1) {
			throw new UpdateException("更新頭像常敗！出現未知錯誤！");
		}
	}
	
	/**
	 * 更新用戶個人資料
	 * @param user 用戶資料
	 */
	private void updateInfo(User user) {
		//執行更新，獲取返回值
		Integer rows = userMapper.updateInfo(user);
		//判斷返回值，出錯時拋出"更新時的未知錯誤"
		if (rows != 1) {
			throw new UpdateException("更新用戶資料時出現未知錯誤！");
		}
	}
	
	/**
	 * 根據用戶帳號查詢用戶資料
	 * @param username 用戶帳號
	 * @return 匹配的用戶數據，如果沒有匹配的數據，則返回null
	 */
	private User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}
	
	/**
	 * 根據用戶id查詢用戶信息
	 * @param id 用戶的id
	 * @return 匹配的用戶數據，如果沒有匹配的數據，則返回null
	 */
	private User findById(Integer id) {
		return userMapper.findById(id);
	}
}





