package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.District;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictServiceTestCase {
	
	@Autowired
    private IDistrictService districtService;
	
	@Test
    public void getListByParent() {
		String parent = "86";
		List<District> result = 
				districtService.getListByParent(parent);
		System.err.println("Begin-------------");
		for(District district : result) {
			System.err.println(district);
		}
		System.err.println("End---------------");
    }
	
	@Test
	public void getByCode() {
		String code = "320000";
		District district = 
				districtService.getByCode(code);
		System.err.println("district=" + district);
	}
}




