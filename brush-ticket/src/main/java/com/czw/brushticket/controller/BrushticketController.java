package com.czw.brushticket.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.czw.brushticket.service.BrushticketService;
import com.czw.brushticket.utils.ConfigUtil;
import com.czw.brushticket.utils.FileUtil;
import com.czw.brushticket.utils.HttpClientUtil;
import com.czw.brushticket.utils.Result;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: czw
 * @create: 2018-10-03 10:35
 **/
@RestController
@RequestMapping("/brushticket")
public class BrushticketController extends BaseController{

    @Autowired
    private BrushticketService brushticketService;

    @RequestMapping("/getImg")
    public Result getImg(){
        return getResult(brushticketService.getImg());
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(String username, String password, String position, String imgName) throws IOException {
        System.out.println("名称："+username+" 密码："+password+" 位置："+position+ " 照片"+imgName);
        brushticketService.login(username,password,position,imgName);
        return getResult(1);
    }

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public Result query(String dateTime,String start,String end) throws IOException {
        String url = ConfigUtil.QUERY.replace("DATE_TIME",dateTime).replace("START",start).replace("END",end);
        HttpResponse httpResponse = HttpClientUtil.doGetResponse(url,null);
        HttpEntity responseEntity = httpResponse.getEntity();
        String str = EntityUtils.toString(responseEntity);
        JSONObject jsonObject = (JSONObject) JSON.parse(str);
        JSONObject data = (JSONObject) jsonObject.get("data");
        JSONArray result = (JSONArray) data.get("result");
        return getResult(result);
    }


}
