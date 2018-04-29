package com.stark.aop;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class BeforeServiceInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        Cookie[] cookies = request.getCookies();
        if (cookies == null  || cookies.length == 0) {
            response.setStatus(403);
            response.sendRedirect("errorpage.html");
            return false;
        }
        String aid = null;
        String sid = null;
        for( Cookie cookie : cookies) {
            if (cookie.getName().equals("aid")) aid = cookie.getValue();
            if (cookie.getName().equals("sid")) sid = cookie.getValue();
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("User-Agent", request.getHeader("User-Agent"));
        List<String> cookieList = new ArrayList<String>();
        cookieList.add("JSESSIONID="+aid);
        httpHeaders.put(HttpHeaders.COOKIE, cookieList);
        String url = "http://localhost:8081/auth?sid=" + sid;
        HttpEntity requestEntity = new HttpEntity(null, httpHeaders);

        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity ,String.class);
            System.out.println("Auth Success.");
            response.setStatus(200);
            return true;
        }
        catch (Exception e ) {
            response.sendRedirect("/errorpage");
            System.out.println("Unauthorized Access");
            return false;
        }
    }
}
