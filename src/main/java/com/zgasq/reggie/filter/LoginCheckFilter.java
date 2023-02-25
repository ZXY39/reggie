package com.zgasq.reggie.filter;


import com.alibaba.fastjson.JSON;
import com.zgasq.reggie.common.BaseContext;
import com.zgasq.reggie.common.R;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER =new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        String[] urls =new String[]{
          "/employee/login",
          "/employee/logout",
          "/backend/**",
          "/front/**",
          "/backend/**",
          "/user/sendMsg",
          "/user/login"
        };
        boolean check = check(urls, requestURI);
        if(check ||request.getSession().getAttribute("employee")!=null){

            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("user")!=null){

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request,response);
            return;
        }

        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    public boolean check(String[] urls, String requestURL){
        for(String url:urls){
            boolean match =PATH_MATCHER.match(url,requestURL);
            if(match){
                return true;
            }
        }
        return false;
    }
}
