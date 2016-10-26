package com.pzl.dreamer.net;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-26]
 */
public class HTTPClientUtil {
    public static final String TAG = "HTTPClientUtil";

    private static HTTPClientUtil instance;

    private HTTPClientUtil() {

    }

    public synchronized static HTTPClientUtil getInstance() {
        if (instance == null) {
            instance = new HTTPClientUtil();
        }
        return instance;
    }

    /**
     * get 请求
     *
     * @param url
     * @return
     */
    public String doGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        HttpParams params = httpGet.getParams();
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream is = httpEntity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder = new StringBuilder();
                String response;
                String readLine;
                while ((readLine = br.readLine()) != null) {
                    stringBuilder.append(readLine);
                }
                response = stringBuilder.toString();
                is.close();
                br.close();
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


}
