package cn.tedu.store.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartMapperTestCase {

	@Autowired
    private CartMapper cartMapper;

    @Test
    public void addnew() {
        Cart cart = new Cart();
        cart.setUid(10);
        cart.setGid(100L);
        cart.setPrice(5000L);
        cart.setCount(8);
        Integer rows = cartMapper.addnew(cart);
        System.err.println("rows=" + rows);
        System.err.println("cart=" + cart);
    }
    
    @Test
    public void updateCount() {
    	Integer id = 1;
    	Integer count = 6;
    	Integer rows = cartMapper.updateCount(id, count);
    	System.err.println("rows=" + rows);
    }
    
    @Test
    public void findByUidAndGid() {
    	Integer uid = 10;
    	Long goodsId = 100L;
    	Cart cart = cartMapper.findByUidAndGid(uid, goodsId);
    	System.err.println("cart=" + cart);
    }
    
    @Test
    public void findById() {
    	Integer id = 9;
    	Cart cart = cartMapper.findById(id);
    	System.err.println(cart);
    }
    
    @Test
    public void findByUid() {
    	Integer uid = 10;
    	List<CartVO> list = cartMapper.findByUid(uid);
    	System.err.println("BEGIN:");
    	for (CartVO cartvo : list) {
    		System.err.println("cartvo = " + cartvo);
    	}
    	System.err.println("END.");
    }
    
    @Test
    public void findByIds() {
    	Integer[] ids = {6,9};
    	List<CartVO> list = cartMapper.findByIds(ids);
    	System.err.println("BEGIN:");
    	for (CartVO cartvo : list) {
    		System.err.println("cartvo = " + cartvo);
    	}
    	System.err.println("END.");
    }
}


