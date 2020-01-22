package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Manager;
import cn.tedu.store.vo.ManagerVO;

/**
 * 處理後台人員數據的持久層
 * @author Klein
 */
//@Mapper //此處就不各自聲明，直接在起動類 TeduStoreApplication 聲明
public interface ManagerMapper {
	
	/**
	 * 新增後台人員資料
	 * @param manager 後台人員資料
	 * @return 受影響的行數
	 */
	//@Insert("INSERT INTO ...") //考慮SQL語句過長，因此在UserMapper.xml中編寫
	Integer addnew(Manager manager);
	
	/**
	 * 停權(軟刪除)後台人員資料
	 * @param id 後台人員的id
	 * @param modifiedUser 最後修改人
	 * @param modifiedTime 最後修改時間
	 * @return 受影響的行數
	 */
	Integer deleteById(
		@Param("id")Integer id,
		@Param("modifiedUser") String modifiedUser,
		@Param("modifiedTime") Date modifiedTime);
	
	/**
	 * 根據要搜尋的inputValue資料，找出符合的人員數
	 * @param inputValue
	 * @return 符合搜尋inputValue值的人員數
	 */
	Integer findAlls(String inputValue);
	
	/**
	 * 根據後台人員帳號查詢後台人員資料
	 * @param username 後台人員帳號
	 * @return 匹配的後台人員數據，如果沒有匹配的數據，則返回null
	 */
	Manager findByUsername(String username);
	
	/**
	 * 根據後台人員id查詢後台人員信息
	 * @param id 後台人員的id
	 * @return 匹配的後台人員數據，如果沒有匹配的數據，則返回null
	 */
	Manager findById(Integer id);
	
	/**
	 * 根據要搜尋的inputValue資料及目前頁數跟每頁筆數，找出符合的人員清單列表
	 * @param page 第幾頁
	 * @param pageSize 每頁顯示幾筆資料
	 * @param inputValue 要搜尋的inputValue值
	 * @return 該後台人員看觀看的人員列表數據
	 */
	List<ManagerVO> findList(
		@Param("page") Integer page,
		@Param("pageSize") Integer pageSize,
		@Param("inputValue") String inputValue);

}
