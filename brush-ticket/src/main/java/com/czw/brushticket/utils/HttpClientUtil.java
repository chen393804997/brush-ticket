package com.czw.brushticket.utils;

import javafx.scene.media.SubtitleTrack;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: czw
 * @create: 2018-10-02 23:19
 **/
public class HttpClientUtil {

    public static final Map<String,Object> map = new HashMap<>();

    public static HttpResponse doGetResponse(String url,Map<String,String> parms) {
        try {
            HttpClient client = new DefaultHttpClient();
            if (parms != null && !parms.isEmpty()){
                StringBuffer parm = new StringBuffer("?");
                int num = 0;
                for (String key : parms.keySet()){
                    if (num != 0){
                        parm.append("&");
                    }
                    parm.append(key+"="+parms.get(key));
                    num++;
                }
                url += parm;
            }
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
           return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean doGetByImg(String url,String path){
        try {
            HttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse httpResponse = client.execute(request);
            /**请求发送成功，并得到响应**/
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                byte[] data = EntityUtils.toByteArray(httpEntity);
                //图片存入磁盘
                FileOutputStream fos = new FileOutputStream(ConfigUtil.PATH+path);
                fos.write(data);
                fos.close();
                CookieStore cookieStore = ((DefaultHttpClient) client).getCookieStore();
                List<Cookie> cookies = cookieStore.getCookies();
                for (Cookie cookie : cookies){
                    if (cookie.getName().equals("_passport_ct")){
                        map.put(path,cookie.getValue());
                        break;
                    }
                }
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }

    public static String doPost(String url, List<NameValuePair> parms, String _passport_ct) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        HttpEntity entity = new UrlEncodedFormEntity(parms, "UTF-8");
        System.out.println(EntityUtils.toString(entity));

        post.setEntity(entity);
        post.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
        String cookie = "_passport_ct=_PASSPORT_CT; _passport_session=09b693d022ca4729a3537df16026edf62999; _jc_save_wfdc_flag=dc; RAIL_EXPIRATION=1538553423481; RAIL_DEVICEID=LYrsnSqls9ri1bI-jfbUwofPiDyKYhi-R75nPjYF61KwH0UqSTyHvzRMy1kv3rAp8djdydP3A_902sz3vHopZ7LPrERj_SB0qp2iIUmAhR0ABWDY1eZVwy3DUwOr0Wth82pqRJK3QcO5Vyn6DwCrO4os2-S2WqPV; _jc_save_toDate=2018-09-30; _jc_save_toStation=%u6DF1%u5733%2CSZQ; _jc_save_fromStation=%u91CD%u5E86%u5317%2CCUW; _jc_save_fromDate=2018-10-08; route=c5c62a339e7744272a54643b3be5bf64; BIGipServerpassport=786956554.50215.0000; BIGipServerotn=1072693770.64545.0000; BIGipServerpool_passport=384631306.50215.0000";
        cookie = cookie.replace("_PASSPORT_CT",_passport_ct);
        post.setHeader("Cookie",cookie);
        post.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        post.setHeader("Host","kyfw.12306.cn");
        post.setHeader("Accept-Language","zh-CN,zh;q=0.9");
        post.setHeader("Referer","https://kyfw.12306.cn/otn/login/init");
        post.setHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
        //5, 请求"实体" (封装请求参数的对象)
        //HttpEntity entity = new UrlEncodedFormEntity(parms,"UTF-8");
        System.out.println(EntityUtils.toString(post.getEntity()));
        HttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() == 200) {
            //得到响应的实体
            HttpEntity responseEntity = response.getEntity();
            String str = EntityUtils.toString(responseEntity);
            System.out.println("响应的内容为 : " + str);
            CookieStore cookieStore = ((DefaultHttpClient) client).getCookieStore();
            List<Cookie> cookies = cookieStore.getCookies();
            for (Cookie cookie1 : cookies){
                System.out.println("cookie"+cookie1.getValue());
            }
        }
        return null;
    }

    public static String doGet(String url) {
        try {
            HttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                CookieStore cookieStore = ((DefaultHttpClient) client).getCookieStore();
                List<Cookie> cookies = cookieStore.getCookies();
                for (Cookie cookie : cookies){
                    System.out.println("cookiename=="+cookie.getName());
                    System.out.println("cookieValue=="+cookie.getValue());
                }
                HttpEntity httpEntity = response.getEntity();
                /**读取服务器返回过来的json字符串数据**/
                //String strResult = EntityUtils.toString(response.getEntity());
                byte[] data = EntityUtils.toByteArray(httpEntity);
                //图片存入磁盘
                FileOutputStream fos = new FileOutputStream("/Users/Mac/Desktop/tmp/mpl.jpg");
                fos.write(data);
                fos.close();
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





    public static void main(String[] args) throws IOException {
       String str = "Ku36RTbFPgVC%2BCfwPz2a%2BsenNQUJtwQtjG36g3zZje4J3IytrQuIpdRgAtLVZVHhSkiSZ9hISaDi%0AiT0ciAK9IdvJQpENMWqW1fL5gfnHp1bvTYqm64DCZIiC5Gat6l83xCRJUNJiCw%2FHeIAzP%2BJUv0aT%0AK5BbLYTY4GsgLq3T4yeclDjTC3UrvUIRjbqsajKmlBhOChM5rkFhY0q9hgUUWRMPO%2FmCmyKiz5AF%0ArHhlGkhB%2BFbeakKQYwHOmNTXX84E";
       str = str.replaceAll("%2F","/").replaceAll("%0A","").replaceAll("%2B","+").replaceAll("%3D","");
        System.out.println(str);
    }

}
