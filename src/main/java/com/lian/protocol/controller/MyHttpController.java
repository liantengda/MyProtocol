package com.lian.protocol.controller;

import com.lian.protocol.common.globalexception.pojo.response.R;
import com.lian.protocol.common.utils.MyHttpClient;
import com.lian.protocol.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ted
 * @date 2020/6/12 14:18
 */
@RestController
@RequestMapping(value = "/myHttp")
@Slf4j
@Api(tags = "myHttp测试接口")
public class MyHttpController {

    @RequestMapping(value = "/requestWithoutRequestParam",method = RequestMethod.GET)
    @ApiOperation("远程调用不带参数的get接口")
    public void   requestWithoutParamByMyHttpClient(String baseUrl, HttpServletResponse response) throws Exception {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization","hehe");
        HashMap<String, Object> params = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        JSONObject get = MyHttpClient.execute(baseUrl, headers, "GET",params,jsonObject);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset= utf8");
        PrintWriter writer = response.getWriter();
        writer.print(get);
        writer.close();
    }

    @RequestMapping(value = "/requestWithUrlParam",method = RequestMethod.GET)
    @ApiOperation("远程调用带有url参数的get接口")
    public void   requestWithUrlParamsByMyHttpClient(String baseUrl, HttpServletResponse response) throws Exception {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization","hehe");
        HashMap<String, Object> params = new HashMap<>();
        params.put("name","ted");
        params.put("age",18);
        JSONObject jsonObject = new JSONObject();
        JSONObject get = MyHttpClient.execute(baseUrl, headers, "GET",params,jsonObject);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset= utf8");
        PrintWriter writer = response.getWriter();
        writer.print(get);
        writer.close();
    }

    @RequestMapping(value = "requestPost",method = RequestMethod.GET)
    @ApiOperation("远程调用post示例接口")
    public void requestPost(String baseUrl,HttpServletResponse response) throws Exception {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization","hehe");
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
        JSONObject post = MyHttpClient.execute(baseUrl, headers, "POST", params, jsonObject);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset= utf8");
        PrintWriter writer = response.getWriter();
        writer.write(post.toString());
    }

    @RequestMapping(value = "calledTestPost",method = RequestMethod.POST)
    @ApiOperation("post请求远程示例")
    public R<User> calledTestPost(@RequestBody User user){
        return new R<>(user);
    }


    @RequestMapping(value = "/calledNotRequestParam",method = RequestMethod.GET)
    @ApiOperation("不带有请求参数的Get远程示例")
    public R<String> calledTestNotRequestParam(){
        System.out.println("hehehe");
        return new R<>("调用成功了");
    }

    @RequestMapping(value = "/calledWithUrlParam",method = RequestMethod.GET)
    @ApiOperation("带有url参数的get请求示例")
    public R<JSONObject> calledTestWithUrlParam(String name,Integer age){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("age",age);
        return new R<>(jsonObject);
    }

}
