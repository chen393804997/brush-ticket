package com.czw.brushticket.service;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * @author: czw
 * @Date: 2018-12-03 17:32
 **/
public interface BrushticketService {

    JSONObject getImg();

    void login(String username,String password,String position,String imgName) throws IOException;

}
