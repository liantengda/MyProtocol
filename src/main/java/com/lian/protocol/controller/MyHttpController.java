package com.lian.protocol.controller;

import com.lian.protocol.common.globalexception.pojo.response.R;
import com.lian.protocol.common.utils.MyHttpClient;
import com.lian.protocol.model.User;
import com.lian.protocol.service.MyHttpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Ted
 * @date 2020/6/12 14:18
 */
@RestController
@RequestMapping(value = "/myHttp")
@Slf4j
@Api(tags = "myHttp测试接口")
public class MyHttpController {

    @Autowired
    MyHttpService myHttpService;

    @RequestMapping(value = "/requestWithoutRequestParam",method = RequestMethod.GET)
    @ApiOperation("远程调用不带参数的get接口")
    public void   requestWithoutParamByMyHttpClient(String baseUrl, HttpServletResponse response)  {
       myHttpService.doGetWithoutParam(baseUrl,response);
    }

    @RequestMapping(value = "/requestWithUrlParam",method = RequestMethod.GET)
    @ApiOperation("远程调用带有url参数的get接口")
    public void   requestWithUrlParamsByMyHttpClient(String baseUrl, HttpServletResponse response)  {
        myHttpService.doGetWithParam(baseUrl,response);
    }

    @RequestMapping(value = "requestPost",method = RequestMethod.GET)
    @ApiOperation("远程调用post示例接口")
    public void requestPost(String baseUrl,HttpServletResponse response) {
       myHttpService.doPostWithoutUrlParam(baseUrl,response);
    }

    @RequestMapping(value = "requestPostUrl",method = RequestMethod.GET)
    @ApiOperation("远程调用带有url参数的Post接口")
    public void requestPostWithUrlParam(String baseUrl,HttpServletResponse response)  {
        myHttpService.doPostWithUrlParam(baseUrl,response);
    }


    @RequestMapping(value = "requestPostUrlAndAuth",method = RequestMethod.GET)
    @ApiOperation("远程调用带有url参数并且需要权限验证的post接口")
    public void requestPostWithUrlParamAndAuthorization(String baseUrl,
                                                        HttpServletResponse response,
                                                        HttpServletRequest request){
        myHttpService.doPostWithUrlParamAndAuth(baseUrl,response,request);
    }

    @RequestMapping(value = "postWithUrlParamAndAuth",method = RequestMethod.POST)
    @ApiOperation("post请求远程示例带有url参数并且带有权限认证头部信息")
    @RequiresRoles("超级管理员")
    public R<JSONObject> calledTestPostWithUrlAndAuthorization(@RequestBody User user,
                                                               String urlParam,
                                                               HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user",user);
        jsonObject.put("urlParam",urlParam);
        jsonObject.put("token",request.getHeader("Authorization"));
        return new R<>(jsonObject);
    }


    @RequestMapping(value = "calledTestPostWithUrl",method = RequestMethod.POST)
    @ApiOperation("post请求远程示例带有url参数")
    public R<JSONObject> calledTestPostWithUrl(@RequestBody User user,String urlParam){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user",user);
        jsonObject.put("urlParm",urlParam);
        return new R<>(jsonObject);
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
