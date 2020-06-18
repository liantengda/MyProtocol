package com.lian.protocol.service.impl;

import com.lian.protocol.common.utils.MyHttpClient;
import com.lian.protocol.model.User;
import com.lian.protocol.service.MyHttpService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author Ted
 * @date 2020/6/18 18:19
 */
@Service
public class MyHttpServiceImpl implements MyHttpService {


    @Override
    public void doGetWithoutParam(String baseUrl, HttpServletResponse response) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization","hehe");
        HashMap<String, Object> params = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        JSONObject get = MyHttpClient.execute(baseUrl,params,headers,"GET",jsonObject);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset= utf8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.print(get);
        writer.close();
    }

    @Override
    public void doGetWithParam(String baseUrl, HttpServletResponse response) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization","hehe");
        HashMap<String, Object> params = new HashMap<>();
        params.put("name","ted");
        params.put("age",18);
        JSONObject jsonObject = new JSONObject();
        JSONObject get = MyHttpClient.execute(baseUrl, params,headers, "GET",jsonObject);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset= utf8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.print(get);
        writer.close();
    }

    @Override
    public void doPostWithoutUrlParam(String baseUrl, HttpServletResponse response) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization","hehe");
        headers.put("Content-Type","application/json");
        HashMap<String, Object> params = new HashMap<>();
        params.put("name","ted");
        params.put("age",18);
        User user = new User();
        user.setCreateTime(System.currentTimeMillis());
        user.setName("ted");
        user.setPhone("18434367785");
        user.setPassword("78685");
        user.setId(1L);
        JSONObject jsonObject = JSONObject.fromObject(user);
        JSONObject post = MyHttpClient.execute(baseUrl,params, headers, "POST", jsonObject);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset= utf8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.write(post.toString());
    }

    @Override
    public void doPostWithUrlParam(String baseUrl, HttpServletResponse response) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization","hehe");
        headers.put("Content-Type","application/json");
        HashMap<String, Object> params = new HashMap<>();
        params.put("urlParam","ted");
        User user = new User();
        user.setCreateTime(System.currentTimeMillis());
        user.setName("ted");
        user.setPhone("18434367785");
        user.setPassword("78685");
        user.setId(1L);
        JSONObject jsonObject = JSONObject.fromObject(user);
        JSONObject post = MyHttpClient.execute(baseUrl, params, headers, "POST", jsonObject);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset= utf8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.write(post.toString());
    }
}
