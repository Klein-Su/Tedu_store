package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.exception.ServiceException;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTestCase {
	
	@Autowired
    private ICartService cartService;
	
	@Test
	public void addToCart() {
		try {
			String username = "springboot";
			Cart cart = new Cart();
			cart.setUid(11);
			cart.setGid(9527L);
			cart.setCount(2);
			cart.setPrice(800L);
			cartService.addToCart(username, cart);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println("錯誤類型："+e.getClass().getName());
			System.err.println("錯誤描述："+e.getMessage());
		}
	}
	
	@Test
	public void addCount() {
		try {
			Integer id = 9;
			Integer uid = 10;
			cartService.addCount(id, uid);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println("錯誤類型："+e.getClass().getName());
			System.err.println("錯誤描述："+e.getMessage());
		}
	}
	
	@Test
	public void getByUid() {
		Integer uid = 10;
		List<CartVO> list = cartService.getByUid(uid);
		System.err.println("BEGIN:");
		for (CartVO cartVO : list) {
			System.err.println(cartVO);
		}
		System.err.println("END.");
	}
	
	@Test
	public void getByIds() {
		Integer[] ids = {7,8};
		List<CartVO> list = cartService.getByIds(ids);
		System.err.println("BEGIN:");
		for (CartVO cartVO : list) {
			System.err.println(cartVO);
		}
		System.err.println("END.");
	}
}



