package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.exception.AccessDeniedException;
import cn.tedu.store.service.exception.CartNotFoundException;
import cn.tedu.store.service.exception.DeleteException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.UpdateException;
import cn.tedu.store.vo.CartVO;

/**
 * 購物車數據的業務層接口
 * @author Klein
 */
public interface ICartService {
	
	/**
	 * 將商品添加到購物車
	 * @param username 當前操作的執行人
	 * @param cart 購物車數據
	 * @throws InsertException 新增插入數據異常
	 * @throws UpdateException 更新數據異常
	 */
	void addToCart(String username, Cart cart) 
		throws InsertException, UpdateException;
	
	/**
	 * 增加購物車中商品的數量
	 * @param id 購物車數據的id
	 * @param uid 當前用戶的id
	 * @throws CartNotFoundException 購物車數據不存在的異常
	 * @throws AccessDeniedException 訪問權限受到限制的異常
	 * @throws UpdateException 更新數據異常
	 */
	void addCount(Integer id, Integer uid) 
		throws CartNotFoundException, 
			AccessDeniedException, 
				UpdateException;
	
	/**
	 * 減少購物車中商品的數量
	 * @param id 購物車數據的id
	 * @param uid 當前用戶的id
	 * @throws CartNotFoundException 購物車數據不存在的異常
	 * @throws AccessDeniedException 訪問權限受到限制的異常
	 * @throws UpdateException 更新數據異常
	 */
	void reduceCount(Integer id, Integer uid) 
		throws CartNotFoundException, 
			AccessDeniedException, 
				UpdateException;
	
	/**
	 * 直接修改更新購物車中商品的數量
	 * @param id 購物車數據的id
	 * @param uid 當前用戶的id
	 * @param count 要更新該商品的數量
	 * @throws CartNotFoundException 購物車數據不存在的異常
	 * @throws AccessDeniedException 訪問權限受到限制的異常
	 * @throws UpdateException 更新數據異常
	 */
	void modifiedCount(Integer id, Integer uid, Integer count) 
		throws CartNotFoundException, 
			AccessDeniedException, 
				UpdateException;
	
	/**
	 * 根據用戶id查詢該用戶的購物車數據列表
	 * @param uid 用戶id
	 * @return 該用戶的購物車數據列表
	 */
	List<CartVO> getByUid(Integer uid);
	
	/**
	 * 根據若干個id查詢匹配的購物車數據的集合
	 * @param ids 若干個id
	 * @return 匹配的購物車數據的集合
	 */
	List<CartVO> getByIds(Integer[] ids);
	
	/**
	 * 根據id刪除購物車商品
	 * @param uid 購物車商品歸屬的用戶的id
	 * @param id 購物車商品數據的id
	 * @throws DeleteException 刪除購物車商品數據時的異常
	 */
	void delete(Integer uid, Integer id)
		throws DeleteException;
}

