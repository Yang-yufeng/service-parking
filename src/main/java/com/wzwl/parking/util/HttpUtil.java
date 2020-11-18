package com.wzwl.parking.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName HttpUtil
 * @Description 第三方请求封装
 * @Author yangwu
 * @Date 2020/11/4 16:51
 * @Version 1.0
 */
@Slf4j
public class HttpUtil {

    private static final Logger LOGGER=LoggerFactory.getLogger(HttpUtil.class);

    /**
     * httpGet请求
     *
     * @param url 地址
     * @return
     */
    public static String doGetRequest(String url) {
        CloseableHttpClient httpclient=null;
        CloseableHttpResponse response=null;
        try {
            httpclient=HttpClients.createDefault();
            // 创建httpget.
            HttpGet httpget=new HttpGet(url);
            // 执行get请求.
            response=httpclient.execute(httpget);
            // 获取响应实体
            HttpEntity entity=response.getEntity();
            if (entity != null) {
                // 打印响应内容
                return EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            LOGGER.error("http请求异常：", e);
        } finally {
            try {
                if (null != httpclient) {
                    httpclient.close();
                }
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                LOGGER.error("http请求异常：", e);
            }
        }
        return null;
    }

    /**
     * 处理post请求.
     *
     * @param url    请求路径
     * @param params 参数
     * @return json
     */
    public static String doPostRequest(String url, Map<String, Object> params) {
        // 实例化httpClient
        CloseableHttpClient httpclient=HttpClients.createDefault();
        // 实例化post方法
        HttpPost httpPost=new HttpPost(url);
        // 处理参数
        List<NameValuePair> nvps=new ArrayList<NameValuePair>();
        Set<String> keySet=params.keySet();
        for (String key : keySet) {
            if (params.get(key) != null) {
                nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
            }
        }
        // 结果
        CloseableHttpResponse response=null;
        String content="";
        try {
            // 提交的参数
            UrlEncodedFormEntity uefEntity=new UrlEncodedFormEntity(nvps, "UTF-8");
            // 将参数给post方法
            httpPost.setEntity(uefEntity);
            // 执行post方法
            response=httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                content=EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (ClientProtocolException e) {
            LOGGER.error("http请求异常：", e);
        } catch (IOException e) {
            LOGGER.error("http请求异常：", e);
        }
        return content;
    }

    public static String doPostRequestJson(String url, JSONObject json) {
        // 实例化httpClient
        CloseableHttpClient httpclient=HttpClients.createDefault();
        // 实例化post方法
        HttpPost httpPost=new HttpPost(url);
        String content= "";
        try {
            StringEntity se=new StringEntity(json.toString(), "UTF-8");
            se.setContentType("application/json");
            se.setContentEncoding("UTF-8");
            httpPost.setHeader("version","1.0.0");
            httpPost.setEntity(se);
            // 执行post方法
            CloseableHttpResponse response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                content=EntityUtils.toString(response.getEntity(), "utf-8");
            }else {
                LOGGER.error("请求异常，状态码为:{}",response.getStatusLine().getStatusCode());
            }
        } catch (HttpResponseException e) {
            LOGGER.error("请求异常:", e);
            LOGGER.error("请求异常:", e.getCause());
            LOGGER.error("请求异常:", e.getMessage());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }



    @SuppressWarnings("deprecation")
    public static String doPost(String url, Map<String, String> param, String charset) {

        StringBuffer buffer=new StringBuffer();
        if (param != null && !param.isEmpty()) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                buffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue())).append("&");
            }
        }
        buffer.deleteCharAt(buffer.length() - 1);
        PrintWriter out=null;
        BufferedReader in=null;
        String result="";
        try {
            URL realUrl=new URL(url);
            // 打开和URL之间的连接
            URLConnection conn=realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out=new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(buffer);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in=new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            String line;
            while ((line=in.readLine()) != null) {
                result+=line;
            }
        } catch (Exception e) {
            LOGGER.error("请求异常:", e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                LOGGER.error("关闭http请求异常:", ex);
            }
        }
        return result;
    }


    /**
     * 找车系统
     * @param url
     * @param user
     * @param pwd
     * @param data
     * @return
     */
    public static String doFindCarPostRequest(String url, String user,String pwd,String data) {
        CloseableHttpClient httpclient=HttpClients.createDefault();
        // 实例化post方法
        HttpPost httpPost=new HttpPost(url);
        String content= "";
        CloseableHttpResponse response = null;
        try {
            JSONObject dataJson = new JSONObject();
            dataJson.put("data",data);
            log.info("此次请求地址为【{}】,参数为【{}】",url,dataJson.toJSONString());
            StringEntity se=new StringEntity(dataJson.toJSONString(), "UTF-8");
            se.setContentType("application/json");
            se.setContentEncoding("UTF-8");
            httpPost.setHeader("user",user);
            httpPost.setHeader("pwd",pwd);
            httpPost.setEntity(se);
            // 执行post方法
            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                content=EntityUtils.toString(response.getEntity(), "utf-8");
            }else {
                LOGGER.error("请求异常，状态码为:{}",response.getStatusLine().getStatusCode());
            }

        } catch (HttpResponseException e) {
            LOGGER.error("请求异常:", e);
            LOGGER.error("请求异常:", e.getCause());
            LOGGER.error("请求异常:", e.getMessage());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * http发送post请求
     * @param url
     * @param jsonObject
     * @return
     */
    public static JSONObject doPost(String url, JSONObject jsonObject) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;

        try {
            StringEntity s = new StringEntity(jsonObject.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(entity);
                response = JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

}

