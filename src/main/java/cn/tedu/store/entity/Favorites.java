package cn.tedu.store.entity;

/**
 * 收藏數據的實體類
 * @author Klein
 */
public class Favorites extends BaseEntity {
	
	private static final long serialVersionUID = -6436436470766591370L;
	
	private Integer id;
	private Integer uid;
	private Long gid;
	
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
	@Override
	public String toString() {
		return "Favorites [id=" + id + ", uid=" + uid + ", gid=" + gid + "]";
	}
	
}