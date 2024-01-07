package com.zizo.carteeng.common;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        try{

//            System.out.println(session.getId());
//            System.out.println(session.getAttribute("status"));
//            Member member = (Member) session.getAttribute("member");
//            System.out.println(member);

            chain.doFilter(request, response);
        }catch(Exception e){
            throw e;
        }finally {

        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
