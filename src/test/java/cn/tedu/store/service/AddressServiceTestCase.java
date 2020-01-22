package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.exception.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTestCase {
	
	@Autowired
    private IAddressService addressService;
	
	@Test
    public void create() {
		String username = "Admin";
		Address address = new Address();
		address.setUid(11);
		address.setName("小馬同學");
		address.setProvince("440000");
		address.setCity("440300");
		address.setArea("440305");
		Address result = addressService.create(username, address);
		System.err.println("result=" + result);
    }
	
	@Test
	public void setDefault() {
		try {
			Integer uid = 10;
			Integer id = 18;
			String modifiedUser = "springboot";
			addressService.setDefault(uid, id, modifiedUser);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println("錯誤類型："+e.getClass().getName());
			System.err.println("錯誤描述："+e.getMessage());
		}
	}
	
	@Test
	public void getListByUid() {
		Integer uid = 10;
		List<Address> list = addressService.getListByUid(uid);
		System.err.println("BEGIN：");
		for (Address address : list) {
			System.err.println(address);
		}
		System.err.println("END.");
	}
	
	@Test
	public void getById() {
		Integer id = 19;
		Address address = addressService.getById(id);
		System.err.println("address=" + address);
	}
	
	@Test
	public void delete() {
		try {
			Integer uid = 10;
			Integer id = 20;
			String modifiedUser = "springboot";
			addressService.delete(uid, id, modifiedUser);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println("錯誤類型："+e.getClass().getName());
			System.err.println("錯誤描述："+e.getMessage());
		}
	}
}




