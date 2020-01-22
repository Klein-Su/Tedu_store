package cn.tedu.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Goods;
import cn.tedu.store.mapper.GoodsMapper;
import cn.tedu.store.service.IGoodsService;
import cn.tedu.store.service.exception.GoodsNotFoundException;
import cn.tedu.store.service.exception.UpdateException;

/**
 * 商品數據的業務層實現類
 * @author Klein
 */
@Service
public class GoodsServiceImpl implements IGoodsService {

	@Autowired
	private GoodsMapper goodsMapper;
	
	@Override
	public Integer getAlls(Long categoryId, String brand,
			Long beginPrice, Long endPrice, String inputValue) {
		return findAlls(categoryId, brand, beginPrice, endPrice, inputValue);
	}
	
	@Override
	public void changeNumById(Long gid) 
			throws GoodsNotFoundException, UpdateException {
		//根據id查詢數據
		Goods data = findById(gid);
		//判斷數據是否為null
		if (data == null) {
			//是：拋出異常： GoodsNotFoundException
			throw new GoodsNotFoundException(
					"嘗試訪問的購物車數據不存在！");
		}
		//判斷商品數量是否大於0
		if(data.getNum() <= 0) {
			//是：拋出異常： UpdateException
			throw new UpdateException(
					"修改商品數量失敗，商品數量有誤！");
		}
		//商品數量-1
		Integer num = data.getNum() - 1;
		//更新商品資料中的數量
		updateNumById(gid, num);
		
	}
	
	@Override
	public Goods getById(Long id) {
		return findById(id);
	}
	
	@Override
	public List<Goods> getByCategoryId(Long categoryId) {
		return findByCategoryId(categoryId);
	}
	
	@Override
	public List<Goods> getData(
			Long categoryId, String brand,
			Long beginPrice, Long endPrice,
			Integer page, Integer pageSize, String inputValue) {
		return findData(
				categoryId, brand, beginPrice, endPrice, page, pageSize, inputValue);
	}
	
	@Override
	public List<Goods> getByCategory(
			Long categoryId, Integer offset, Integer count) {
		return findByCategory(categoryId, offset, count);
	}
	
	@Override
	public List<Goods> getByPriority(Integer count) {
		return findByPriority(count);
	}
	
	@Override
	public List<Goods> getByLaunched(Integer count) {
		return findByLaunched(count);
	}
	
	/**
	 * 根據要搜尋的inputValue資料，找出符合的商品數
	 * @param categoryId 商品分類id
	 * @param brand 商品廠牌名稱
	 * @param beginPrice 高於多少價錢
	 * @param endPrice 小於等於多少價錢
	 * @param inputValue 要搜尋的商品名稱
	 * @return 符合搜尋inputValue值的商品數
	 */
	private Integer findAlls(
			Long categoryId, String brand,
			Long beginPrice, Long endPrice,
			String inputValue) {
		return goodsMapper.findAlls(categoryId, brand, beginPrice, endPrice, inputValue);
	}
	
	/**
	 * 根據商品id修改商品的數量num
	 * @param gid 商品id
	 * @param num 商品數量
	 * @return 受影響的行數
	 */
	private void updateNumById(Long gid, Integer num) {
		Integer rows = goodsMapper.updateNumById(gid, num);
		if (rows != 1) {
			throw new UpdateException(
				"修改商品數量時發生未知的錯誤！");
		}
	}
	
	/**
	 * 根據商品id查詢商品詳情
	 * @param id 商品的id
	 * @return 匹配的商品詳情，如果沒有匹配的數據，則返回null
	 */
	private Goods findById(Long id) {
		return goodsMapper.findById(id);
	}
	
	/**
	 * 根據商品分類category_id查詢商品廠牌
	 * @param categoryId 商品的category_id
	 * @return 匹配的商品廠牌，如果沒有匹配的數據，則返回null
	 */
	private List<Goods> findByCategoryId(Long categoryId) {
		return goodsMapper.findByCategoryId(categoryId);
	}
	
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
	private List<Goods> findData(
			Long categoryId, String brand,
			Long beginPrice, Long endPrice,
			Integer page, Integer pageSize, String inputValue) {
		return goodsMapper.findData(
				categoryId, brand, beginPrice, endPrice, page, pageSize, inputValue);
	}

	/**
	 * 根據父級id獲取子級的商品分類的數據的列表
	 * @param parentId 父級商品分類的id
	 * @return 子級的商品分類的數據的列表
	 */
	private List<Goods> findByCategory(Long categoryId, Integer offset, Integer count) {
		return goodsMapper.findByCategory(categoryId, offset, count);
	}
	
	/**
	 * 根據優先級獲取商品數據的列表
	 * @param count 獲取商品的數量
	 * @return 優先級最高的幾個商品數據的列表
	 */
	private List<Goods> findByPriority(Integer count) {
		return goodsMapper.findByPriority(count);
	}
	
	/**
	 * 根據新上架的時間獲取商品數據的列表
	 * @param count 獲取商品的數量
	 * @return 新上架時間的最高幾個商品數據的列表
	 */
	private List<Goods> findByLaunched(Integer count) {
		return goodsMapper.findByLaunched(count);
	}
}


