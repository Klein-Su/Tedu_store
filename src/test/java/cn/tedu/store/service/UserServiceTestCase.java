package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.exception.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTestCase {
	
	@Autowired
    private IUserService userService;
	
	@Test
    public void reg() {
		try {
	        User user = new User();
	        user.setUsername("springboot");
	        user.setPassword("123456");
	        user.setGender(1);
	        user.setPhone("13800138010");
	        user.setEmail("springboot@tedu.cn");
	        User result = userService.reg(user);
	        System.err.println("result=" + result);
		} catch (ServiceException e) {
			System.err.println("錯誤類型："+e.getClass().getName());
			System.err.println("錯誤描述："+e.getMessage());
		}
    }
	
	@Test
	public void login() {
		try {
			String username = "spring";
			String password = "1234";
			User result = userService.login(username, password);
			System.err.println("result=" + result);
		} catch (ServiceException e) {
			System.err.println("錯誤類型："+e.getClass().getName());
			System.err.println("錯誤描述："+e.getMessage());
		}
	}
	
	@Test
	public void changePassword() {
		try {
			Integer id = 10;
			String oldPassword = "123456";
			String newPassword = "1234";
			userService.changePassword(id, oldPassword, newPassword);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println("錯誤類型："+e.getClass().getName());
			System.err.println("錯誤描述："+e.getMessage());
		}
	}
	
	@Test
	public void changeAvatar() {
		try {
			Integer uid = 10;
			String avatar = "upload/201901071138.jpg";
			userService.changeAvatar(uid, avatar);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println("錯誤類型："+e.getClass().getName());
			System.err.println("錯誤描述："+e.getMessage());
		}
	}
	
	@Test
	public void changeInfo() {
		try {
			User user = new User();
			user.setId(10);
			user.setGender(0);
			user.setPhone("17700001111");
			user.setEmail("g-liugb@tedu.cn");
			userService.changeInfo(user);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println("錯誤類型："+e.getClass().getName());
			System.err.println("錯誤描述："+e.getMessage());
		}
	}
}



