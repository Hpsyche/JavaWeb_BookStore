package cn.itcast.bookstore.user.web.Filter;

import cn.itcast.bookstore.user.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /**
         * 1.从session中获取用户信息，判断如果session中存在用户信息，放行！
         *  否则，保存错误信息，转发到login.jsp
         */
        HttpServletRequest httpServletRequest= (HttpServletRequest) req;
        User user= (User) httpServletRequest.getSession().getAttribute("session_user");
        if(user!=null) {
            chain.doFilter(req, resp);
        }else{
            httpServletRequest.setAttribute("msg","您还未登录");
            httpServletRequest.getRequestDispatcher("/jsps/user/login.jsp").forward(httpServletRequest,resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
