package cn.tedu.store.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.mapper.OrderMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.IGoodsService;
import cn.tedu.store.service.IOrderService;
import cn.tedu.store.service.exception.AccessDeniedException;
import cn.tedu.store.service.exception.AddressNotFoundException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.OrderNotFoundException;
import cn.tedu.store.vo.CartVO;
import cn.tedu.store.vo.OrderVO;

/**
 * 訂單與訂單商品數據的業務層實現類
 * @author Klein
 */
@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private IAddressService addressService;
	
	@Autowired
	private ICartService cartService;
	
	@Autowired
	private IGoodsService goodsService;
	
	@Autowired
	private OrderMapper orderMapper; //本身的持久層物件
	
	@Override
	@Transactional
	public Order createOrder(
		Integer uid, String username, 
		Integer addressId, Integer[] cartIds) {
		//創建Date物件
		Date now = new Date();
		
		//聲明pay變數
		Long pay = 0L;
		//List<CartVO> cartService.getByIds(ids);
		List<CartVO> carts = cartService.getByIds(cartIds);
		//創建List<OrderItem> orderItems
		List<OrderItem> orderItems = new ArrayList<>();
		//遍歷集合
		for (CartVO cartVO : carts) {
			//計算總價pay
			pay += cartVO.getNewPrice() * cartVO.getCount();
			// --創建OrderItem
			OrderItem orderItem = new OrderItem();
			// --item屬性：goods_5 - ok
			orderItem.setGoodsId(cartVO.getGid());
			orderItem.setGoodsImage(cartVO.getImage());
			orderItem.setGoodsTitle(cartVO.getTitle());
			orderItem.setGoodsCount(cartVO.getCount());
			orderItem.setGoodsPrice(cartVO.getNewPrice());
			// --item屬性：4個日誌 - ok
			orderItem.setCreatedUser(username);
			orderItem.setCreatedTime(now);
			orderItem.setModifiedUser(username);
			orderItem.setModifiedTime(now);
			//將item添加到集合中
			orderItems.add(orderItem);
		}
		
		//創建Order物件
		Order order = new Order();
		//order屬性：uid - ok
		order.setUid(uid);
		//order屬性：pay - ok
		order.setPay(pay);
		
		//通過addressService.getById()得到收貨地址數據
		Address address = addressService.getById(addressId);
		//判斷是否查詢到address數據
		if (address == null) {
			throw new AddressNotFoundException(
				"創建訂單失敗！收貨地址資訊有誤，請重新刷新頁面再繼續！");
		}
		//order屬性：recv_4 - ok
		order.setRecvName(address.getName());
		order.setRecvPhone(address.getPhone());
		order.setRecvCity(address.getCity());
		order.setRecvDistrict(address.getDistrict());
		order.setRecvZip(address.getZip());
		order.setRecvAddress(address.getAddress());
		
		//order屬性：order_time - ok
		order.setOrderTime(now);
		//order屬性：status - ok，值為0
		order.setStatus(0);
		
		//order屬性：4個日誌
		order.setCreatedUser(username);
		order.setCreatedTime(now);
		order.setModifiedUser(username);
		order.setModifiedTime(now);
		
		//插入訂單數據並獲取oid：insertOrder(order);
		insertOrder(order);
		
		//遍歷orderItems
		for (OrderItem orderItem : orderItems) {
			//item屬性：oid - ok
			orderItem.setOid(order.getId());
			//插入訂單商品數據：insertOrderItem(orderItem);
			insertOrderItem(orderItem);
		}
		
		// 刪除購物車中對應的數據
		for (Integer cartId : cartIds) {
			//刪除購物車中的資料
			cartService.delete(uid, cartId);
		}
		
		// 更新商品庫存
		for (CartVO cartVO : carts) {
			//獲取商品id
			Long gid = cartVO.getGid();
			goodsService.changeNumById(gid);
		}
		
		//返回 Order 物件
		return order;
	}
	
	@Override
	public Order getByIdAndUid(Integer id, Integer uid) {
		//創建order物件
		Order order = new Order();
		//根據商品id獲取OrderVO物件
		OrderVO orderVO = findById(id);
		
		//判斷orderVO物件是否存在
		if (orderVO == null) {
			throw new OrderNotFoundException(
				"獲取訂單失敗！找不到訂單資訊！");
		}
		
		//判斷訂單是否為該登入使用者的
		if (orderVO.getUid() != uid) {
			throw new AccessDeniedException(
				"獲取訂單失敗！訪問數據權限驗證不通過！");
		}
		
		//判斷訂單狀態 status: 0:待付款(未付款), 1:待出貨(已付款), 2:已出貨, 3:已到貨, 4:已收貨, 5:待評價, 6:退貨退款 
		if (orderVO.getStatus() != 0) {
			throw new AccessDeniedException(
				"獲取訂單失敗！訂單已完成付款且處理中！");
		}
		
		//封裝order資訊
		order.setId(id);
		order.setPay(orderVO.getPay());
		
		//返回order物件(訂單資料)
		return order;
	}
	
	@Override
	public OrderVO getById(Integer id) {
		return findById(id);
	}
	
	@Override
	public List<OrderVO> getByUid(Integer uid) {
		return findByUid(uid);
	}

	/**
	 * 插入訂單數據
	 * @param order 訂單數據
	 */
	private void insertOrder(Order order) {
		Integer rows = orderMapper.insertOrder(order);
		if (rows != 1) {
			throw new InsertException(
				"插入訂單數據時發生未知錯誤！");
		}
	}
	
	/**
	 * 插入訂單商品數據
	 * @param orderItem 訂單商品數據
	 */
	private void insertOrderItem(OrderItem orderItem) {
		Integer rows = orderMapper.insertOrderItem(orderItem);
		if (rows != 1) {
			throw new InsertException(
				"插入訂單商品數據時發生未知錯誤！");
		}
	}
	
	/**
	 * 根據id查詢訂單詳情
	 * @param id 訂單的id
	 * @return 匹配的訂單詳情，如果沒有匹配的數據，則返回null
	 */
	private OrderVO findById(Integer id) {
		return orderMapper.findById(id);
	}

	/**
	 * 根據用戶id查詢該用戶的訂單數據列表
	 * @param uid 用戶id
	 * @return 該用戶的訂單數據列表
	 */
	private List<OrderVO> findByUid(Integer uid) {
		return orderMapper.findByUid(uid);
	}
}
