package com.czw.brushticket.service;

import java.io.IOException;
import java.util.Map;

/**
 * @author: czw
 * @Date: 2018-12-03 17:32
 **/
public interface BrushticketService {

    Map<String,String> getImg();

    void login(String username,String password,String position,String imgName) throws IOException;

}
