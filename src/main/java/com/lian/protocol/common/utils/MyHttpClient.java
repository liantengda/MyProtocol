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

    private static final String CONTENT_TYPE_JSON = "application/json";

    private static final String ENCODING_FORMAT = "UTF-8";


    public static JSONObject execute(String strUrl,Map<String,Object> params,
                                     Map<String,String> headers,String method,
                                     JSONObject requestBody){
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        ByteArrayOutputStream baos = null;
        try {
            switch (method){
                case "POST":
                    httpURLConnection =
                            buildHttpConnectionWithRequestBody(method, strUrl, headers, params,requestBody);
                    break;
                case "GET":
                    httpURLConnection =
                            buildHttpConnectionWithoutRequestBody(method, strUrl, headers, params);
            }
            //建立TCP连接
            httpURLConnection.connect();
            //发起HTTP请求
            inputStream = httpURLConnection.getInputStream();
            //得到响应状态码
            int responseCode = httpURLConnection.getResponseCode();
            baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int size = 0;
            while ((size = inputStream.read(bytes))!=-1){
                baos.write(bytes,0,size);
            }
            byte[] byteContent = baos.toByteArray();
            //从Http请求后的输入流中的到响应体
            String strContent = new String(byteContent,"utf-8");
            JSONObject jsonContent = JSONObject.fromObject(strContent);
            jsonContent.put("status",responseCode);
            return jsonContent;
        }catch (Exception e){
            e.printStackTrace();
            log.info("发生异常"+e.getMessage());
        }finally {
            try {
                if(inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(baos!=null){baos.close();}
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    httpURLConnection.disconnect();
                }
            }
        }
        return null;
    }

    public static HttpURLConnection buildHttpConnectionWithRequestBody(String method, String strUrl,
                                                                       Map<String,String> headers,
                                                                       Map<String,Object> urlParams,
                                                                       JSONObject requestBody) {
        HttpURLConnection httpURLConnection = null;
        try{
            //构建请求url
            URL  url= new URL(strUrl+builderUrlParam(urlParams));
            if(httpURLConnection == null){
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            for (Map.Entry<String,String> entry:headers.entrySet()){
                httpURLConnection.setRequestProperty(entry.getKey(),entry.getValue());
            }
            //向输出流中写入请求体
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            printWriter.write(requestBody.toString());
            printWriter.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        return httpURLConnection;
    }


    public static HttpURLConnection buildHttpConnectionWithoutRequestBody(String method, String strUri, Map<String,String> headers, Map<String,Object> params){
        HttpURLConnection httpURLConnection = null;
        URI uri = URI.create(strUri+builderUrlParam(params));
        try{
            if(httpURLConnection == null){
                httpURLConnection = (HttpURLConnection) uri.toURL().openConnection();
            }
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            for (Map.Entry<String,String> entry:headers.entrySet()){
                httpURLConnection.setRequestProperty(entry.getKey(),entry.getValue());
            }
        }catch (Exception e){

        }
        return httpURLConnection;
    }



    /**
     * 构建url请求参数
     * @param params
     * @return
     */
    public static String builderUrlParam(Map<String,Object> params) {
        StringBuilder urlParam = new StringBuilder();
        for (Map.Entry<String,Object> entry:params.entrySet()){
            try {
                urlParam.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                urlParam.append("=");
                urlParam.append(URLEncoder.encode(entry.getValue().toString(),"UTF-8"));
                urlParam.append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
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
