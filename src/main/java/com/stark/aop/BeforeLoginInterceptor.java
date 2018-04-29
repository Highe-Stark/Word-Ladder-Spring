package com.stark.aop;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BeforeLoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        System.out.println("Login intercepted");

        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        String url = "http://localhost:8081/login?userid=" + username + "&pwd="+ pwd;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("User-Agent", request.getHeader("User-Agent"));
        JSONObject jsonObject = new JSONObject();
        HttpEntity requestEntity = new HttpEntity(jsonObject.toString(),  httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        if (responseEntity.getStatusCodeValue() != 200) {
            response.setStatus(403);
            response.sendRedirect("errorpage.html");
            return false;
        }
        else {
            if (jsonObject == null) {
                System.out.println("Error, no json object received.");
                return false;
            } else
            System.out.println(jsonObject.toString());
            String aid = jsonObject.getString("JSESSIONID");
            String sid = jsonObject.getString("sid");
            Cookie aidcookie = new Cookie("aid", aid);
            Cookie sidcookie = new Cookie("sid", sid);
            response.addCookie(aidcookie);
            response.addCookie(sidcookie);
            response.setStatus(200);
            return true;
        }
    }
}
