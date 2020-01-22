package cn.tedu.store.entity;

import java.util.Date;

/**
 * 訂單的實體類
 * @author Klein
 */
public class Order extends BaseEntity {
	
	private static final long serialVersionUID = 774244680746349276L;
	
	private Integer id;
	private Integer uid;
	private String recvName;
	private String recvPhone;
	private String recvCity;
	private String recvDistrict;
	private String recvAddress;
	private String recvZip;
	private Long pay;
	private Date orderTime;
	private Integer status;
	
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
	public String getRecvName() {
		return recvName;
	}
	public void setRecvName(String recvName) {
		this.recvName = recvName;
	}
	public String getRecvPhone() {
		return recvPhone;
	}
	public void setRecvPhone(String recvPhone) {
		this.recvPhone = recvPhone;
	}
	public String getRecvCity() {
		return recvCity;
	}
	public void setRecvCity(String recvCity) {
		this.recvCity = recvCity;
	}
	public String getRecvDistrict() {
		return recvDistrict;
	}
	public void setRecvDistrict(String recvDistrict) {
		this.recvDistrict = recvDistrict;
	}
	public String getRecvAddress() {
		return recvAddress;
	}
	public void setRecvAddress(String recvAddress) {
		this.recvAddress = recvAddress;
	}
	public String getRecvZip() {
		return recvZip;
	}
	public void setRecvZip(String recvZip) {
		this.recvZip = recvZip;
	}
	public Long getPay() {
		return pay;
	}
	public void setPay(Long pay) {
		this.pay = pay;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", uid=" + uid + ", recvName=" + recvName + ", recvPhone=" + recvPhone
				+ ", recvCity=" + recvCity + ", recvDistrict=" + recvDistrict + ", recvAddress=" + recvAddress
				+ ", recvZip=" + recvZip + ", pay=" + pay + ", orderTime=" + orderTime + ", status=" + status + "]";
	}
}


