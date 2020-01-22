package cn.tedu.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 網站首頁請求的控制器類
 * @author Klein
 */
@Controller
public class WebIndexController {
	
	//網站首頁
	@RequestMapping({"/", "/web","/web/"}) //表示處理方法的路徑都在/底下
	public ModelAndView index() {
        return new ModelAndView("/web/index.html");
    }
//	public String index() {
//        return "forward:/web/index.html";
//    }
}
