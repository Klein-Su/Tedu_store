package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.District;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.util.ResponseResult;

/**
 * 處理省/市/區的控制器類
 * @author Klein
 */
@RestController //表示ResponseBody和Controller的組合，都是響應正文，不是轉發和重定向
@RequestMapping("/district") //表示處理方法的路徑都在/district底下
public class DistrictController extends BaseController {
	
	@Autowired
	private IDistrictService districtService;
	
	// /district/list
	// GET / POST
	// parent=?
	// ResponseResult<List<District>>
	
	@RequestMapping("/list/{parent}")
	public ResponseResult<List<District>> getListByParent(
		@PathVariable("parent") String parent) {
		List<District> list = districtService.getListByParent(parent);
		return new ResponseResult<List<District>>(SUCCESS, list);
	}
}


