package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Favorites;
import cn.tedu.store.mapper.FavoritesMapper;
import cn.tedu.store.service.IFavoritesService;
import cn.tedu.store.service.exception.AccessDeniedException;
import cn.tedu.store.service.exception.CartNotFoundException;
import cn.tedu.store.service.exception.DeleteException;
import cn.tedu.store.service.exception.DuplicateKeyException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.vo.FavoritesVO;

/**
 * 處理收藏資料的業務層實現類
 * @author Klein
 */
@Service
public class FavoritesServiceIml implements IFavoritesService {

	@Autowired
	private FavoritesMapper favoritesMapper;
	
	@Override
	public void addToFavorites(String username, Favorites favorites) 
		throws DuplicateKeyException, InsertException {
		// 獲取當前操作時間
		Date now = new Date();
		// 根據參數cart中的uid和gid查詢數據
		Integer uid = favorites.getUid();
		Long gid = favorites.getGid();
		//獲取是否有同樣的數據
		Favorites data = findByUidAndGid(uid, gid);
		// 判斷查詢結果是否為null
		if (data == null) {
			// 是：該用戶尚未在購物車中添加該商品，則執行新增
			favorites.setCreatedUser(username);
			favorites.setCreatedTime(now);
			favorites.setModifiedUser(username);
			favorites.setModifiedTime(now);
			addnew(favorites);
		} else {
			//否：該收藏已儲存該商品id數據，拋出DuplicateKeyException異常
			throw new DuplicateKeyException(
				"收藏失敗！該商品已經被收藏！");
		}
	}
	
	@Override
	public Integer getAllByUid(Integer uid) {
		return findAllByUid(uid);
	}
	
	@Override
	public Favorites getByUidAndGid(Integer uid, Long goodsId) {
		return findByUidAndGid(uid, goodsId);
	}
	
	@Override
	public List<FavoritesVO> getByUid(Integer uid, Integer page) {
		return findByUid(uid, page);
	}

	@Override
	public void delete(Integer uid, Integer id) 
		throws DeleteException {
		//根據id查詢數據
		Favorites data = findById(id);
		//判斷數據是否為null
		if (data == null) {
			//是：拋出異常： CartNotFoundException
			throw new CartNotFoundException(
				"嘗試訪問的收藏數據不存在！");
		}
						
		//判斷數據歸屬是否不匹配
		if (!data.getUid().equals(uid)) {
			//是：拋出異常： AccessDeniedException
			throw new AccessDeniedException(
				"刪除收藏失敗！訪問數據權限驗證不通過！");
		}
			    
		// 執行刪除
		deleteById(id);
	}

	/**
	 * 新增收藏數據
	 * @param favorites 收藏數據
	 * @return 受影響的行數
	 */
	private void addnew(Favorites favorites) {
		Integer rows = favoritesMapper.addnew(favorites);
		if(rows != 1) {
			throw new InsertException("新增收藏資料時，出現未知錯誤！");
		}
	}
	
	/**
	 * 根據用戶id查詢所有收藏數據的數量
	 * @param uid 用戶id
	 * @return 匹配的收藏數據數量
	 */
	private Integer findAllByUid(Integer uid) {
		return favoritesMapper.findAllByUid(uid);
	}
	
	/**
	 * 根據用戶id和商品id查詢收藏數據
	 * @param uid 用戶id
	 * @param goodsId 商品id
	 * @return 匹配的收藏數據，如果沒有匹配的數據，則返回null
	 */
	private Favorites findByUidAndGid(
		@Param("uid") Integer uid,
		@Param("goodsId") Long goodsId) {
		return favoritesMapper.findByUidAndGid(uid, goodsId);
	}
	
	/**
	 * 根據id獲取收藏數據
	 * @param id 收藏數據的id
	 * @return 匹配的收藏數據，如果沒有匹配的數據，則返回null
	 */
	private Favorites findById(Integer id) {
		return favoritesMapper.findById(id);
	}
	
	/**
	 * 根據用戶id查詢該用戶的收藏數據列表
	 * @param uid 用戶id
	 * @param page 查詢的頁數
	 * @return 該用戶的收藏數據列表
	 */
	private List<FavoritesVO> findByUid(Integer uid, Integer page) {
		return favoritesMapper.findByUid(uid, page);
	}
	
	/**
	 * 根據id刪除收藏數據
	 * @param id 收藏數據的id
	 * @return 受影響的行數
	 */
	private void deleteById(Integer id) {
		Integer rows = favoritesMapper.deleteById(id);
		if (rows != 1) {
			throw new DeleteException(
				"刪除購物車商品時出現未知的錯誤！");
		}
	}
}
