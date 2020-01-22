package cn.tedu.store.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 後台登入攔截器(測試中，尚未完成)
 * @author Klein
 */
public class BackStageLoginInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(
		HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//獲取Session物件
		HttpSession session = request.getSession();
		//判斷Session中是否存在mid
		if (session.getAttribute("mid") == null) {
			//為null，即沒有mid，即沒有登入
			response.sendRedirect("/backstage/index.html");
			//攔截
			return false;
		} else {
			//非null，即存在mid，即已經登入
			return true;
		}
	}
}

