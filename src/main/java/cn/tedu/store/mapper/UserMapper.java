package cn.tedu.store.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.User;
/**
 * 處理用戶數據的持久層
 * @author Klein
 */
//@Mapper //此處就不各自聲明，直接在起動類 TeduStoreApplication 聲明
public interface UserMapper {
	/**
	 * 新增用戶資料
	 * @param user 用戶資料
	 * @return 受影響的行數
	 */
	//@Insert("INSERT INTO ...") //考慮SQL語句過長，因此在UserMapper.xml中編寫
	Integer addnew(User user);
	
	/**
	 * 用戶更新密碼
	 * @param uid 用戶的id
	 * @param password 用戶的新密碼
	 * @param modifiedUser 修改執行人
	 * @param modifiedTime 修改時間
	 * @return 受影響的行數
	 */
	Integer updatePassword(
		@Param("uid") Integer uid,
		@Param("password") String password,
		@Param("modifiedUser") String modifiedUser,
		@Param("modifiedTime") Date modifiedTime
	);
	
	/**
	 * 更新用戶頭像
	 * @param uid 用戶的id
	 * @param avatar 頭像的路徑
	 * @param modifiedUser 修改執行人
	 * @param modifiedTime 修改時間
	 * @return 受影響的行數
	 */
	Integer updateAvatar(
		@Param("uid") Integer uid,
		@Param("avatar") String avatar,
		@Param("modifiedUser") String modifiedUser,
		@Param("modifiedTime") Date modifiedTime
	);
	
	/**
	 * 修改用戶資料 (不含用戶名、密碼與頭像)
	 * @param user 用戶資料
	 * @return 受影響的行數
	 */
	Integer updateInfo(User user);
	
	/**
	 * 根據用戶帳號查詢用戶資料
	 * @param username 用戶帳號
	 * @return 匹配的用戶數據，如果沒有匹配的數據，則返回null
	 */
	User findByUsername(String username);
	
	/**
	 * 根據用戶id查詢用戶信息
	 * @param id 用戶的id
	 * @return 匹配的用戶數據，如果沒有匹配的數據，則返回null
	 */
	User findById(Integer id);
}

