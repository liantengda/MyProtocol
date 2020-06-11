package com.lian.protocol.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * httpclient工具类
 *
 * @author ted
 * @date 2019年12月2日
 */
@Slf4j
public class HttpClientUtil {
    final static int TIMEOUT = 10000;
    final static int TIMEOUT_MSEC = 5 * 10000;

    public static Map<String, Object> myGet(String url,Map<String,String> headers) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        Set<String> strings = headers.keySet();
        strings.forEach(key->httpGet.setHeader(key,headers.get(key)));
        response = httpClient.execute(httpGet);
        HttpEntity responseEntity = response.getEntity();
        log.info("HttpClientUtil响应状态为:" + response.getStatusLine());
        //获取data内容
        String data = null;
        data = EntityUtils.toString(responseEntity, "utf-8");
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", data);
        log.info("HttpClientUtil返回的数据" + data);
        return map;
    }

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static HashMap<String, Object> doGet(String url) {
        HashMap<String, Object> map = new HashMap<>();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(30000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(30000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(30000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();

            httpGet.setConfig(requestConfig);
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            log.info("HttpClientUtil响应状态为:" + response.getStatusLine());
            String data = null;
            data = EntityUtils.toString(responseEntity, "utf-8");
            map.put("data", data);
            log.info("HttpClientUtil返回的数据" + data);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * json串入参的post请求
     *
     * @param url
     * @param jsonParam   参数字符串
     * @param contentType 参数类型{xml，json}
     * @return
     */
    public static String doPost(String url, String jsonParam, String contentType) {
        try {
            CloseableHttpClient httpClient = buildSSLContext();
            HttpPost post = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000).build();
            post.setConfig(requestConfig);
            StringEntity entitys = new StringEntity(jsonParam, "utf-8");
            entitys.setContentEncoding("UTF-8");
            if (contentType != null) {
                if (contentType.equals("xml"))
                    entitys.setContentType("application/xml");
                if (contentType.equals("json"))
                    entitys.setContentType("application/json");
            }
            post.setEntity(entitys);
            HttpResponse response = httpClient.execute(post);
            String data = EntityUtils.toString(response.getEntity(), "utf-8");
            log.info("HttpClientUtil返回的数据" + data);
            return data;
        } catch (ClientProtocolException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * map入参的post请求
     *
     * @param url
     * @param paramMap
     * @return
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> paramMap) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            if (paramMap != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String, String> param : paramMap.entrySet()) {
                    paramList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                entity.setContentEncoding("UTF-8");
                httpPost.setEntity(entity);
            }
            httpPost.setConfig(builderRequestConfig());
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                throw e;
            }
        }

        return resultString;
    }


    /**
     * patch请求
     *
     * @param url
     * @return
     */
    public static String doPatch(String url) {
        try {
            CloseableHttpClient httpClient = buildSSLContext();
            HttpPatch patch = new HttpPatch(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000).build();
            patch.setConfig(requestConfig);
            HttpResponse response = httpClient.execute(patch);
            String data = EntityUtils.toString(response.getEntity(), "utf-8");
            log.info("HttpClientUtil返回的数据" + data);
            return data;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * delete参数
     * @param url
     * @return
     */
    public static String doDelete(String url) {
        try {
            CloseableHttpClient httpClient = buildSSLContext();
            HttpDelete delete = new HttpDelete(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000).build();
            delete.setConfig(requestConfig);
            HttpResponse response = httpClient.execute(delete);
            String data = EntityUtils.toString(response.getEntity(), "utf-8");
            log.info("HttpClientUtil返回的数据" + data);
            return data;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 请求参数配置
     *
     * @return
     */
    private static RequestConfig builderRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(TIMEOUT_MSEC)
                .setConnectionRequestTimeout(TIMEOUT_MSEC)
                .setSocketTimeout(TIMEOUT_MSEC).build();
    }

    /**
     * 忽略安全证书设置
     *
     * @return
     */
    public static CloseableHttpClient buildSSLContext(){
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            }).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        CloseableHttpClient httpclient = HttpClients.custom().setSSLContext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        return httpclient;
    }
}
