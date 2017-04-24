package com.baizhitong.resource.common.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * HTTP工具类
 * 
 * @author lixiangyang
 * 
 */
public class HttpUtils {

    private static Log                                log                          = LogFactory.getLog(HttpUtils.class);

    /**
     * 定义编码格式 UTF-8
     */
    public static final String                        URL_PARAM_DECODECHARSET_UTF8 = "UTF-8";

    /**
     * 定义编码格式 GBK
     */
    public static final String                        URL_PARAM_DECODECHARSET_GBK  = "GBK";

    private static final String                       URL_PARAM_CONNECT_FLAG       = "&";

    private static final String                       EMPTY                        = "";

    private static MultiThreadedHttpConnectionManager connectionManager            = null;

    private static int                                connectionTimeOut            = 25000;

    private static int                                socketTimeOut                = 25000;

    private static int                                maxConnectionPerHost         = 20;

    private static int                                maxTotalConnections          = 20;

    private static HttpClient                         client;

    static {
        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
        connectionManager.getParams().setSoTimeout(socketTimeOut);
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
        connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
        client = new HttpClient(connectionManager);
    }

    /**
     * POST方式提交数据
     * 
     * @param url 待请求的URL
     * @param params 要提交的数据
     * @param enc 编码
     * @return 响应结果
     * @throws IOException IO异常
     */
    public static String URLPost(String url, Map<String, String> params, String enc) {

        String response = EMPTY;
        PostMethod postMethod = null;
        try {
            postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            // 将表单的值放入postMethod中
            if(params!=null){
            // 执行postMethod
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    String value = params.get(key);
                    postMethod.addParameter(key, value);
                }
            }
            int statusCode = client.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                response = postMethod.getResponseBodyAsString();
                log.info("回调信息：" + response);
            } else {
                log.error("响应状态码 = " + postMethod.getStatusCode());
            }
        } catch (HttpException e) {
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
        } catch (IOException e) {
            log.error("发生网络异常", e);
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
                postMethod = null;
            }
        }

        return response;
    }

    /**
     * 二进制数组转文件
     * 
     * @param bfile
     * @return
     */
    public static File ByteAry2File(byte[] bfile, String suffix) {
        File f = null;
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            String dir = System.getProperty("java.io.tmpdir");
            log.error("临时文件目录:" + dir + File.separator + new Date().getTime() + suffix);
            f = new File(dir + File.separator + new Date().getTime() + suffix);
            File parent = f.getParentFile();

            if (!parent.exists()) {
                parent.mkdirs();
            }
            if (!f.exists()) {
                f.createNewFile();
            }
            log.debug("写入文件");
            fos = new FileOutputStream(f);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    log.error(e1.getLocalizedMessage(), e1);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    log.error(e1.getMessage(), e1);
                }
            }
        }
        return f;
    }

    /**
     * post文件上传
     * 
     * @param URL_STR 地址
     * @param param 参数列表
     * @param file 文件
     * @param fileKey 文件key值
     * @return
     */
    public static String URLPostUpload(String URL_STR, Map<String, String> param, File file, String fileKey) {
        String responseString = EMPTY;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();

            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(URL_STR);
            httpPost.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 120);
            log.error(file.getName() + "\n");
            // 把文件转换成流对象FileBody
            FileBody bin = new FileBody(file);

            MultipartEntity reqEntity = new MultipartEntity();
            // 相当于<input type="file" name="file"/>
            reqEntity.addPart(fileKey, bin);

            if (param != null) {
                // 相当于<input type="text" name="userName" value=userName>
                for (String key : param.keySet()) {
                    reqEntity.addPart(key,
                                    new StringBody(param.get(key), ContentType.create("text/plain", Consts.UTF_8)));
                }

            }
            httpPost.setEntity(reqEntity);

            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);

            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                // 打印响应长度

                // 打印响应内容
                responseString = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));

            }

            // 销毁
            EntityUtils.consume(resEntity);
        } catch (Exception e) {
            log.error("上传出错", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*
             * if(file!=null) file.delete();
             */
        }
        return responseString;
    }

    /**
     * GET方式提交数据 并返回状态码
     * 
     * @param url 待请求的URL
     * @param enc 编码
     * @return 响应结果
     * @throws IOException IO异常
     */
    public static Integer URLGet(String url, String enc) {

        String response = EMPTY;
        GetMethod getMethod = null;
        StringBuffer strtTotalURL = new StringBuffer(EMPTY);
        strtTotalURL.append(url);
        log.info("GET请求URL = \n" + strtTotalURL.toString());
        int statusCode = 0;
        try {
            getMethod = new GetMethod(url);
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 1000 * 20);
            // 执行getMethod
            statusCode = client.executeMethod(getMethod);

        } catch (HttpException e) {
            log.info("GET请求URL = \n" + strtTotalURL.toString());
            log.info("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
        } catch (IOException e) {
            log.info("GET请求URL = \n" + strtTotalURL.toString());
            log.info("发生网络异常", e);
        } finally {
            if (getMethod != null) {
                getMethod.abort();
                getMethod = null;
            }
        }

        return statusCode;
    }

    /**
     * GET方式提交数据
     * 
     * @param url 待请求的URL
     * @param params 要提交的数据
     * @param enc 编码
     * @return 响应结果
     * @throws IOException IO异常
     */
    public static String URLGet(String url, Map<String, String> params, String enc) {

        String response = EMPTY;
        GetMethod getMethod = null;
        StringBuffer strtTotalURL = new StringBuffer(url);

        if (params != null) {
            if (strtTotalURL.indexOf("?") == -1) {
                strtTotalURL.append("?").append(getUrl(params, enc));
            } else {
                strtTotalURL.append("&").append(getUrl(params, enc));
            }
        }
        log.debug("GET请求URL = \n" + strtTotalURL.toString());

        try {
            getMethod = new GetMethod(strtTotalURL.toString());
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            // 执行getMethod
            int statusCode = client.executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK) {
                response = getMethod.getResponseBodyAsString();
            } else {
                log.debug("响应状态码 = " + getMethod.getStatusCode());
            }
        } catch (HttpException e) {
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题\n请求url为:" + strtTotalURL.toString() + "\n", e);
        } catch (IOException e) {
            log.error("发生网络异常\n请求url为:" + strtTotalURL.toString() + "\n", e);
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
                getMethod = null;
            }
        }

        return response;
    }

    /**
     * 据Map生成URL字符串
     * 
     * @param map Map
     * @param valueEnc URL编码
     * @return URL
     */
    private static String getUrl(Map<String, String> map, String valueEnc) {

        if (null == map || map.keySet().size() == 0) {
            return (EMPTY);
        }
        StringBuffer url = new StringBuffer();
        Set<String> keys = map.keySet();
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
            String key = it.next();
            if (map.containsKey(key)) {
                String val = map.get(key);
                String str = val != null ? val : EMPTY;
                try {
                    str = URLEncoder.encode(str, valueEnc);
                } catch (UnsupportedEncodingException e) {
                    log.info(e.getMessage(), e);
                }
                url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
            }
        }
        String strURL = EMPTY;
        strURL = url.toString();
        if (URL_PARAM_CONNECT_FLAG.equals(EMPTY + strURL.charAt(strURL.length() - 1))) {
            strURL = strURL.substring(0, strURL.length() - 1);
        }

        return (strURL);
    }

    public static byte[] URLPostByte(String url, Map<String, String> params, String enc) {

        byte[] responseByte = null;
        PostMethod postMethod = null;
        try {
            postMethod = new PostMethod(url);

            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
            // postMethod.setp
            // 将表单的值放入postMethod中
            if (params != null) {
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    String value = params.get(key);
                    postMethod.addParameter(key, value);
                }
            }

            // 执行postMethod
            int statusCode = client.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                responseByte = postMethod.getResponseBody();
            } else {
                log.info("响应状态码 = " + postMethod.getStatusCode());
            }
        } catch (HttpException e) {
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
        } catch (IOException e) {
            log.error("发生网络异常", e);
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
                postMethod = null;
            }
        }

        return responseByte;
    }

    public static byte[] URLGetByte(String url, Map params, String enc) {
        String response = EMPTY;
        byte[] responseByte = null;
        GetMethod getMethod = null;
        StringBuffer strtTotalURL = new StringBuffer(url);

        if (params != null) {
            if (strtTotalURL.indexOf("?") == -1) {
                strtTotalURL.append(url).append("?").append(getUrl(params, enc));
            } else {
                strtTotalURL.append(url).append("&").append(getUrl(params, enc));
            }
        }
        log.debug("GET请求URL = \n" + strtTotalURL.toString());

        try {
            getMethod = new GetMethod(strtTotalURL.toString());
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            // 执行getMethod
            int statusCode = client.executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK) {
                responseByte = getMethod.getResponseBody();
            } else {
                log.debug("响应状态码 = " + getMethod.getStatusCode());
            }
        } catch (HttpException e) {
            log.info("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
        } catch (IOException e) {
            log.info("发生网络异常", e);
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
                getMethod = null;
            }
        }

        return responseByte;
    }

    public static void main(String[] arg) {
        String url="http://www.jyeoo.com/math/ques/partialques?q=b2cbc1f6-733a-4969-b657-f41c66f742f7~fe2ad366-cda0-4065-9245-e2033d893707~"
                        + "&f=0"
                        +"&ct=9"                    
                        +"&dg=1"
                        +"&fg=0"
                        +"&po=0"
                        +"&pd=1"
                        +"&pi=1"       
                        +"&lbs="
                        +"&so=0"
                        +"&r=0.9607077600852558";
         URLPost(url,null, HttpUtils.URL_PARAM_DECODECHARSET_UTF8);

    }
}