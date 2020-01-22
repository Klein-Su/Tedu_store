package cn.tedu.store.entity;

public class Manager extends BaseEntity {
	
	private static final long serialVersionUID = 8711884082151345956L;
	
	private Integer id;
	private String name;
	private String username;
	private String password;
	private String salt;
	private Integer roleId;
	private String email;
	private Integer mStaff;
	private Integer mProduct;
	private Integer isDelete;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getmStaff() {
		return mStaff;
	}
	public void setmStaff(Integer mStaff) {
		this.mStaff = mStaff;
	}
	public Integer getmProduct() {
		return mProduct;
	}
	public void setmProduct(Integer mProduct) {
		this.mProduct = mProduct;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Manager [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", salt="
				+ salt + ", roleId=" + roleId + ", email=" + email + ", mStaff=" + mStaff + ", mProduct=" + mProduct
				+ ", isDelete=" + isDelete + "]";
	}
	
}
