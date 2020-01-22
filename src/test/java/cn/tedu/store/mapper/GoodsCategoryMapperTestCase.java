package cn.tedu.store.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.GoodsCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsCategoryMapperTestCase {

	@Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Test
	public void getByParent() {
		Long parentId = 161L;
		List<GoodsCategory> list = goodsCategoryMapper.findByParent(parentId);
		System.err.println("BEGIN：");
		for (GoodsCategory goodsCategory : list) {
			System.err.println(goodsCategory);
		}
		System.err.println("END.");
	}
    
}



