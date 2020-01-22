package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.exception.AddressNotFoundException;
import cn.tedu.store.service.exception.DeleteException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.UpdateException;

/**
 * 收貨地址的業務層接口
 * @author Klein
 */
public interface IAddressService {
	
	/**
	 * 創建新收貨地址
	 * @param username 當前執行人
	 * @param address 收貨地址信息
	 * @return 成功新增的收貨地址數據
	 * @throws InsertException 插入數據出現未知錯誤的異常
	 */
	Address create(String username,  Address address) 
			throws InsertException;
	
	/**
	 * 設置默認收貨地址
	 * @param uid 收貨地址歸屬的用戶的id
	 * @param id 將要設置為默認收貨地址的數據的id
	 * @param modifiedUser 最後修改者
	 */
	void setDefault(Integer uid, Integer id, String modifiedUser);
	
	/**
	 * 修改收貨地址
	 * @param uid 收貨地址歸屬的用戶的id
	 * @param address 要修改的收貨地址資訊
	 * @throws AddressNotFoundException 收貨地址不存在的異常
	 * @throws UpdateException 修改收貨地址出現未知錯誤的異常
	 */
	void changeAddress(Integer uid, Address address) 
		throws AddressNotFoundException, UpdateException;
	
	/**
	 * 根據用戶id獲取某用戶的收貨地址列表
	 * @param uid 用戶id
	 * @return 收貨地址列表
	 */
	List<Address> getListByUid(Integer uid);
	
	/**
	 * 根據id查詢收貨地址數據
	 * @param id 收貨地址的id
	 * @return 匹配的收貨地址數據，如果沒有匹配的數據，則返回null
	 */
	Address getById(Integer id);
	
	/**
	 * 根據id刪除收貨地址
	 * @param uid 收貨地址歸屬的用戶的id
	 * @param id 收貨地址數據的id
	 * @param modifiedUser 最後修改人
	 * @throws DeleteException 刪除收貨地址數據時的異常
	 */
	void delete(Integer uid, Integer id, String modifiedUser)
		throws DeleteException;
}

