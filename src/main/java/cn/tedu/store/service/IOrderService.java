package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Order;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.vo.OrderVO;

/**
 * 訂單與訂單商品數據的業務層接口
 * @author Klein
 */
public interface IOrderService {
	
	/**
	 * 創建訂單
	 * @param uid 當前登入的用戶id
	 * @param username 當前登入的用戶名
	 * @param addressId 訂單所選擇的收貨地址id
	 * @param cartIds 訂單中的商品在購物車中的數據id
	 * @return 返回成功創建的訂單
	 */
	Order createOrder(
		Integer uid, String username,
		Integer addressId,
		Integer[] cartIds) throws InsertException;
	
	/**
	 * 根據id獲取Order資料
	 * @param id 訂單的id
	 * @param uid 使用者的id
	 * @return 符合的訂單資料
	 */
	Order getByIdAndUid(Integer id, Integer uid);
	
	/**
	 * 根據id查詢訂單詳情
	 * @param id 訂單的id
	 * @return 匹配的訂單詳情，如果沒有匹配的數據，則返回null
	 */
	OrderVO getById(Integer id);
	
	/**
	 * 根據用戶id查詢該用戶的訂單數據列表
	 * @param uid 用戶id
	 * @return 該用戶的訂單數據列表
	 */
	List<OrderVO> getByUid(Integer uid);
}
