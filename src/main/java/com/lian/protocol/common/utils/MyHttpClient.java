package com.lian.protocol.common.utils;


import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author Ted
 * @date 2020/6/12 13:25
 */
@Slf4j
public class MyHttpClient {

    private static final String HTTP_CONTENT_TYPE_JSON = "application/json";

    private static final String ENCODING_FORMAT = "UTF-8";


    public static JSONObject execute(String strUrl,Map<String,String> headers,String method,Map<String,Object> params,JSONObject requestBody) throws Exception {
        HttpURLConnection httpURLConnection = null;
        switch (method){
            case "POST":
                buildHttpConnectionByURI(method,strUrl,headers,params);
                break;
            case "GET":
                buildHttpConnectionByURI(method,strUrl,headers,params);
        }


        InputStream inputStream = null;
        ByteArrayOutputStream baos = null;
        try {
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int size = 0;
            while ((size = inputStream.read(bytes))!=-1){
                baos.write(bytes,0,size);
            }
            byte[] byteContent = baos.toByteArray();
            String strContent = new String(byteContent,"utf-8");
            JSONObject jsonContent = JSONObject.fromObject(strContent);
            System.out.println(jsonContent);
            return jsonContent;
        }catch (Exception e){
            log.info("发生异常"+e.getMessage());
        }finally {
            if(inputStream != null){inputStream.close();}
            if(baos!=null){baos.close();}
            httpURLConnection.disconnect();
        }
        return null;
    }

    public static HttpURLConnection buildHttpConnectionByBody(String method,String strUrl,Map<String,String> headers,Map<String,Object> urlParams,JSONObject jsonObject) throws IOException {
        HttpURLConnection httpURLConnection = null;
        URL  url= new URL(strUrl);
        if(httpURLConnection == null){
            httpURLConnection = (HttpURLConnection) url.openConnection();
        }
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);

        httpURLConnection.setRequestProperty("Content-Type","application/json");
        PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
        printWriter.write(jsonObject.toString());
        printWriter.flush();
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setConnectTimeout(10000);
        httpURLConnection.setReadTimeout(10000);
        httpURLConnection.setRequestProperty("Accept-Encoding","identity");
        for (Map.Entry<String,String> entry:headers.entrySet()){
            httpURLConnection.setRequestProperty(entry.getKey(),entry.getValue());
        }
        return httpURLConnection;
    }

    public static HttpURLConnection buildHttpConnectionByURL(String method,String strUrl, Map<String,String> headers) throws Exception {

        HttpURLConnection httpURLConnection = null;
        URL  url= new URL(strUrl);
        if(httpURLConnection == null){
            httpURLConnection = (HttpURLConnection) url.openConnection();
        }
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setConnectTimeout(10000);
        httpURLConnection.setReadTimeout(10000);
        httpURLConnection.setRequestProperty("Accept-Encoding","identity");
        for (Map.Entry<String,String> entry:headers.entrySet()){
            httpURLConnection.setRequestProperty(entry.getKey(),entry.getValue());
        }
        return httpURLConnection;
    }


    public static HttpURLConnection buildHttpConnectionByURI(String method,String strUri,Map<String,String> headers,Map<String,Object> params) throws IOException {
        HttpURLConnection httpURLConnection = null;
        URI uri = URI.create(strUri+builderUrlParam(params));
        if(httpURLConnection == null){
            httpURLConnection = (HttpURLConnection) uri.toURL().openConnection();
        }
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setConnectTimeout(10000);
        httpURLConnection.setReadTimeout(10000);
        httpURLConnection.setRequestProperty("Accept-Encoding","identity");
        for (Map.Entry<String,String> entry:headers.entrySet()){
            httpURLConnection.setRequestProperty(entry.getKey(),entry.getValue());
        }
        return httpURLConnection;
    }

    /**
     * 构建url请求参数
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String builderUrlParam(Map<String,Object> params) throws UnsupportedEncodingException {
        StringBuilder urlParam = new StringBuilder();
        for (Map.Entry<String,Object> entry:params.entrySet()){
            urlParam.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            urlParam.append("=");
            urlParam.append(URLEncoder.encode(entry.getValue().toString(),"UTF-8"));
            urlParam.append("&");
        }
        String result = urlParam.toString();
        return result.length()>0?"?"+result.substring(0,result.length()-1):result;
    }

    public static void main(String[] args) {
        byte[] bytes = {72,84,84,80,47,49,46,49,32,50,48,48,32,13,10,65,99,99,101,115,115,45,99,111,110,116,114,111,108,45,65,108,108,111,119,45,79,114,105,103,105,110,58,32,42,13,10,65,99,99,101,115,115,45,67,111,110,116,114,111,108,45,65,108,108,111,119,45,77,101,116,104,111,100,115,58,32,71,69,84,44,80,79,83,84,44,79,80,84,73,79,78,83,44,80,85,84,44,68,69};

        String s = new String(bytes);

        String hanzi = "汉字";

        byte[] bytes1 = hanzi.getBytes();
        for (byte b:bytes1){
            System.out.print(b+" ");
        }
        System.out.println();
        System.out.println(s);
    }

}
