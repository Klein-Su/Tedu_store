package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Goods;
import cn.tedu.store.service.IGoodsService;
import cn.tedu.store.util.ResponseResult;

/**
 * 商品數據相關請求的控制器類
 * @author Klein
 */
@RestController //表示ResponseBody和Controller的組合，都是響應正文，不是轉發和重定向
@RequestMapping("/goods") //表示處理方法的路徑都在/goods底下
public class GoodsController extends BaseController {

	@Autowired
	private IGoodsService goodsService;
	
	@GetMapping("/list/{categoryId}")
	public ResponseResult<List<Goods>> getByCategory(
		@PathVariable("categoryId") Long categoryId){
		List<Goods> list = 
			goodsService.getByCategory(categoryId, 0, 20);
		return new ResponseResult<List<Goods>>(SUCCESS, list);
	}
	
	@GetMapping("/details/{id}")
	public ResponseResult<Goods> getById(
		@PathVariable("id") Long id){
		Goods goods = 
			goodsService.getById(id);
		return new ResponseResult<Goods>(SUCCESS, goods);
	}
	
	@GetMapping("/hot")
	public ResponseResult<List<Goods>> getHotGoods() {
		List<Goods> list = 
			goodsService.getByPriority(4);
		return new ResponseResult<List<Goods>>(SUCCESS, list);
	}
	
	@GetMapping("/new")
	public ResponseResult<List<Goods>> getNewGoods() {
		List<Goods> list = 
			goodsService.getByLaunched(4);
		return new ResponseResult<List<Goods>>(SUCCESS, list);
	}
	
	@PostMapping("/getAlls")
	public ResponseResult<Integer> getAll(
		@RequestParam("categoryId") Long categoryId,
		@RequestParam("brand") String brand,
		@RequestParam("beginPrice") Long beginPrice,
		@RequestParam("endPrice") Long endPrice,
		@RequestParam("inputValue") String inputValue) {
		Integer count = 
			goodsService.getAlls(categoryId, brand, beginPrice, endPrice, inputValue);
		return new ResponseResult<Integer>(SUCCESS, count);
	}
	
	@PostMapping("/getSearchData")
	public ResponseResult<List<Goods>> getData(
		@RequestParam("categoryId") Long categoryId,
		@RequestParam("brand") String brand,
		@RequestParam("beginPrice") Long beginPrice,
		@RequestParam("endPrice") Long endPrice,
		@RequestParam("page") Integer page,
		@RequestParam("pageSize") Integer pageSize, 
		@RequestParam("inputValue") String inputValue) {
		//重新設置要跳過的筆數，每頁最多12筆資料
		page = (page-1) * pageSize;
		List<Goods> list = 
				goodsService.getData(
					categoryId, brand, beginPrice, endPrice, page, pageSize, inputValue);
		return new ResponseResult<List<Goods>>(SUCCESS, list);
	}
	
	@PostMapping("/getBrandByCategoryId")
	public ResponseResult<List<Goods>> getBrandByCategoryId(
		@RequestParam("categoryId") Long categoryId) {
		List<Goods> goods = 
			goodsService.getByCategoryId(categoryId);
		return new ResponseResult<List<Goods>>(SUCCESS, goods);
	}
}



