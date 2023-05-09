package com.trickle.os.mvc.auth;

import javax.servlet.http.*;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.trickle.os.util.Debug;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String reUri = request.getRequestURI();
        Debug.log("reUri:" + reUri);
        if(reUri.startsWith("/login")) {
        	session.setAttribute("returnUrl",reUri);
        	 if (session == null || session.getAttribute("user") == null) { // 로그인 정보가 없는 경우
             	response.sendRedirect("/user/loginForm");
                return false;
             }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}