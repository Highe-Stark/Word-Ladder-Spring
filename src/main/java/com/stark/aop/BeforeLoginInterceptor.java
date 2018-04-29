package com.stark.aop;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeforeLoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        System.out.println("Login intercepted");

        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        if (username == null || username.isEmpty() || pwd == null || pwd.isEmpty()) {
            response.setStatus(403);
            response.sendRedirect("errorpage.html");
            return false;
        }
        String url = "http://localhost:8081/login?userid=" + username + "&pwd="+ pwd;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("User-Agent", request.getHeader("User-Agent"));
        // JSONObject jsonObject = new JSONObject();
        HttpEntity requestEntity = new HttpEntity(null,  httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            HttpHeaders responseHeader = null;
            responseHeader = responseEntity.getHeaders();
            Map<String, String> resCookie = new HashMap<>();
            List<String> pairs = responseHeader.get("Set-Cookie");
            String cookieStr = pairs.get(0);
            cookieStr = cookieStr.replaceAll("; ", ";");
            System.out.println(cookieStr);
            String[] keyValue = cookieStr.split(";");
            for (int i = 0; i != keyValue.length; i++) {
                System.out.println(keyValue[i]);
                String[] pair = keyValue[i].split("=");
                if (pair.length != 2) {
                    break;
                }
                System.out.println(pair[0] + " : " + pair[1]);
                resCookie.put(pair[0], pair[1]);
            }

            JSONObject responseBody = new JSONObject (responseEntity.getBody());

            String aid = resCookie.get("JSESSIONID");

            String sid = responseBody.getString("sid");
            Cookie aidcookie = new Cookie("aid", aid);
            Cookie sidcookie = new Cookie("sid", sid);
            response.addCookie(aidcookie);
            response.addCookie(sidcookie);
            response.setStatus(200);
            return true;
        }
        catch (Exception e) {
            System.out.println("OAuth failed.");
            response.setStatus(403);
            Cookie invalid = new Cookie("sid", "");
            invalid.setMaxAge(0);
            response.addCookie(invalid);
            invalid = new Cookie("aid", "");
            invalid.setMaxAge(0);
            response.addCookie(invalid);
            response.sendRedirect("errorpage.html");
            return false;
        }

    }
}
