package cn.tedu.store.vo;

import java.io.Serializable;

/**
 * 管理人員與管理人員職稱的Value Object VO類
 * @author Klein
 */
public class ManagerVO implements Serializable {
	
	private static final long serialVersionUID = -4146733069535511142L;
	
	private Integer id;
	private String name;
	private String username;
	private String roleId;
	private String email;
	private String roleName;
	
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
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Override
	public String toString() {
		return "ManagerVO [id=" + id + ", name=" + name + ", username=" + username + ", roleId=" + roleId + ", email="
				+ email + ", roleName=" + roleName + "]";
	}
	
}
