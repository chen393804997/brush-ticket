package com.czw.brushticket.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.czw.brushticket.HttpUtil.OkHttpUtils;
import com.czw.brushticket.service.BrushticketService;
import com.czw.brushticket.utils.ConfigUtil;
import com.czw.brushticket.utils.FileUtil;
import com.czw.brushticket.utils.HttpClientUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * @author: czw
 * @Date: 2018-12-03 17:33
 **/
@Service
public class BrushticketServiceImpl implements BrushticketService {

    public @Bean OkHttpUtils okHttpUtils(){
        return new OkHttpUtils();
    }

    @Autowired
    private OkHttpUtils okHttpUtils;

    @Override
    public JSONObject getImg() {
        String uuid = UUID.randomUUID().toString();
        JSONObject flag = HttpClientUtil.doGetByImg(ConfigUtil.GET_IMG,uuid);
        flag.put("imgName",flag.getString("src"));
        flag.put("uuid",uuid);
        return flag;
    }

    @Override
    public void login(String username, String password, String position, String uuid) throws IOException {
        if (StringUtils.isEmpty(position)){
            return;
        }
        String answer = position.substring(1);

        //图片标识
        String _passport_ct = (String) HttpClientUtil.map.get(uuid);
        System.out.println(_passport_ct);
        //验证码校验
        List<NameValuePair> parms = new ArrayList<NameValuePair>(5);
        NameValuePair valuePair = new BasicNameValuePair("answer",answer);
        parms.add(valuePair);
        NameValuePair login_site = new BasicNameValuePair("login_site","E");
        parms.add(login_site);
        NameValuePair rand = new BasicNameValuePair("rand","sjrand");
        parms.add(rand);
        HttpClientUtil.doPost(ConfigUtil.CAPTCHA_CHECK,parms,_passport_ct);
        //登录
        List<NameValuePair> parms1 = new ArrayList<>();
        NameValuePair nameValuePair = new BasicNameValuePair("username",username);
        NameValuePair nameValuePair1 = new BasicNameValuePair("password",password);
        NameValuePair nameValuePair2 = new BasicNameValuePair("appid","otn");
        parms1.add(nameValuePair);
        parms1.add(nameValuePair1);
        parms1.add(nameValuePair2);
        HttpClientUtil.doPost(ConfigUtil.LOGIN,parms1,_passport_ct);
    }
}
