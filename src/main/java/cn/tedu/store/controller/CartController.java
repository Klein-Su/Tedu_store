package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.util.ResponseResult;
import cn.tedu.store.vo.CartVO;

/**
 * 購物車數據相關請求的控制器類
 * @author Klein
 */
@RestController //表示ResponseBody和Controller的組合，都是響應正文，不是轉發和重定向
@RequestMapping("/cart") //表示處理方法的路徑都在/cart底下
public class CartController extends BaseController {
	
	@Autowired
	private ICartService cartService;
	
	@PostMapping("/add_to_cart")
	public ResponseResult<Void> addToCart(
		HttpSession session, Cart cart) {
		//從session中獲取username
		String username = session.getAttribute("username").toString();
		//從session中獲取uid
		Integer uid = getUidFromSession(session);
		//將uid封裝到cart中
		cart.setUid(uid);
		//執行業務方法
		cartService.addToCart(username, cart);
		//返回
		return new ResponseResult<>(SUCCESS);
	}
	
	@GetMapping("/add_count")
	public ResponseResult<Void> addCount(
		@RequestParam("id") Integer id, 
		HttpSession session) {
		//獲取uid
		Integer uid = getUidFromSession(session);
		//執行業務
		cartService.addCount(id, uid);
		//返回
		return new ResponseResult<>(SUCCESS);
	}
	
	@GetMapping("/reduce_count")
	public ResponseResult<Void> reduceCount(
		@RequestParam("id") Integer id, 
		HttpSession session) {
		//獲取uid
		Integer uid = getUidFromSession(session);
		//執行業務
		cartService.reduceCount(id, uid);
		//返回
		return new ResponseResult<>(SUCCESS);
	}
	
	@GetMapping("/update_count")
	public ResponseResult<Void> updateCount(
		@RequestParam("id") Integer id,
		@RequestParam("count") Integer count,
		HttpSession session) {
		//獲取uid
		Integer uid = getUidFromSession(session);
		//執行業務
		cartService.modifiedCount(id, uid, count);
		//返回
		return new ResponseResult<>(SUCCESS);
	}
	
	@GetMapping("/list")
	public ResponseResult<List<CartVO>> getByUid (
		HttpSession session){
		//從session中獲取uid
		Integer uid = getUidFromSession(session);
		//執行查詢，獲取結果
		List<CartVO> list = cartService.getByUid(uid);
		//返回
		return new ResponseResult<List<CartVO>>(SUCCESS, list);
	}
	
	@GetMapping("/get_by_ids")
	public ResponseResult<List<CartVO>> getByIds(
		@RequestParam("cart_id") Integer[] ids) {
		//執行查詢，獲取結果
		List<CartVO> list = cartService.getByIds(ids);
		//返回
		return new ResponseResult<List<CartVO>>(SUCCESS, list);
	}
	
	@GetMapping("/delete/{id}")
	public ResponseResult<Void> deleteById(
		HttpSession session, 
		@PathVariable("id") Integer id){
		//從session中獲取uid
		Integer uid = getUidFromSession(session);
		//調用業務層方法執行設置
		cartService.delete(uid, id);
		//返回
		return new ResponseResult<>(SUCCESS);
	}
}

