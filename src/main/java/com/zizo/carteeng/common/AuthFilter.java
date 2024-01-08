package com.zizo.carteeng.common;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthFilter implements Filter {

    private record UrlPath(String method, String url) {}

    private List<UrlPath> excludedUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String initialExcludedUrls = filterConfig.getInitParameter("excludedUrls");
        excludedUrls = Arrays.stream(initialExcludedUrls.split(","))
                .map(url -> {
                    String methodUrl[] = url.trim().split(" ");
                    return new UrlPath(methodUrl[0], methodUrl[1]);
                })
                .toList();

        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String method = httpRequest.getMethod();
        String path = httpRequest.getServletPath();

        if(excludedUrls.contains(new UrlPath(method, path))) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        if(session == null || session.getAttribute("member") == null) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN); // TODO: Error Response
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}