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

import cn.tedu.store.entity.Favorites;
import cn.tedu.store.service.IFavoritesService;
import cn.tedu.store.util.ResponseResult;
import cn.tedu.store.vo.FavoritesVO;

/**
 * 收藏數據相關請求的控制器類
 * @author Klein
 */
@RestController //表示ResponseBody和Controller的組合，都是響應正文，不是轉發和重定向
@RequestMapping("/favorites") //表示處理方法的路徑都在/favorites底下
public class FavoritesController extends BaseController {
	
	@Autowired
	private IFavoritesService favoritesService;
	
	@PostMapping("/add_to_favorites")
	public ResponseResult<Integer> addToFavorites(
		HttpSession session, Favorites favorites) {
		//從session中獲取username
		String username = session.getAttribute("username").toString();
		//從session中獲取uid
		Integer uid = getUidFromSession(session);
		//將uid封裝到favorites中
		favorites.setUid(uid);
		//執行業務方法
		favoritesService.addToFavorites(username, favorites);
		//取得自增id
		Integer newId = favorites.getId();
		//返回
		return new ResponseResult<Integer>(SUCCESS, newId);
	}
	
	@PostMapping("/getAllFavorites")
	public ResponseResult<Integer> getAllByUid(
		HttpSession session) {
		//從session中獲取uid
		Integer uid = getUidFromSession(session);
		//執行業務方法
		Integer count = 
			favoritesService.getAllByUid(uid);
		//返回
		return new ResponseResult<Integer>(SUCCESS, count);
	}
	
	@RequestMapping("/findById")
	public ResponseResult<Favorites> findById(
		HttpSession session, Long gid) {
		//從session中獲取uid
		Integer uid = getUidFromSession(session);
		//執行業務方法
		Favorites favorites = 
				favoritesService.getByUidAndGid(uid, gid);
		//返回
		return new ResponseResult<Favorites>(SUCCESS, favorites);
	}
	
	@GetMapping("/list")
	public ResponseResult<List<FavoritesVO>> getByUid (
		HttpSession session,
		@RequestParam("page") Integer page){
		//從session中獲取uid
		Integer uid = getUidFromSession(session);
		//重新設置要跳過的筆數，每頁最多12筆資料
		Integer pages = (page-1) * 12;
		//執行查詢，獲取結果
		List<FavoritesVO> list = favoritesService.getByUid(uid, pages);
		//返回
		return new ResponseResult<List<FavoritesVO>>(SUCCESS, list);
	}
	
	@GetMapping("/delete/{id}")
	public ResponseResult<Void> deleteById(
		HttpSession session, 
		@PathVariable("id") Integer id){
		//從session中獲取uid
		Integer uid = getUidFromSession(session);
		//調用業務層方法執行設置
		favoritesService.delete(uid, id);
		//返回
		return new ResponseResult<>(SUCCESS);
	}
}
