package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Manager;
import cn.tedu.store.service.IManagerService;
import cn.tedu.store.util.ResponseResult;
import cn.tedu.store.vo.ManagerVO;

@RestController //表示ResponseBody和Controller的組合，都是響應正文，不是轉發和重定向
@RequestMapping("/backstage/manager") //表示處理方法的路徑都在/backstage/manager底下
public class ManagerController extends BaseController {

	@Autowired
	private IManagerService managerService;
	
	@PostMapping("/add_manager")
	public ResponseResult<Void> handleReg(
		Manager manager,
		HttpSession session) {
		//預設新增資料is_delete = 0;
		manager.setIsDelete(0);
		//執行新增
		managerService.reg(manager);
		return new ResponseResult<Void>(SUCCESS);
	}
	
	@PostMapping("/login.do")
	public ResponseResult<Manager> handleLogin(
		@RequestParam("username") String username,
		@RequestParam("password") String password,
		HttpSession session) {
		//執行登入
		Manager manager = managerService.login(username, password);
		//將相關信息存入到Session
		session.setAttribute("mid", manager.getId());
		session.setAttribute("username", manager.getUsername());
		//返回
		return new ResponseResult<Manager>(SUCCESS, manager);
	}
	
	@RequestMapping("/logout.do")
	public ResponseResult<Void> handleLogOut(
		HttpSession session) {
		//銷毀session中的數據
		session.invalidate();
		//返回
		return new ResponseResult<Void>(SUCCESS);
	}
	
	@RequestMapping("/info.do")
	public ResponseResult<Manager> getInfo(
		HttpSession session) {
		Integer id = getMidFromSession(session);
		Manager manager = managerService.getById(id);
		//返回
		return new ResponseResult<Manager>(SUCCESS, manager);
	}
	
	@RequestMapping("/getAlls")
	public ResponseResult<Integer> getAll(
		@RequestParam("inputValue") String inputValue) {
		Integer count = 
			managerService.getAlls(inputValue);
		return new ResponseResult<Integer>(SUCCESS, count);
	}
	
	@RequestMapping("/getList")
	public ResponseResult<List<ManagerVO>> getList(
		@RequestParam("page") Integer page,
		@RequestParam("pageSize") Integer pageSize, 
		@RequestParam("inputValue") String inputValue) {
		//重新設置要跳過的筆數，每頁最多10筆資料
		page = (page-1) * pageSize;
		List<ManagerVO> list = 
				managerService.getList(page, pageSize, inputValue);
		return new ResponseResult<List<ManagerVO>>(SUCCESS, list);
	}
	
	@RequestMapping("/getData/{id}")
	public ResponseResult<Manager> getInfo(
		HttpSession session, 
		@PathVariable("id") Integer id) {
		Manager manager = managerService.getById(id);
		//返回
		return new ResponseResult<Manager>(SUCCESS, manager);
	}
	
	@GetMapping("/delete/{id}")
	public ResponseResult<Void> deleteById(
		HttpSession session, 
		@PathVariable("id") Integer id){
		//從session中獲取mid
		Integer mid = getMidFromSession(session);
		//調用業務層方法執行(軟刪除)
		managerService.changeById(id, mid);
		//返回
		return new ResponseResult<>(SUCCESS);
	}
}
