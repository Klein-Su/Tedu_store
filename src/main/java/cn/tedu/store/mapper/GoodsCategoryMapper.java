package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.GoodsCategory;

/**
 * 商品分類數據的持久層接口
 * @author Klein
 */
public interface GoodsCategoryMapper {
	
	/**
	 * 根據父級id獲取子級的商品分類的數據的列表
	 * @param parentId 父級商品分類的id
	 * @return 子級的商品分類的數據的列表
	 */
	List<GoodsCategory> findByParent(Long parentId);
}

