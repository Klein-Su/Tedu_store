package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.GoodsCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsCategoryServiceTestCase {
	
	@Autowired
    private IGoodsCategoryService goodsCategoryService;
	
	@Test
	public void getListByParent() {
		Long parentId = 161L;
		List<GoodsCategory> list = goodsCategoryService.getByParent(parentId);
		System.err.println("BEGIN：");
		for (GoodsCategory goodsCategory : list) {
			System.err.println(goodsCategory);
		}
		System.err.println("END.");
	}
	
}


