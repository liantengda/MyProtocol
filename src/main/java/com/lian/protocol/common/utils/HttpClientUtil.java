package com.lian.protocol.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static HashMap<String, Object> doGet(String url) {
        //用来返回状态码的内部类
        HashMap<String, Object> map = new HashMap<>();
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);
        // 响应模型
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

            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            log.info("HttpClientUtil响应状态为:" + response.getStatusLine());
            //获取data内容
            String data = null;
            data = EntityUtils.toString(responseEntity, "utf-8");
            map.put("data", data);
            log.info("HttpClientUtil返回的数据" + data);
            return map;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
            HttpPost post = new HttpPost(url);
            //httpClient 4.5版本的超时参数配置
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000).build();
            post.setConfig(requestConfig);
            //往BODY里填充数据主体
            StringEntity entitys = new StringEntity(jsonParam, "utf-8");
            entitys.setContentEncoding("UTF-8");
            if (contentType != null) {
                if (contentType.equals("xml"))
                    entitys.setContentType("application/xml");
                if (contentType.equals("json"))
                    entitys.setContentType("application/json");
            }
            post.setEntity(entitys);
            HttpResponse response = httpclient.execute(post);
            //获取data内容
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
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (paramMap != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String, String> param : paramMap.entrySet()) {
                    paramList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                entity.setContentEncoding("UTF-8");
                httpPost.setEntity(entity);
            }
            httpPost.setConfig(builderRequestConfig());
            // 执行http请求
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
     * patch请求
     *
     * @param url
     * @return
     */
    public static String doPatch(String url) {
        try {
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
            HttpPatch patch = new HttpPatch(url);
            //httpClient 4.5版本的超时参数配置
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000).build();
            patch.setConfig(requestConfig);
            HttpResponse response = httpclient.execute(patch);
            //获取data内容
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
     * delete参数
     * @param url
     * @return
     */
    public static String doDelete(String url) {
        try {
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
            HttpDelete delete = new HttpDelete(url);
            //httpClient 4.5版本的超时参数配置
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000).build();
            delete.setConfig(requestConfig);
            HttpResponse response = httpclient.execute(delete);
            //获取data内容
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
}
