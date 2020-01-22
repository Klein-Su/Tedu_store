package cn.tedu.store.vo;

import java.io.Serializable;
import java.util.List;

import cn.tedu.store.entity.OrderItem;

/**
 * 訂單與訂單商品數據的Value Object VO類
 * @author Klein
 */
public class OrderVO implements Serializable {
	
	private static final long serialVersionUID = 2577439764870595194L;
	
	private Integer id;
	private Integer uid;
	private String recvName;
	private String recvPhone;
	private String recvCity;
	private String recvDistrict;
	private String recvAddress;
	private String recvZip;
	private String orderTime;
	private Long pay;
	private Integer status;
	private List<OrderItem> items;
	
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
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public Long getPay() {
		return pay;
	}
	public void setPay(Long pay) {
		this.pay = pay;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "OrderVO [id=" + id + ", uid=" + uid + ", recvName=" + recvName + ", recvPhone=" + recvPhone
				+ ", recvCity=" + recvCity + ", recvDistrict=" + recvDistrict + ", recvAddress=" + recvAddress
				+ ", recvZip=" + recvZip + ", orderTime=" + orderTime + ", pay=" + pay + ", status=" + status
				+ ", items=" + items + "]";
	}
}

