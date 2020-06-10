package com.lian.protocol.controller;

import com.lian.protocol.common.globalexception.pojo.response.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public R<Map> test(){

    }
}
