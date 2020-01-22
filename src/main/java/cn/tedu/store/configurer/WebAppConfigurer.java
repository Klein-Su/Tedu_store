package cn.tedu.store.configurer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.tedu.store.interceptor.BackStageLoginInterceptor;
import cn.tedu.store.interceptor.LoginInterceptor;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

	@Override //添加欄截器
	public void addInterceptors(InterceptorRegistry registry) {
		//黑名單
		List<String> pathPatterns = new ArrayList<>();
		pathPatterns.add("/user/**");
		pathPatterns.add("/web/**");
		pathPatterns.add("/address/**");
		pathPatterns.add("/cart/**");
		pathPatterns.add("/order/**");
		pathPatterns.add("/favorites/**");
//		pathPatterns.add("/manager/**");
		
		//白名單
		List<String> excludePathPatterns = new ArrayList<>();
		excludePathPatterns.add("/user/reg.do");
		excludePathPatterns.add("/user/login.do");
//		excludePathPatterns.add("/manager/add_manager"); //TODO 正常來說，需有此權限的人才能新增管理人員
//		excludePathPatterns.add("/manager/login.do");
		excludePathPatterns.add("/web/index.html");
		excludePathPatterns.add("/web/header.html");
		excludePathPatterns.add("/web/footer.html");
		excludePathPatterns.add("/web/register.html");
		excludePathPatterns.add("/web/login.html");
		excludePathPatterns.add("/web/product.html");
		excludePathPatterns.add("/web/search.html");
//		excludePathPatterns.add("/web/backstageHeader.html");
//		excludePathPatterns.add("/web/backstageFooter.html");
//		excludePathPatterns.add("/web/backstage.html");
//		excludePathPatterns.add("/web/addManager.html");
		
		//登入欄截器註冊
		registry
			.addInterceptor(new LoginInterceptor())
			.addPathPatterns(pathPatterns)
			.excludePathPatterns(excludePathPatterns);
		
		//後台黑名單
		List<String> backPathPatterns = new ArrayList<>();
		backPathPatterns.add("/backstage/**");
		//後台白名單
		List<String> backExcludePathPatterns = new ArrayList<>();
		backExcludePathPatterns.add("/backstage/manager/add_manager"); //TODO 正常來說，需有此權限的人才能新增管理人員
		backExcludePathPatterns.add("/backstage/manager/login.do");
		backExcludePathPatterns.add("/backstage/backstageHeader.html");
		backExcludePathPatterns.add("/backstage/backstageFooter.html");
		backExcludePathPatterns.add("/backstage/index.html");
		backExcludePathPatterns.add("/backstage/addManager.html"); //TODO 正常來說，需有此權限的人才能新增管理人員
		
		//後台登入欄截器註冊
		registry
			.addInterceptor(new BackStageLoginInterceptor())
			.addPathPatterns(backPathPatterns)
			.excludePathPatterns(backExcludePathPatterns);
		
	}
	
	//設定默認路徑 (首頁)
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/").setViewName("/web/index.html");
//	}
}


