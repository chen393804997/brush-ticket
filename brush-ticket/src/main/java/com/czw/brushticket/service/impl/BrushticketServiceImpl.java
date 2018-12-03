package com.czw.brushticket.service.impl;

import com.czw.brushticket.service.BrushticketService;
import com.czw.brushticket.utils.ConfigUtil;
import com.czw.brushticket.utils.FileUtil;
import com.czw.brushticket.utils.HttpClientUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: czw
 * @Date: 2018-12-03 17:33
 **/
@Service
public class BrushticketServiceImpl implements BrushticketService {
    @Override
    public Map<String, String> getImg() {
        String imgName = System.currentTimeMillis()+".jpg";
        Map<String,String> result = new HashMap<>();
        result.put("status","false");
        boolean flag = HttpClientUtil.doGetByImg(ConfigUtil.GET_IMG,imgName);
        if (flag){
            result.put("status","true");
            result.put("imgName",ConfigUtil.IMG_IP+imgName);
        }
        return result;
    }

    @Override
    public void login(String username, String password, String position, String imgName) throws IOException {
        if (StringUtils.isEmpty(position)){
            return;
        }
        String answer = position.substring(1);
        String[] ids = imgName.split("/");
        //图片名称
        String imgname = ids[ids.length-1];
        //图片标识
        String _passport_ct = (String) HttpClientUtil.map.get(imgname);
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
        //删除照片
        FileUtil.delete(ConfigUtil.PATH+imgname);
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
