/* ==============================================================
 * $ID: SystemModel.java, v1.30 2016/4/25 13:15:01 Rick Exp $
 * created: [2016-04-25 13:15:01] by Rick
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.util.filter;

import com.sendyago.util.common.CookieUtil;
import com.sendyago.util.context.CONFIG;
import org.apache.commons.collections.bag.HashBag;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * springMVC过滤器类
 * 判断session中用户是否过期
 *
 * @author $Author: Rick $
 * @version $Revision: 1.0 $Date: 2016/4/25 13:15:01 $
 */
public class SystemFilter implements Filter {

    Log log = LogFactory.getLog(SystemFilter.class);


    /**
     * 主方法 ，判断用户SESSION是否过期
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
            throw new ServletException("OncePerRequestFilter just supports HTTP requests");
        }
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession(true);

        /* 获取前台调用的URL，截取URL，判断后缀名是否符合校验 */
        StringBuffer url = httpRequest.getRequestURL();
        String proName = httpRequest.getContextPath();
        String urlNewName = url.substring(0, url.lastIndexOf("/"));
        String urlLastName = urlNewName.substring(urlNewName.lastIndexOf("/"));

        boolean flag = false;

        /* 循环判断校验信息是否正确 */
        for (String str : CONFIG.LOGIN_FILTER_STRS) {
        	if(str.equals(urlLastName)){
                flag = true;
        	}
        }

        if (proName.equals(urlLastName)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        /* 获取SESSION中的用户信息，如果为空则跳转到登录页面 */
        Object object = session.getAttribute(CONFIG.LOGINED);

        if (object == null) {

            // 用户未登录或已过期
            if(flag) {
                filterChain.doFilter(servletRequest, servletResponse);
                log.debug("=============用户未登录或已超时===============");
                return;
            }
            boolean isAjaxRequest = isAjaxRequest(httpRequest);
            if (isAjaxRequest) {
                httpResponse.setCharacterEncoding("UTF-8");
                httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(),
                        "您已经太长时间没有操作,请刷新页面");
                log.debug("=============用户未登录或已超时===============");
            }
            httpResponse.sendRedirect(httpRequest.getContextPath());
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
    }

    /**
     * 判断是否为Ajax请求
     *
     * @param request HttpServletRequest
     * @return 是true, 否false
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/api");
    }


    public void init(FilterConfig filterConfig) throws ServletException {
            /*如果需要注入，请取消注释*/
//           ServletContext servletContext = filterConfig.getServletContext();
//            WebApplicationContext applicationContext = (WebApplicationContext) servletContext.
//                    getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
//            if (null == topConstantsImpl) {
//                //从Spring AC 中加载app configuration对象
//                topConstantsImpl = applicationContext.getBean(TopConstantsImpl.class);
//        }
    }


    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
