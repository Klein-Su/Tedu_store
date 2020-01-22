package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.GoodsCategory;
import cn.tedu.store.service.IGoodsCategoryService;
import cn.tedu.store.util.ResponseResult;

/**
 * 商品分類數據相關請求的控制器類
 * @author Klein
 */
@RestController //表示ResponseBody和Controller的組合，都是響應正文，不是轉發和重定向
@RequestMapping("/category") //表示處理方法的路徑都在/category底下
public class GoodsCategoryController extends BaseController {
	
	@Autowired
	private IGoodsCategoryService goodsCategoryService;
	
	@GetMapping("/list/{parent}")
	public ResponseResult<List<GoodsCategory>> getByParent(
		@PathVariable("parent") Long parentId) {
		List<GoodsCategory> list = goodsCategoryService.getByParent(parentId);
		return new ResponseResult<List<GoodsCategory>>(SUCCESS, list);
	}
}

