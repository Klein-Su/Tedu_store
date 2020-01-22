package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Order;
import cn.tedu.store.service.IOrderService;
import cn.tedu.store.util.ResponseResult;
import cn.tedu.store.vo.OrderVO;

/**
 * 處理訂單相關請求的控制器類
 * @author Klein
 */
@RestController //表示ResponseBody和Controller的組合，都是響應正文，不是轉發和重定向
@RequestMapping("/order") //表示處理方法的路徑都在/order底下
public class OrderController extends BaseController {
	
	@Autowired
	private IOrderService orderService;
	
	@RequestMapping("/create")
	public ResponseResult<Order> createOrder(
		HttpSession session,
		@RequestParam("address") Integer addressId,
		@RequestParam("cart_id") Integer[] cartIds){
		//獲取uid
		Integer uid = getUidFromSession(session);
		//獲取當前用戶名
		String username = session.getAttribute("username").toString();
		//新增並獲取 Order 物件
		Order order = 
			orderService.createOrder(uid, username, addressId, cartIds);
		//返回
		return new ResponseResult<Order>(SUCCESS, order);
	}
	
	@RequestMapping("/details/{id}")
	public ResponseResult<OrderVO> getById(
		@PathVariable("id") Integer id) {
		OrderVO orderVO = orderService.getById(id);
		return new ResponseResult<OrderVO>(SUCCESS, orderVO);
	}
	
	@RequestMapping("/list")
	public ResponseResult<List<OrderVO>> getByUid (
		HttpSession session){
		//從session中獲取uid
		Integer uid = getUidFromSession(session);
		//執行查詢，獲取結果
		List<OrderVO> list = orderService.getByUid(uid);
		//返回
		return new ResponseResult<List<OrderVO>>(SUCCESS, list);
	}
	
	@RequestMapping("/getData/{id}")
	public ResponseResult<Order> getByIdAndUid(
		@PathVariable("id") Integer id,
		HttpSession session) {
		//從session中獲取uid
		Integer uid = getUidFromSession(session);
		//執行查詢，獲取結果
		Order order = orderService.getByIdAndUid(id, uid);
		//返回
		return new ResponseResult<Order>(SUCCESS, order);
	}
}

