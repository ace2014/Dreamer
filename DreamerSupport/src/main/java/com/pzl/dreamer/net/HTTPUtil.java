package com.pzl.dreamer.net;

import android.util.Log;

import com.pzl.dreamer.utils.IOUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-26]
 */
public class HTTPUtil {
    public static final String TAG = "HTTPUtil";

    private static HTTPUtil instance;
    private HttpConfig defaultHttpConfig;

    private HTTPUtil() {
        defaultHttpConfig = new HttpConfig();
        defaultHttpConfig.connectTimeout = 8000;
        defaultHttpConfig.readTimeout = 5000;
    }

    public synchronized static HTTPUtil getInstance() {
        if (instance == null) {
            instance = new HTTPUtil();
        }
        return instance;
    }

    /**
     * HttpClient Get 请求 (Apache open source)
     *
     * @param url
     * @return
     */
    public String httpClientGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        HttpParams params = httpGet.getParams();
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream is = httpEntity.getContent();
                String response = IOUtil.readString(is);
                Log.d(TAG, "Get请求(" + url + ")response:" + response);
                return response;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HttpClient Post 请求 (Apache open source)
     *
     * @param url
     * @param params
     * @return
     */
    public String httpClientPost(String url, String params) {
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        StringEntity entity = null;
        try {
            entity = new StringEntity(params.toString(), "utf-8");//解决中文乱码问题
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream is = httpEntity.getContent();
                String response = IOUtil.readString(is);
                Log.d(TAG, "Post请求(" + url + ")response:" + response);
                return response;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HttpURLConnection get (java 网络层)
     *
     * @param getUrl
     * @return
     */
    public String doGet(String getUrl) {
        String result = null;
        try {
            URL url = new URL(getUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream is = httpURLConnection.getInputStream();
            result = IOUtil.readString(is);
            httpURLConnection.disconnect();
            Log.d(TAG, "Get请求(" + url + ")response:" + result);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * HttpURLConnection post (java 网络层)
     *
     * @param urlPost
     * @param params
     */
    public String doPost(String urlPost, String params) {
        Log.d(TAG, "Post参数:" + params);
        String result = null;
        try {
            URL url = new URL(urlPost);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");// 提交模式
            conn.setConnectTimeout(defaultHttpConfig.connectTimeout);//连接超时 单位毫秒
            conn.setReadTimeout(defaultHttpConfig.readTimeout);//读取超时 单位毫秒
            conn.setDoOutput(true);// 默认值false,是否允许发送数据(是否允许post数据参数)
            conn.setDoInput(true);//默认值true,是否允许接收数据
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStream os = conn.getOutputStream();
            byte[] bypes = params.toString().getBytes();
            os.write(bypes);//输入参数
            os.flush();
            os.close();
            InputStream inStream = conn.getInputStream();
            result = IOUtil.readString(inStream);
            Log.d(TAG, "Post请求(" + url + ")response:" + result);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    static class HttpConfig {
        public int connectTimeout;
        public int readTimeout;
    }


}
