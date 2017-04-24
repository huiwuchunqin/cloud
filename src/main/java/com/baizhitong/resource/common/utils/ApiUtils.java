package com.baizhitong.resource.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiUtils {
    private static Log          log          = LogFactory.getLog(ApiUtils.class);

    private static String       host         = "112.80.45.178";
    private static int          port         = 59009;
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static String        apiHost      = "http://api.sipedu.org";
    // public static String apiHost = "http://10.31.0.81:8280";
    private static String       token        = "Bearer 58934a15b6d6906e566c29696daaf8";
    // private static String token = "cabc3ac9f09cf6b73381d9a1bb39d24";
    public static boolean       userProxy    = false;                                  // just for
                                                                                       // test

    public static List<Map<String, Object>> get4List(String url) {
        List<Map<String, Object>> dataList = null;
        try {
            Content content;
            if (userProxy) {
                HttpHost httpHost = new HttpHost(host, port);
                content = Request.Get(url).viaProxy(httpHost).setHeader(HttpHeaders.AUTHORIZATION, token)
                                .setHeader("Accept", "application/json").connectTimeout(3000).execute().returnContent();
            } else {
                content = Request.Get(url).setHeader(HttpHeaders.AUTHORIZATION, token)
                                .setHeader("Accept", "application/json").connectTimeout(9000).execute().returnContent();
            }
            String contentStr = content.asString();
            Map resultMap = JSON.parseObject(contentStr, Map.class);

            Object data = resultMap.get("data");

            // 濡傛灉涓哄垎椤碉紝鍒欐牸寮忎负map
            if (data instanceof Map) {
                dataList = (List<Map<String, Object>>) ((Map<String, Object>) resultMap.get("data")).get("list");
                // 娌℃湁鐨勮瘽锛屽垯涓篖ist
                if (dataList == null) {
                    dataList = (List<Map<String, Object>>) ((Map<String, Object>) resultMap.get("data")).get("List");
                }
            } else {
                dataList = (List<Map<String, Object>>) resultMap.get("data");
            }

        } catch (Exception e) {
            log.error("", e);
        }
        return dataList;
    }

    public static Object get4Obj(String url) {
        try {
            Content content;
            if (userProxy) {
                HttpHost httpHost = new HttpHost(host, port);
                content = Request.Get(url).viaProxy(httpHost).setHeader(HttpHeaders.AUTHORIZATION, token)
                                .setHeader("Accept", "application/json").connectTimeout(3000).execute().returnContent();
            } else {
                content = Request.Get(url).setHeader(HttpHeaders.AUTHORIZATION, token)
                                .setHeader("Accept", "application/json").connectTimeout(3000).execute().returnContent();
            }
            String contentStr = content.asString();
            Map resultMap = JSON.parseObject(contentStr, Map.class);
            // Map resultMap = (Map) getRestfulService(url, new HashMap(), App.token);
            Object data = null;
            if (resultMap == null) {
                System.err.println("empty data");
            } else {
                data = resultMap.get("data");
            }
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object post4Obj(String url, HttpEntity entity) {
        try {
            Content content;
            if (userProxy) {
                HttpHost httpHost = new HttpHost(host, port);
                content = Request.Post(url).body(entity).viaProxy(httpHost).setHeader(HttpHeaders.AUTHORIZATION, token)
                                .setHeader("Accept", "application/json").setHeader("Content-Type", "application/json")
                                .connectTimeout(3000).execute().returnContent();

            } else {
                content = Request.Post(url).body(entity).setHeader(HttpHeaders.AUTHORIZATION, token)
                                .setHeader("Accept", "application/json").setHeader("Content-Type", "application/json")
                                .connectTimeout(3000).execute().returnContent();
            }
            String contentStr = content.asString();
            Map resultMap = objectMapper.readValue(contentStr, HashMap.class);
            return resultMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Map<String, Object>> post4List(String url, HttpEntity entity) {
        try {
            Content content;
            if (userProxy) {
                HttpHost httpHost = new HttpHost(host, port);
                content = Request.Post(url).body(entity).viaProxy(httpHost).setHeader(HttpHeaders.AUTHORIZATION, token)
                                .setHeader("Accept", "application/json").connectTimeout(3000).execute().returnContent();
            } else {
                content = Request.Post(url).body(entity).setHeader(HttpHeaders.AUTHORIZATION, token)
                                .setHeader("Accept", "application/json").connectTimeout(3000).execute().returnContent();
            }
            String contentStr = content.asString();
            Map resultMap = objectMapper.readValue(contentStr, HashMap.class);

            Object data = resultMap.get("data");

            List<Map<String, Object>> dataList;
            // 濡傛灉涓哄垎椤碉紝鍒欐牸寮忎负map
            if (data instanceof Map) {
                dataList = (List<Map<String, Object>>) ((Map<String, Object>) resultMap.get("data")).get("list");

                // 娌℃湁鐨勮瘽锛屽垯涓篖ist
                if (dataList == null) {
                    dataList = (List<Map<String, Object>>) ((Map<String, Object>) resultMap.get("data")).get("List");
                }
            } else {
                dataList = (List<Map<String, Object>>) resultMap.get("data");
            }
            return dataList;

        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    /**
     * post得到远程接口的数据 ()<br>
     * 
     * @param url 地址
     * @param jsonObj 传参
     * @return map
     */
    public static Map<String, Object> postResponseObj(String url, JSONObject jsonObj) {
        url = ApiUtils.apiHost + url;
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonObj.toString());
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (Map<String, Object>) ApiUtils.post4Obj(ApiUtils.apiHost + url, entity);
    }

    /**
     * get得到远程数据接口数据 ()<br>
     * 
     * @param url 地址
     * @return 返回值
     */
    public static Map<String, Object> getResponseObj(String url) {
        url = ApiUtils.apiHost + url;
        return (Map<String, Object>) ApiUtils.get4Obj(url);
    }

    /**
     * get得到远程数据接口数据 ()<br>
     * 
     * @param url 地址
     * @return 返回值
     */
    public static List<Map<String, Object>> getResponseList(String url) {
        url = ApiUtils.apiHost + url;
        return ApiUtils.get4List(url);
    }

    /**
     * 得到远程接口的数据 ()<br>
     * 
     * @param url 地址
     * @param jsonObj 传参
     * @return map
     */
    public static List<Map<String, Object>> postResponseList(String url, JSONObject jsonObj) {
        url = ApiUtils.apiHost + url;
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonObj.toString());
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ApiUtils.post4List(ApiUtils.apiHost + url, entity);
    }
}
