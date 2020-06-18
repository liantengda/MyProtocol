package com.lian.protocol.service.impl;

import com.lian.protocol.common.utils.HttpClientUtil;
import com.lian.protocol.service.ApacheHttpClientService;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author Ted
 * @date 2020/6/10 16:32
 */
@Service
public class ApacheHttpClientServiceImpl implements ApacheHttpClientService {


    @Override
    public void test(String baseUrl, HttpServletResponse response) throws IOException {
        HashMap<String, Object> stringObjectHashMap = HttpClientUtil.doGet(baseUrl);
        String string = JSONObject.toJSONString(stringObjectHashMap);
        PrintWriter writer = response.getWriter();
        writer.write(string);
        writer.close();
    }

}
