package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Favorites;
import cn.tedu.store.vo.FavoritesVO;

/**
 * 收藏的持久層接口
 * @author Klein
 */
//@Mapper //此處就不各自聲明，直接在起動類 TeduStoreApplication 聲明
public interface FavoritesMapper {
	
	/**
	 * 新增收藏數據
	 * @param favorites 收藏數據
	 * @return 受影響的行數
	 */
	Integer addnew(Favorites favorites);
	
	/**
	 * 根據用戶id查詢所有收藏數據的數量
	 * @param uid 用戶id
	 * @return 匹配的收藏數據數量
	 */
	Integer findAllByUid(Integer uid);
	
	/**
	 * 根據用戶id和商品id查詢收藏數據
	 * @param uid 用戶id
	 * @param goodsId 商品id
	 * @return 匹配的收藏數據，如果沒有匹配的數據，則返回null
	 */
	Favorites findByUidAndGid(
		@Param("uid") Integer uid,
		@Param("goodsId") Long goodsId);
	
	/**
	 * 根據id獲取收藏數據
	 * @param id 收藏數據的id
	 * @return 匹配的收藏數據，如果沒有匹配的數據，則返回null
	 */
	Favorites findById(Integer id);
	
	/**
	 * 根據用戶id查詢該用戶的收藏數據列表
	 * @param uid 用戶id
	 * @param page 查詢的頁數
	 * @return 該用戶的收藏數據列表
	 */
	List<FavoritesVO> findByUid(
		@Param("uid") Integer uid, 
		@Param("page") Integer page);
	
	/**
	 * 根據id刪除收藏數據
	 * @param id 收藏數據的id
	 * @return 受影響的行數
	 */
	Integer deleteById(Integer id);
}



