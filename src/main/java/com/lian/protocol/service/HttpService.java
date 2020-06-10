package com.lian.protocol.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ted
 * @date 2020/6/10 16:32
 */
public interface HttpService {

    void test(String baseUrl, HttpServletResponse response) throws IOException;

}
