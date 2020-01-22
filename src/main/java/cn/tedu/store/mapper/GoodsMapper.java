package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Goods;

/**
 * 商品數據的持久層接口
 * @author Klein
 */
//@Mapper //此處就不各自聲明，直接在起動類 TeduStoreApplication 聲明
public interface GoodsMapper {
	
	/**
	 * 根據要搜尋的inputValue資料，找出符合的商品數
	 * @param categoryId 商品分類id
	 * @param brand 商品廠牌名稱
	 * @param beginPrice 高於多少價錢
	 * @param endPrice 小於等於多少價錢
	 * @param inputValue 要搜尋的商品名稱
	 * @return 符合搜尋inputValue值的商品數
	 */
	Integer findAlls(
		@Param("categoryId") Long categoryId,
		@Param("brand") String brand,
		@Param("beginPrice") Long beginPrice,
		@Param("endPrice") Long endPrice,
		@Param("inputValue") String inputValue);
	
	/**
	 * 根據商品id修改商品的數量num
	 * @param gid 商品id
	 * @param num 商品數量
	 * @return 受影響的行數
	 */
	Integer updateNumById(Long gid,Integer num);
	
	/**
	 * 根據商品id查詢商品詳情
	 * @param id 商品的id
	 * @return 匹配的商品詳情，如果沒有匹配的數據，則返回null
	 */
	Goods findById(Long id);
	
	/**
	 * 根據商品分類category_id查詢商品廠牌
	 * @param categoryId 商品的category_id
	 * @return 匹配的商品廠牌，如果沒有匹配的數據，則返回null
	 */
	List<Goods> findByCategoryId(Long categoryId);
	
	/**
	 * 根據要搜尋的inputValue資料及目前頁數跟每頁筆數，找出符合的商品數據
	 * @param categoryId 商品分類id
	 * @param brand 商品廠牌名稱
	 * @param beginPrice 高於多少價錢
	 * @param endPrice 小於等於多少價錢
	 * @param page 第幾頁
	 * @param pageSize 每頁顯示幾筆資料
	 * @param inputValue 要搜尋的inputValue值
	 * @return 符合inputVlaue值的商品數據
	 */
	List<Goods> findData(
		@Param("categoryId") Long categoryId,
		@Param("brand") String brand,
		@Param("beginPrice") Long beginPrice,
		@Param("endPrice") Long endPrice,
		@Param("page") Integer page,
		@Param("pageSize") Integer pageSize,
		@Param("inputValue") String inputValue);
	
	/**
	 * 根據商品分類id，查詢商品列表
	 * @param categoryId 商品分類id
	 * @param offset 遍移量，跳過幾筆資料 
	 * @param count 一次顯示幾筆數據，獲取數據的最大數量
	 * @return 匹配的商品列表
	 */
	List<Goods> findByCategory(
		@Param("categoryId") Long categoryId, 
		@Param("offset") Integer offset, 
		@Param("count") Integer count);
	
	/**
	 * 根據優先級獲取商品數據的列表
	 * @param count 獲取商品的數量
	 * @return 優先級最高的幾個商品數據的列表
	 */
	List<Goods> findByPriority(Integer count);
	
	/**
	 * 根據新上架的時間獲取商品數據的列表
	 * @param count 獲取商品的數量
	 * @return 新上架時間的最高幾個商品數據的列表
	 */
	List<Goods> findByLaunched(Integer count);
}



