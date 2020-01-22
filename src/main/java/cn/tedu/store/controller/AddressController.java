package cn.tedu.store.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.exception.AccessDeniedException;
import cn.tedu.store.service.exception.AddressNotFoundException;
import cn.tedu.store.util.ResponseResult;

/**
 * 處理收貨地址相關請求的控制器類
 * @author Klein
 */
@RestController //表示ResponseBody和Controller的組合，都是響應正文，不是轉發和重定向
@RequestMapping("/address") //表示處理方法的路徑都在/address底下
public class AddressController extends BaseController {
	
	@Autowired
	private IAddressService addressService;
	
	@PostMapping("/create")
	public ResponseResult<Void> handleCreate(
		Address address, HttpSession session){
		//根據session獲取username
		String username = session.getAttribute("username").toString();
		//根據session獲取uid
		Integer uid = getUidFromSession(session);
		
		//將uid封裝到address中
		address.setUid(uid);
		
		//調用業務層物件執行創建收貨地址
		addressService.create(username, address);
		
		//返回
		return new ResponseResult<>(SUCCESS);
	}
	
	@RequestMapping("/list")
	public ResponseResult<List<Address>> getListByUid(
		HttpSession session) {
		//獲取uid
		Integer uid = getUidFromSession(session);
		//查詢數據
		List<Address> list = 
				addressService.getListByUid(uid);
		//返回
		return new ResponseResult<List<Address>>(SUCCESS, list);
	}
	
	@GetMapping("/get_by_id/{id}")
	public ResponseResult<Address> getById(
		@PathVariable("id") Integer id,
		HttpSession session) {
		//獲取uid
		Integer uid = getUidFromSession(session);
		//執行查詢，獲取結果
		Address address = addressService.getById(id);
		//判斷是否查詢到數據
		if (address == null) {
			throw new AddressNotFoundException(
				"獲取收貨地址失敗！嘗試訪問的收貨地址資訊不存在！");
		}
		//判斷所找的收貨地址資料是否登入者所屬的資料
		if (!address.getUid().equals(uid)) {
			//是：拋出異常： AccessDeniedException
			throw new AccessDeniedException(
				"獲取收貨地址失敗！訪問數據權限驗證不通過！");
		}
		//返回
		return new ResponseResult<Address>(SUCCESS, address);
	}
	
	@GetMapping("/default/{id}")
	public ResponseResult<Void> setDefault(
		HttpSession session, 
		@PathVariable("id") Integer id){
		//根據session獲取username
		String modifiedUser = session.getAttribute("username").toString();
		//從session中獲取uid
		Integer uid = getUidFromSession(session);
		//調用業務層方法執行設置
		addressService.setDefault(uid, id, modifiedUser);
		//返回
		return new ResponseResult<>(SUCCESS);
	}
	
	@PostMapping("/update")
	public ResponseResult<Void> changeAddress(
		Address address, HttpSession session) {
		//從session中獲取username
		String username = session.getAttribute("username").toString();
		//從session中獲取uid
		Integer uid = getUidFromSession(session);
		//封裝資訊到address中
		address.setModifiedUser(username);
		address.setModifiedTime(new Date());
		//執行業務方法
		addressService.changeAddress(uid, address);
		//返回
		return new ResponseResult<>(SUCCESS);
	}
	
	@GetMapping("/delete/{id}")
	public ResponseResult<Void> deleteById(
		HttpSession session, 
		@PathVariable("id") Integer id){
		//根據session獲取username
		String modifiedUser = session.getAttribute("username").toString();
		//從session中獲取uid
		Integer uid = getUidFromSession(session);
		//調用業務層方法執行設置
		addressService.delete(uid, id, modifiedUser);
		//返回
		return new ResponseResult<>(SUCCESS);
	}
	
}


