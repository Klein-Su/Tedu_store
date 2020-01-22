package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

/**
 * 購物車數據的持久層接口
 * @author Klein
 */
//@Mapper //此處就不各自聲明，直接在起動類 TeduStoreApplication 聲明
public interface CartMapper {
	
	/**
	 * 新增購物車數據
	 * @param cart 購物車數據
	 * @return 受影響的行數
	 */
	Integer addnew(Cart cart);
	
	/**
	 * 更新購物車中商品的數量
	 * @param id 購物車數據的id
	 * @param count 新的數量
	 * @return 受影響的行數
	 */
	Integer updateCount(
		@Param("id") Integer id,
		@Param("count") Integer count);
	
	/**
	 * 根據用戶id和商品id查詢購物車數據
	 * @param uid 用戶id
	 * @param goodsId 商品id
	 * @return 匹配的購物車數據，如果沒有匹配的數據，則返回null
	 */
	Cart findByUidAndGid(
		@Param("uid") Integer uid,
		@Param("goodsId") Long goodsId);
	
	/**
	 * 根據id獲取購車數據
	 * @param id 購物車數據的id
	 * @return 匹配的購物車數據，如果沒有匹配的數據，則返回null
	 */
	Cart findById(Integer id);
	
	/**
	 * 根據用戶id查詢該用戶的購物車數據列表
	 * @param uid 用戶id
	 * @return 該用戶的購物車數據列表
	 */
	List<CartVO> findByUid(Integer uid);
	
	/**
	 * 根據若干個id查詢匹配的購物車數據的集合
	 * @param ids 若干個id
	 * @return 匹配的購物車數據的集合
	 */
	List<CartVO> findByIds(Integer[] ids);
	
	/**
	 * 根據id刪除購物車商品數據
	 * @param id 購物車商品數據的id
	 * @return 受影響的行數
	 */
	Integer deleteById(Integer id);
}

