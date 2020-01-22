package cn.tedu.store.vo;

import java.io.Serializable;
import java.util.List;

import cn.tedu.store.entity.Goods;

/**
 * 收藏與商品數據的Value Object VO類
 * @author Klein
 */
public class FavoritesVO implements Serializable {

	private static final long serialVersionUID = 8509111119597396663L;

	private Integer id;
	private Integer uid;
	private Long gid;
	private List<Goods> goods;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Long getGid() {
		return gid;
	}
	public void setGid(Long gid) {
		this.gid = gid;
	}
	public List<Goods> getGoods() {
		return goods;
	}
	public void setGoods(List<Goods> goods) {
		this.goods = goods;
	}
	@Override
	public String toString() {
		return "FavoritesVO [id=" + id + ", uid=" + uid + ", gid=" + gid + ", goods=" + goods + "]";
	}
}
