package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.tedu.store.entity.Manager;
import cn.tedu.store.mapper.ManagerMapper;
import cn.tedu.store.service.IManagerService;
import cn.tedu.store.service.exception.AccessDeniedException;
import cn.tedu.store.service.exception.DuplicateKeyException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.ManagerNotFoundException;
import cn.tedu.store.service.exception.PasswordNotMatchException;
import cn.tedu.store.service.exception.UpdateException;
import cn.tedu.store.vo.ManagerVO;

/**
 * 處理後台人員資料的業務層實現類
 * @author Klein
 */
@Service
public class ManagerServiceImpl implements IManagerService {

	@Autowired
	private ManagerMapper managerMapper;
	
	@Override
	public Integer getAlls(String inputValue) {
		return findAlls(inputValue);
	}
	
	@Override //後台人員註冊
	public Manager reg(Manager manager) 
		throws DuplicateKeyException, InsertException {
		//根據嘗試註冊的後台人員帳號查詢後台人員數據
		Manager data = findByUsername(manager.getUsername());
		//判斷查詢到的數據是否為null
		if(data == null) {
			//是：後台人員帳號不存在，允許註冊，則處理密碼加密
					
			//【補充非後台人員提交的數據】
			//4項日誌
			Date now = new Date();
			manager.setCreatedUser(manager.getUsername());
			manager.setCreatedTime(now);
			manager.setModifiedUser(manager.getUsername());
			manager.setModifiedTime(now);
					
			//加密-1：獲取隨機的UUID作為鹽值
			String salt = UUID.randomUUID().toString().toUpperCase();
			//加密-2：獲取後台人員提交的原始密碼
			String srcPassword = manager.getPassword();
			//加密-3：基於原始密碼和鹽值執行加密，獲取通過MD5加密的密碼
			String md5Password = getMd5Password(srcPassword, salt);
			//加密-4：將加密後的密碼封裝在後台人員物件中
			manager.setPassword(md5Password);
			//加密-5：將鹽值封裝在後台人員物件中
			manager.setSalt(salt);
			//執行註冊
			addnew(manager);
			//-- 返回註冊的後台人員對象
			return manager;
		} else {
			//否：後台人員帳號已被佔用，拋出DuplicateKeyException異常
			throw new DuplicateKeyException(
					"註冊失敗！嘗試註冊的帳號("+manager.getUsername()+")已經被佔用！");
		}
	}
	
	@Override //刪除人員(軟刪除)
	public void changeById(Integer id, Integer mid)
		throws ManagerNotFoundException, 
			AccessDeniedException, UpdateException {
		//根據登入者的id獲取該登入人員資訊
		Manager loginData = findById(mid);
		//根據要刪除人員id獲取該管理人員資訊
		Manager delData = findById(id);
		//判斷是否查詢到要刪除人員的數據
		if (delData == null) {
			throw new ManagerNotFoundException(
				"刪除人員失敗！嘗試訪問的人員數據不存在！");
		}
		if (loginData.getRoleId() > delData.getRoleId()) {
			throw new AccessDeniedException(
				"刪除人員失敗！訪問權限驗證不通過！");
		}
		//判斷該登入人員是否有刪除權限功能
		if ((loginData.getmStaff() & 4) != 4) {
			throw new AccessDeniedException(
				"刪除人員失敗！您無刪除權限功能！");
		}
		//最後修改人及時間
		String createdUser = loginData.getUsername();
		Date createdTime = new Date();
		//執行刪除
		deleteById(id, createdUser, createdTime);
	}

	@Override //後台人員登入
	public Manager login(String username, String password) 
		throws ManagerNotFoundException, PasswordNotMatchException {
		//根據參數username查詢後台人員數據
		Manager data = findByUsername(username);
		//判斷後台人員數據是否為null
		if (data == null) {
			//是：為null，後台人員帳號不存在，則拋出ManagerNotFoundException
			throw new ManagerNotFoundException("登入失敗！您嘗試登入的帳號不存在！");
		}
		//否：非null，根據帳號找到了數據，取出鹽值
		String salt = data.getSalt();
		//對參數password執行加密
		String md5Password = getMd5Password(password, salt);
		//判斷密碼是否匹配
		if(data.getPassword().equals(md5Password)) {
			//是：匹配，密碼正確，則判斷是否被刪除
			if(data.getIsDelete() == 1) {
				//是：已被刪除，則拋出ManagerNotFoundException
				throw new ManagerNotFoundException("登入失敗！您嘗試登入的帳號已被刪除");
			}
			//否：沒被刪除，則登入成功，將第1步查詢的後台人員數據中的鹽值和密碼設置為null，再返回後台人員數據
			data.setPassword(null);
			data.setSalt(null);
			//返回後台人員數據
			return data;
		} else {
			//否：不匹配，密碼錯誤，則拋出PasswordNotMatchException
			throw new PasswordNotMatchException("登入失敗！密碼錯誤！");
		}
	}

	@Override
	public Manager getById(Integer id) {
		Manager data = findById(id);
	    data.setPassword(null);
	    data.setSalt(null);
	    data.setIsDelete(null);
	    return data;
	}
	
	@Override
	public List<ManagerVO> getList(
		Integer page, Integer pageSize, String inputValue) {
		return findList(page, pageSize, inputValue);
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
	 * 新增後台人員資料
	 * @param manager 後台人員資料
	 * @throws InsertException 新增用戶資料異常
	 */
	private void addnew(Manager manager) {
		Integer rows = managerMapper.addnew(manager);
		if(rows != 1) {
			throw new InsertException("新增後台人員資料時，出現未知錯誤！");
		}
	}
	
	/**
	 * 根據要搜尋的inputValue資料，找出符合的人員數
	 * @param inputValue
	 * @return 符合搜尋inputValue值的人員數
	 */
	private Integer findAlls(String inputValue) {
		return managerMapper.findAlls(inputValue);
	}
	
	/**
	 * 停權(軟刪除)後台人員資料
	 * @param id 後台人員的id
	 * @return 受影響的行數
	 */
	private void deleteById(Integer id, String createdUser, Date createdTime) {
		Integer rows = managerMapper.deleteById(id, createdUser, createdTime);
		if (rows != 1) {
			throw new UpdateException(
				"刪除人員資料時發生未知的錯誤！");
		}
	}

	/**
	 * 根據後台人員帳號查詢後台人員資料
	 * @param username 後台人員帳號
	 * @return 匹配的後台人員數據，如果沒有匹配的數據，則返回null
	 */
	private Manager findByUsername(String username) {
		return managerMapper.findByUsername(username);
	}
	
	/**
	 * 根據後台人員id查詢後台人員信息
	 * @param id 後台人員的id
	 * @return 匹配的後台人員數據，如果沒有匹配的數據，則返回null
	 */
	private Manager findById(Integer id) {
		return managerMapper.findById(id);
	}
	
	/**
	 * 根據要搜尋的inputValue資料及目前頁數跟每頁筆數，找出符合的人員清單列表
	 * @param page 第幾頁
	 * @param pageSize 每頁顯示幾筆資料
	 * @param inputValue 要搜尋的inputValue值
	 * @return 符合inputVlaue值的人員列表數據
	 */
	private List<ManagerVO> findList(
		Integer page, Integer pageSize, String inputValue) {
		return managerMapper.findList(page, pageSize, inputValue);
	}
}
