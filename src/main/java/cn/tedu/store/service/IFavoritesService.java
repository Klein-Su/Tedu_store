package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Favorites;
import cn.tedu.store.service.exception.DeleteException;
import cn.tedu.store.service.exception.DuplicateKeyException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.vo.FavoritesVO;

/**
 * 收藏與商品數據的業務層接口
 * @author Klein
 */
public interface IFavoritesService {
	
	/**
	 * 新增收藏
	 * @param username 當前操作的執行人
	 * @param favorites 要新增的收藏數據
	 * @throws DuplicateKeyException 已有重覆的收藏商品數據
	 * @throws InsertException 插入數據出現未知錯誤的異常
	 */
	void addToFavorites(String username, Favorites favorites) 
		throws DuplicateKeyException, InsertException;
	
	/**
	 * 根據用戶id查詢所有收藏數據的數量
	 * @param uid 用戶id
	 * @return 匹配的收藏數據數量
	 */
	Integer getAllByUid(Integer uid);
	
	/**
	 * 根據用戶id和商品id查詢收藏數據
	 * @param uid 用戶id
	 * @param goodsId 商品id
	 * @return 匹配的收藏數據，如果沒有匹配的數據，則返回null
	 */
	Favorites getByUidAndGid(Integer uid, Long goodsId);
	
	/**
	 * 根據用戶id查詢該用戶的收藏數據列表
	 * @param uid 用戶id
	 * @param page 查詢的頁數
	 * @return 該用戶的收藏數據列表
	 */
	List<FavoritesVO> getByUid(Integer uid, Integer page);
	
	/**
	 * 根據id刪除收藏數據
	 * @param uid 收貨地址歸屬的用戶的id
	 * @param id 收貨地址數據的id
	 * @throws DeleteException 刪除收貨地址數據時的異常
	 */
	void delete(Integer uid, Integer id)
		throws DeleteException;
}
