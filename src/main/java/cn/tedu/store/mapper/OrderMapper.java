package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.vo.OrderVO;

/**
 * 訂單與訂單商品數據的持久層接口
 * @author Klein
 */
//@Mapper //此處就不各自聲明，直接在起動類 TeduStoreApplication 聲明
public interface OrderMapper {
	/**
	 * 插入訂單數據
	 * @param order 訂單數據
	 * @return 受影響的行數
	 */
	Integer insertOrder(Order order);
	
	/**
	 * 插入訂單商品數據
	 * @param orderItem 訂單商品數據
	 * @return 受影響的行數
	 */
	Integer insertOrderItem(OrderItem orderItem);
	
	/**
	 * 根據id查詢訂單詳情
	 * @param id 訂單的id
	 * @return 匹配的訂單詳情，如果沒有匹配的數據，則返回null
	 */
	OrderVO findById(Integer id);
	
	/**
	 * 根據用戶id查詢該用戶的訂單數據列表
	 * @param uid 用戶id
	 * @return 該用戶的訂單數據列表
	 */
	List<OrderVO> findByUid(Integer uid);
}

