package cn.tedu.store.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Goods;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsMapperTestCase {

	@Autowired
    private GoodsMapper goodsMapper;

    @Test
	public void findByCategory() {
		Long categoryId = 163L;
		Integer offset = 0;
		Integer count = 5;
		List<Goods> list = goodsMapper.findByCategory(categoryId, offset, count);
		System.err.println("BEGIN：");
		for (Goods goods : list) {
			System.err.println(goods);
		}
		System.err.println("END.");
	}
    
    @Test
    public void findById() {
    	Long id = 100000401L;
    	Goods goods = goodsMapper.findById(id);
    	System.err.println("goods=" + goods);
    }
    
    @Test
	public void findByPriority() {
		Integer count = 5;
		List<Goods> list = goodsMapper.findByPriority(count);
		System.err.println("BEGIN：");
		for (Goods goods : list) {
			System.err.println(goods);
		}
		System.err.println("END.");
	}
}





