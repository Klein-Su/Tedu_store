package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.store.controller.exception.FileEmptyException;
import cn.tedu.store.controller.exception.FileSizeOutOfLimitException;
import cn.tedu.store.controller.exception.FileTypeNotSupportException;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.util.ResponseResult;

@RestController //表示ResponseBody和Controller的組合，都是響應正文，不是轉發和重定向
@RequestMapping("/user") //表示處理方法的路徑都在/user底下
public class UserController extends BaseController {
	/**
	 * 上傳頭像的資料夾名稱
	 */
	private static final String UPLOAD_DIR_NAME = "upload";
	/**
	 * 檔案上傳大小的最大值
	 */
	private static final long FILE_MAX_SIZE = 5 * 1024 * 1024;
	/**
	 * 允許上傳的文件類型
	 */
	private static final List<String> FILE_CONTENT_TYPES = new ArrayList<>();
	
	/**
	 * 初始化允許上傳的文件類型的集合
	 */
	static {
		FILE_CONTENT_TYPES.add("image/jpeg");
		FILE_CONTENT_TYPES.add("image/jpg");
		FILE_CONTENT_TYPES.add("image/png");
		FILE_CONTENT_TYPES.add("image/bmp");
		FILE_CONTENT_TYPES.add("image/gif");
	}
	
	@Autowired
	private IUserService userService;
	
//	@PostMapping("/reg.do")
//	public ResponseResult<Void> handleReg(User user) {
//		userService.reg(user);
//		return new ResponseResult<Void>(SUCCESS);
//	}
	
	@PostMapping("/reg.do")
	public ResponseResult<User> handleReg(
		@RequestParam("regUsername") String username,
		@RequestParam("regPassword") String password,
		HttpSession session) {
		//創建 user 物件
		User user = new User();
		//執行註冊並取回註冊資料
		user.setUsername(username);
		user.setPassword(password);
		userService.reg(user);
		//執行登入
		User userData = 
			userService.login(username, password);
		//將相關信息存入到Session
		session.setAttribute("uid", userData.getId());
		session.setAttribute("username", userData.getUsername());
		return new ResponseResult<User>(SUCCESS, userData);
	}
	
	@PostMapping("/login.do")
	public ResponseResult<User> handleLogin(
		@RequestParam("username") String username,
		@RequestParam("password") String password,
		HttpSession session) {
		//執行登入
		User user = userService.login(username, password);
		//將相關信息存入到Session
		session.setAttribute("uid", user.getId());
		session.setAttribute("username", user.getUsername());
		//返回
		return new ResponseResult<>(SUCCESS, user);
	}
	
	@RequestMapping("/logout.do")
	public ResponseResult<Void> handleLogOut(
		HttpSession session) {
		//銷毀session中的數據
		session.invalidate();
		//返回
		return new ResponseResult<Void>(SUCCESS);
	}
	
	@RequestMapping("/password.do")
	public ResponseResult<Void> changePassword(
		@RequestParam("old_password") String oldPassword,
		@RequestParam("new_password") String newPassword,
		HttpSession session) {
		//獲取當前登入的用戶的id
		Integer uid = getUidFromSession(session);
		//執行修改密碼
		userService.changePassword(uid, oldPassword, newPassword);
		//返回
		return new ResponseResult<Void>(SUCCESS);
	}
	
	@RequestMapping("/info.do")
	public ResponseResult<User> getInfo(
		HttpSession session) {
		Integer id = getUidFromSession(session);
		User user = userService.getById(id);
		//返回
		return new ResponseResult<User>(SUCCESS, user);
	}
	
	@PostMapping("/change_info.do")
	public ResponseResult<Void> changeInfo(
		User user, HttpSession session) {
		//獲取當前登入的用戶的id
		Integer id = getUidFromSession(session);
		//將id封裝到參數user中，因為user是用戶提交的數據，並不包含id
		user.setId(id);
		//執行修改
		userService.changeInfo(user);
		//返回
		return new ResponseResult<Void>(SUCCESS);
	}
	
	@PostMapping("/upload.do")
	public ResponseResult<String> handleUpload(
		HttpSession session, 
		@RequestParam("file") MultipartFile file){
		//檢查是否存在上傳文件 > file.isEmpty()
		if (file.isEmpty()) {
			//拋出異常：文件不允許為空
			throw new FileEmptyException(
					"上傳失敗！沒有選擇上傳的文件，或選中的文件為空！");
		}
		//檢查文件大小 > file.getSize()，若檔案超過5MB
		if (file.getSize() > FILE_MAX_SIZE) {
			//拋出異常：文件大小超出限制
			throw new FileSizeOutOfLimitException(
					"上傳失敗！上傳檔案超出大小！");
		}
		//檢查文件類型 > file.getContentType()
		if (!FILE_CONTENT_TYPES.contains(file.getContentType())) {
			//拋出異常：文件類型限制
			throw new FileTypeNotSupportException(
					"上傳失敗！上傳的檔案類型不支援！");
		}
		//確定上傳文件夾的路徑 > 
		//session.getServletContext.getRealPath(UPLOAD_DIR_NAME) > exists() > mkdirs()
		String parentPath = 
				session.getServletContext().getRealPath(UPLOAD_DIR_NAME);
		File parent = new File(parentPath);
		if (!parent.exists()) {
			parent.mkdirs();
		}
		//確定文件名 > getOriginalFileName() 獲取檔案名稱
		String originalFileName = file.getOriginalFilename();
		//獲取檔案的副檔名 suffix
		int beginIndex = originalFileName.lastIndexOf(".");
		String suffix = originalFileName.substring(beginIndex);
		//賦予檔案新名稱
		//(為了避免同時間上傳，遇到檔案重名的問題，因此加了Random隨機數取值，以降低重名的機率)
		String fileName = System.currentTimeMillis() + "" + 
				(new Random().nextInt(900000)+100000) + suffix;
		//確定文件
		File dest = new File(parent, fileName);
		//執行保存文件
		try {
			file.transferTo(dest);
			System.err.println("上傳完成！");
		} catch (IllegalStateException e) {
			//拋出異常：上傳失敗
		} catch (IOException e) {
			//拋出異常：上傳失敗
		}
		//獲取當前用戶的id
		Integer uid = getUidFromSession(session);
		//更新頭像數據
		String avatar = "/" + UPLOAD_DIR_NAME + "/" + fileName;
		userService.changeAvatar(uid, avatar);
		//返回
		ResponseResult<String> rr = new ResponseResult<>();
		rr.setState(SUCCESS);
		rr.setData(avatar);
		return rr;
	}
}



