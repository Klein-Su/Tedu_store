package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Address;

/**
 * 收貨地址的持久層接口
 * @author Klein
 */
//@Mapper //此處就不各自聲明，直接在起動類 TeduStoreApplication 聲明
public interface AddressMapper {
	/**
	 * 增加新的收貨地址數據
	 * @param address 收貨地址數據
	 * @return 受影響的行數
	 */
	Integer addnew(Address address);
	
	/**
	 * 將某用戶的收貨地址全部設置為非默認
	 * @param uid 用戶id
	 * @return 受影響的行數
	 */
	Integer updateNonDefault(Integer uid);

	/**
	 * 將指定id的收貨地址設置為默認
	 * @param id 收貨地址數據id
	 * @param modifiedUser 修改執行人
	 * @param modifiedTime 修改時間
	 * @return 受影響的行數
	 */
	Integer updateDefault(
		@Param("id")Integer id,
		@Param("modifiedUser") String modifiedUser,
		@Param("modifiedTime") Date modifiedTime);
	
	/**
	 * 修改收貨地址 
	 * @param user 要修改的收貨地址資訊
	 * @return 受影響的行數
	 */
	Integer updateAddress(Address address);

	/**
	 * 根據用戶id獲取該用戶的收貨地址數據的數量
	 * @param uid 用戶id
	 * @return 該用戶的收貨地址數據的數量，如果沒有數據，則返回0
	 */
	Integer getCountByUid(Integer uid);
	
	/**
	 * 根據用戶id獲取某用戶的收貨地址列表
	 * @param uid 用戶id
	 * @return 收貨地址列表
	 */
	List<Address> findByUid(Integer uid);

	/**
	 * 根據id查詢收貨地址數據
	 * @param id 收貨地址的id
	 * @return 匹配的收貨地址數據，如果沒有匹配的數據，則返回null
	 */
	Address findById(Integer id);
	
	/**
	 * 查詢某用戶最後修改的收貨地址信息
	 * @param uid 用戶的id
	 * @return 匹配的數據，如果沒有匹配的數據，則返回null
	 */
	Address findLastModified(Integer uid);
	
	/**
	 * 根據id刪除收貨地址數據
	 * @param id 收貨地址數據的id
	 * @return 受影響的行數
	 */
	Integer deleteById(Integer id);
}



