package com.lian.protocol.controller;

import cn.hutool.json.JSONUtil;
import com.lian.protocol.common.globalexception.pojo.response.R;
import com.lian.protocol.common.utils.HttpClientUtil;
import com.lian.protocol.service.HttpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ted
 * @date 2020/6/10 16:29
 */
@RestController
@RequestMapping(value = "/http")
@Slf4j
@Api(tags = "http测试接口")
public class HttpController {

    @Autowired
    HttpService httpService;

    @RequestMapping(value = "/getWriteException",method = RequestMethod.GET)
    @ApiOperation("重复getWriter报错")
    public R<String> getWriteException(HttpServletResponse response,String baseUrl) throws IOException {
        HashMap<String, Object> stringObjectHashMap = HttpClientUtil.doGet(baseUrl);
        PrintWriter writer = response.getWriter();
        writer.write(stringObjectHashMap.toString());
        return new R<>("调用成功");
    }

    @RequestMapping(value = "/writeStringNotSetContentType",method = RequestMethod.GET)
    @ApiOperation("响应一个字符串,不设置响应体数据类型")
    public void writeString(HttpServletResponse response,String baseUrl) throws IOException {
        HashMap<String, Object> stringObjectHashMap = HttpClientUtil.doGet(baseUrl);
        PrintWriter writer = response.getWriter();
        writer.write(stringObjectHashMap.toString());
        writer.close();
    }

    @RequestMapping(value = "/writeStringSetContentType",method = RequestMethod.GET)
    @ApiOperation("响应一个字符串，设置响应体数据类型")
    public void writeStringSetType(HttpServletResponse response,String baseUrl) throws IOException {
        HashMap<String, Object> stringObjectHashMap = HttpClientUtil.doGet(baseUrl);
        response.setContentType("application/json;charset = utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(stringObjectHashMap.toString());
    }

    @RequestMapping(value = "/writeMapWithDoubleQuotationMark",method = RequestMethod.GET)
    @ApiOperation("响应一个带有双引号的map")
    public void writeJSONString(HttpServletResponse response,String baseUrl) throws IOException {
        HashMap<String, Object> stringObjectHashMap = HttpClientUtil.doGet(baseUrl);
        log.info("stringObject{}",stringObjectHashMap);
        JSONObject jsonObject = JSONObject.fromObject(stringObjectHashMap);
        response.setContentType("application/json;charset = utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(jsonObject.toString());
    }

    @RequestMapping(value = "/writeMapWithoutDoubleQuotationMark",method = RequestMethod.GET)
    @ApiOperation("响应一个不带有双引号的map")
    public void writeJSONObject(HttpServletResponse response,String baseUrl) throws IOException {
        HashMap<String, String> stringHashMap = new HashMap<>();
        stringHashMap.put("hehe","hahah");
        stringHashMap.put("meme","lele");
        log.info("Object{}",stringHashMap);
        response.setContentType("application/json;charset = utf-8");
        JSONObject jsonObject = JSONObject.fromObject(stringHashMap);
        PrintWriter writer = response.getWriter();
        writer.write(jsonObject.toString());
    }

    @RequestMapping(value = "/requestWithUrlParam",method = RequestMethod.GET)
    @ApiOperation("远程调用带有url参数的接口")
    public void requestRemoteWithUrlParam(HttpServletResponse response,String baseUrl) throws IOException {
        StringBuilder urlParamBuilder = new StringBuilder();
        urlParamBuilder.append(baseUrl).append("?").append("name").append("=").append("Ted").append("&").
                append("age").append("=").append(18);
        HashMap<String, Object> stringObjectHashMap = HttpClientUtil.doGet(urlParamBuilder.toString());
//        JSONObject jsonObject = JSONObject.fromObject(stringObjectHashMap);
        response.setContentType("application/json;charset = utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(stringObjectHashMap.toString());
    }

    @RequestMapping(value = "/calledNotRequestParam",method = RequestMethod.GET)
    @ApiOperation("不带有请求参数的Get远程示例")
    public R<String> calledTestNotRequestParam(){
        return new R<>("调用成功了");
    }

    @RequestMapping(value = "/calledWithRequestUrl",method = RequestMethod.GET)
    @ApiOperation("带有URL请求参数的Get远程示例")
    public R<Map<String,Object>> calledTestWithRequestUrlParam(String name, Integer age){
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("name",name);
        stringObjectHashMap.put("age",age);
        return new R<>(stringObjectHashMap);
    }
}
