package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Order;
import cn.tedu.store.service.exception.ServiceException;
import cn.tedu.store.vo.OrderVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTestCase {
	
	@Autowired
    private IOrderService orderService;
	
	@Test
    public void createOrder() {
		try {
			Integer uid = 10;
			String username = "springboot";
			Integer addressId = 16;
			Integer[] cartIds = {6,9};
			Order order = orderService.createOrder(uid, username, addressId, cartIds);
			System.err.println("order=" + order);
		} catch (ServiceException e) {
			System.err.println("錯誤類型："+e.getClass().getName());
			System.err.println("錯誤描述："+e.getMessage());
		}
    }
	
	@Test
	public void getById() {
		Integer id = 5;
		OrderVO orderVO = orderService.getById(id);
		System.err.println("orderVO=" + orderVO);
	}
}

